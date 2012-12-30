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

import java.lang.reflect.Constructor;

/**
 * Factory for ConfigurationBuilders
 * 
 * @author Tracy Flynn
 * @version 3.0
 * @since 1.0
 */
public class ConfigurationBuilderFactory {
  
  /**
   * Get an instance of the default ConfigurationBuilder
   * 
   * @return Instance of requested ConfigurationBuilder class, or NullConfigurationBuilder if any instantiation or other errors 
   */
  public static ConfigurationBuilder instance() {
    return ConfigurationBuilderFactory.instance(null);
  }
  
  /**
   * Get an instance of the default ConfigurationBuilder allowing for class name overrides
   * 
   * @param internalProperties Fully defaulted set of internal properties
   * 
   * @return Instance of requested ConfigurationBuilder class, or NullConfigurationBuilder if any instantiation or other errors 
   */
  public static ConfigurationBuilder instance(ExtendedProperties internalProperties) {
    internalProperties = internalProperties == null ? new ExtendedProperties() : internalProperties;
    ConfigurationHelper configurationHelper = new ConfigurationHelper();
    ConfigurationBuilder configurationBuilderInstance = null;
    
    String configurationBuilderClassName = internalProperties.getProperty(InternalConfigurationConstants.DEFAULT_CONFIGURATION_BUILDER_CLASS_NAME_PROPERTY_NAME);
    if (configurationBuilderClassName == null) {
      configurationBuilderClassName = InternalConfigurationConstants.DEFAULT_CONFIGURATION_BUILDER_CLASS_NAME;
    }
    try {
      Class configurationBuilderClass = Class.forName(configurationBuilderClassName);
      configurationBuilderInstance = (ConfigurationBuilder) configurationBuilderClass.newInstance();
    }
    catch (Exception e) {
      configurationBuilderInstance = NullConfigurationBuilder.INSTANCE;
    }
    return configurationBuilderInstance;
  }

}
