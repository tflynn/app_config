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

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;



import org.apache.commons.configuration.DefaultConfigurationBuilder;

public class FreemarkerHandlerTest {

  private static TestLogger logger = null;
  

  //private static final String RUNTIME_ENVIRONMENT = "test";
  private static final String INTERNAL_LOGGING_LEVEL = "TRACE";
  

  private static boolean internalLoggingIntiialized = false;
  private static boolean loggersQuietened = false;

  public FreemarkerHandlerTest() {
    initializeTestLogger();
    logger.trace("AppConfigTest()");
    quietLoggers();

  }
  private void quietLoggers() {
    if (! loggersQuietened) {
      logger.trace("AppConfigTest.quietLoggers start");
      LoggingHelper loggingHelper = LoggingHelperFactory.instance();
      loggingHelper.quietLoggingInitializationMessages("freemarker");
      loggingHelper.quietLoggingInitializationMessages("org.apache.commons.configuration");
      logger.trace("AppConfigTest.quietLoggers end");
      loggersQuietened = true;
    }
  }
  
  @Test
  public void testDefaultTemplateLoad() throws Exception {
    
    logger.trace("AppConfig.FreemarkerHandlerTest.testDefaultTemplateLoad starting");
    
    String testTemplateName = "test-configuration-xml.ftl";
    
    String expectedTemplateContents = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "\n" 
                                      + "\n"
                                      + "<configuration>" + "\n"
                                      + "   <properties fileName=\"application.properties\"/>" + "\n"
                                      + "</configuration>" + "\n";

    FreemarkerHandler freemarkerHandler = new FreemarkerHandler(FreemarkerHandlerTest.class);
    HashMap<String,String> data = new HashMap<String,String>();
    String templateContents = freemarkerHandler.getTemplate(testTemplateName,null);
    boolean success = templateContents.equals(expectedTemplateContents); 
    assertTrue("The loaded template should match the default contents",success);
    if (logger.isTraceEnabled()) {
      logger.trace(String.format("AppConfig.FreemarkerHandlerTest.testDefaultTemplateLoad finished and %s", success ? "passed" : "failed"));
    }
  }
  
  /*
   * Lazy initializer for test logger
   */
  private static void initializeTestLogger() {
    if (!internalLoggingIntiialized) {
      //announceStart("AppConfigTest.initializer");
      logger = new TestLogger();
      logger.setLevelString(INTERNAL_LOGGING_LEVEL);
      //announceEnd("AppConfigTest.initializer");
      internalLoggingIntiialized = true;
    }
  }
  
}
