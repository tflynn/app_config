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

import java.util.HashMap;

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
    configurationOptions = new HashMap<String,String>();
    configurationOptions.put(ConfigurationDefinitionBuilder.RUN_TIME_ENVIRONMENT_PROPERTY_NAME,"test");
  }
  
  @Test
  public void testConfigureInternalOnly() {
    //Case 1 optional files present, defaults not present - should generate an error
    String packageName1 = "com.verymuchme.appconfig.test.internalOnly.case1";
    boolean success = true;
    try {
      AppConfig appConfig = new AppConfig();
      appConfig.setApplicationPropertiesPackageName(packageName1);
      appConfig.setOptions(this.configurationOptions);
      appConfig.configure();
    }
    catch (Exception e) {
      logger.debug(String.format("AppConfigTest.testConfigureInternalOnly error testing case 1"),e);
      success = false;
    }
    assertFalse("Optional properties files present, defaults not present, so the property load should fail", success);

    //Case 2 optional files present, defaults presents - check overridden values
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
    
  }
  
  
}
