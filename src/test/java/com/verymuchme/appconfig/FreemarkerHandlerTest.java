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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.configuration.DefaultConfigurationBuilder;

public class FreemarkerHandlerTest {

  private static final Logger logger = LoggerFactory.getLogger(FreemarkerHandlerTest.class);

  @Before
  public void silenceLoggers() {
    Log4jHelper.silencePackage("freemarker");
    Log4jHelper.loadInitialInternalLogger("TRACE");
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
  
}
