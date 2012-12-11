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

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.apache.commons.configuration.DefaultConfigurationBuilder;

@RunWith(JMock.class)
public class AppConfigTest {

  Mockery context = new JUnit4Mockery(); 
  @SuppressWarnings("deprecation")
  @Test
  public void testConfigure() throws Exception {
//      AppConfig.sConfigure();
//      String configName = "/Users/tracy/Dropbox/ClickFuel/MarketMate/Code/experiments/test_configdir/config_definition.xml";
//      DefaultConfigurationBuilder dcb = new DefaultConfigurationBuilder(configName);
//      dcb.getConfiguration();
      assertTrue(true);
  }

}
