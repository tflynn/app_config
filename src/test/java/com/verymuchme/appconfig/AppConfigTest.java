/* Copyright 2009-2013 Tracy Flynn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.verymuchme.appconfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.configuration.DefaultConfigurationBuilder;

import com.verymuchme.appconfig.test.TestBase;

@RunWith(JMock.class)
public class AppConfigTest {

  private static final Logger logger = LoggerFactory.getLogger(AppConfigTest.class);
  

  Mockery context = new JUnit4Mockery();

  HashMap<String,String> configurationOptions = null;
  
  @Before
  public void silenceLoggers() {
    Log4jHelper.silencePackage("freemarker");
    Log4jHelper.loadInitialInternalLogger("TRACE");
    configurationOptions = new HashMap<String,String>();
    configurationOptions.put(ConfigurationDefinitionBuilder.RUN_TIME_ENVIRONMENT_PROPERTY_NAME,"test");
    configurationOptions.put(ConfigurationDefinitionBuilder.DEFAULT_LOGGING_LEVEL_PROPERTY_NAME,"TRACE");
  }
  
  
  @Test
  public void testConfigureInternalOnly() {
    
    //Case 1 optional files present, defaults not present - should generate an error
    logger.trace("AppConfigTest.testConfigureInternalOnly.case1 starting");
    String packageName1 = "com.verymuchme.appconfig.test.internalOnly.case1";
    boolean success = false;
    try {
      AppConfig appConfig = new AppConfig();
      appConfig.setApplicationPropertiesPackageName(packageName1);
      appConfig.setOptions(this.configurationOptions);
      appConfig.configure();
    }
    catch (Exception e) {
      logger.debug(String.format("AppConfigTest.testConfigureInternalOnly error testing case 1"),e);
      success = true;
    }
    assertTrue("Optional properties files present, defaults not present, so the property load should fail", success);
    if (logger.isTraceEnabled()) {
      logger.trace(String.format("AppConfigTest.testConfigureInternalOnly.case1 %s", success ? "passed" : "failed"));
    }
    
    //Case 2 optional files present, defaults presents - check overridden values
    logger.trace("AppConfigTest.testConfigureInternalOnly.case2 starting");
    String packageName2 = "com.verymuchme.appconfig.test.internalOnly.case2";
    success = true;
    try {
      AppConfig appConfig = new AppConfig();
      appConfig.setApplicationPropertiesPackageName(packageName2);
      appConfig.setOptions(this.configurationOptions);
      appConfig.configure();
      CombinedConfiguration configuration = appConfig.getCombinedConfiguration();
      assertEquals("Optional properties files present, defaults present value not overridden", "app_value1", configuration.getString("app.test.value.1"));
      assertEquals("Optional properties files present, defaults present value overridden", "app_value2", configuration.getString("app.test.value.2"));
      assertEquals("Optional properties files present, defaults present value overridden", "db_value1", configuration.getString("db.test.value.1"));
      assertEquals("Optional properties files present, defaults present value not overridden", "db_value2", configuration.getString("db.test.value.2"));
      
    }
    catch (Exception e) {
      success = false;
    }
    assertTrue(success);

    if (logger.isTraceEnabled()) {
      logger.trace(String.format("AppConfigTest.testConfigureInternalOnly.case2 %s", success ? "passed" : "failed"));
    }
    
  }
  
  
  @Test
  public void testConfigureExternal() {

    logger.trace("AppConfigTest.testConfigureExternal starting");

    String tempDirName = getTemporaryDir();
    File tempDir = new File(tempDirName);
    logger.debug(String.format("AppConfigTest.testConfigureExternal temporary dir is %s and %s",tempDirName, tempDir.exists() ? "exists" : "doesn't exist"));

    String appConfigValue = "external_app_value1";
    String appConfigString = String.format("app.test.value.1 = %s",appConfigValue);
    String dbConfigValue = "external_db_value2";
    String dbConfigString = String.format("db.test.value.2 = %s", dbConfigValue);
    List<String> externalFileNames = createExternalTestFiles(tempDirName, appConfigString, dbConfigString);
    
    //Case 1 optional files present, defaults presents, external files present - check overridden values
    String packageName2 = "com.verymuchme.appconfig.test.internalOnly.case2";
    boolean success = true;
    try {
      AppConfig appConfig = new AppConfig();
      appConfig.setApplicationPropertiesPackageName(packageName2);
      appConfig.setExternalConfigurationDirectory(tempDirName);
      appConfig.setOptions(this.configurationOptions);
      appConfig.configure();
      CombinedConfiguration configuration = appConfig.getCombinedConfiguration();
      assertEquals("Optional properties files present, defaults present value overridden", appConfigValue , configuration.getString("app.test.value.1"));
      assertEquals("Optional properties files present, defaults present value not overridden", "app_value2", configuration.getString("app.test.value.2"));
      assertEquals("Optional properties files present, defaults present value overridden", "db_value1", configuration.getString("db.test.value.1"));
      assertEquals("Optional properties files present, defaults present value overridden", dbConfigValue, configuration.getString("db.test.value.2"));

      for (String externalFileName : externalFileNames) {
        File tmpFile = new File(externalFileName);
        tmpFile.delete();
      }
      deleteDir(tempDirName);
      logger.debug(String.format("AppConfigTest.testConfigureExternal deleted dir %s and %s",tempDirName, tempDir.exists() ? "exists" : "doesn't exist"));

    }
    catch (Exception e) {
      logger.error("AppConfigTest.testConfigureExternal error during test",e);
      success = false;
    }
    
    assertTrue("Internal defaults and optional files and external files present. Configuration values overridden correctly",success);
    if (logger.isTraceEnabled()) {
      logger.trace(String.format("AppConfigTest.testConfigureExternal finished and %s", success ? "passed" : "failed"));
    }
    
    
  }
  
  private String getTemporaryDir() {
    String tempDir = null;
    try {
      File file = File.createTempFile("tmpdir", "");
      file.delete();
      file.mkdir();
      tempDir = file.getAbsolutePath();
    }
    catch (Exception e) {
      logger.error(String.format("AppConfigTest.getTemporaryDir error creating temporary test dir"),e);
      tempDir = null;
    }
    return tempDir;
  }
  
  private void deleteDir(String dirName) {
    try {
      File dir = new File(dirName);
      dir.delete();
    }
    catch (Exception e) {
      logger.error(String.format("AppConfigTest.deleteDir error deleting temporary test dir %s",dirName),e);
    }
  }
  
  private List<String> createExternalTestFiles(String externalDirName, String appConfigString, String dbConfigString) {
    List<String> externalFiles =  new ArrayList<String>();
    try {
      String fullPath = null;
      File configFile = null;
      BufferedWriter bw = null;
      fullPath = (new File(externalDirName,"application-test.properties")).toString();
      externalFiles.add(fullPath);
      configFile = new File(fullPath);
      bw = new BufferedWriter(new FileWriter(configFile));
      bw.write(appConfigString + "\n");
      bw.close();
      fullPath = (new File(externalDirName,"database-test.properties")).toString();
      externalFiles.add(fullPath);
      configFile = new File(fullPath);
      bw = new BufferedWriter(new FileWriter(configFile));
      bw.write(dbConfigString + "\n");
      bw.close();
      
    }
    catch (Exception e) {
      logger.error("AppConfigTest.createExternalTestFiles error creating files",e);
      externalFiles = Arrays.asList();
    }
    return externalFiles;
  }
}
