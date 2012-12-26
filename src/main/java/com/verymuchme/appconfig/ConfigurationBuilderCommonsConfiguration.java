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

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dynamically build a configuration definition compatible with Apache Commons Configuration
 *
 * See the documentation in AppConfig for details.
 * 
 * @author Tracy Flynn
 * @version 3.0
 * @since 3.0
 */
public class ConfigurationBuilderCommonsConfiguration extends ConfigurationBuilderBase implements ConfigurationBuilder {

  /*
   * Logger instance for this class
   */
  private static final Logger logger = LoggerFactory.getLogger(ConfigurationBuilderCommonsConfiguration.class);
  
  /**
   * Create a new instance
   */
  public ConfigurationBuilderCommonsConfiguration() {
  }

  @Override
  public Configuration buildConfiguration() {
    String configurationDefinition = generateConfigurationDefinition();
    CombinedConfiguration combinedConfiguration = loadConfigurationDefinition(configurationDefinition);
    Configuration configuration = new ConfigurationCommonsConfiguration(combinedConfiguration);
    return configuration;
  }

  
  /**
   * Get the configuration definition as an (XML) string
   */
  private String generateConfigurationDefinition() {

      try {
        //Add all internal properties with appropriate prefixes to template data
        HashMap<String,String> templateData = new HashMap<String,String>();
        @SuppressWarnings("unchecked")
        Enumeration<String> propertyNames = (Enumeration<String>) internalProperties.propertyNames();
        while (propertyNames.hasMoreElements()) {
          String propertyName = propertyNames.nextElement();
          String propertyValueString = internalProperties.getStringProperty(propertyName);
          propertyName = propertyName.replace(InternalConfigurationConstants.INTERNAL_PACKAGE_PREFIX_FULL, InternalConfigurationConstants.INTERNAL_PACKAGE_PREFIX_SHORT);
          propertyName = propertyName.replaceAll("\\.", "_");
          templateData.put(propertyName, propertyValueString);
        }
        if (logger.isTraceEnabled()) {
          logger.trace("ConfigurationBuilderCommonsConfiguration: creating template data map");
          AppConfigUtils.dumpMap(templateData);
        }
        
        FreemarkerHandler freemarkerHandler = null;
        String freemarkerBaseClassName = this.internalProperties.getNullProperty(InternalConfigurationConstants.FREEMARKER_CONFIGURATION_BASE_CLASS_PROPERTY_NAME);
        if (freemarkerBaseClassName == null) {
          freemarkerHandler = new FreemarkerHandler();
        }
        else {
          Class baseClass = Class.forName(freemarkerBaseClassName);
          freemarkerHandler = new FreemarkerHandler(baseClass);
        }
        freemarkerHandler.setInternalProperties(this.internalProperties);
        String templateContents = freemarkerHandler.getTemplate(this.internalProperties.getProperty(InternalConfigurationConstants.DEFAULT_FREEMARKER_CONFIGURATION_TEMPLATE_PROPERTY_NAME),templateData);
        if (logger.isTraceEnabled()) {
          logger.trace("AppConfig.ConfigurationDefinitionBuilder.generateConfigurationDefinition definition file");
          logger.trace(templateContents);
        }
        return templateContents;
      }
      catch (Exception e) {
        String errorMessage = "AppConfig.ConfigurationDefinitionBuilder.generateConfigurationDefinition generate configuration definition from template";
        logger.error(errorMessage,e);
        throw new AppConfigException(errorMessage,e);
      }
  }
  
  /**
   * Load a Configuration Definition file using Apache Commons Configuration DefaultConfigurationBuilder
   * 
   * @param configurationDefinition
   * @return CommonConfiguration instance
   */
  private CombinedConfiguration loadConfigurationDefinition(String configurationDefinition) {
    
    InputStream configurationDefinitionIs = null;
    CombinedConfiguration combinedConfiguration = null;
    
    try {
      configurationDefinitionIs = new ByteArrayInputStream(configurationDefinition.getBytes("UTF-8"));
      // Load configuration definition
      DefaultConfigurationBuilder defaultConfigurationBuilder = new DefaultConfigurationBuilder();
      defaultConfigurationBuilder.load(configurationDefinitionIs);
      logger.trace(String.format("AppConfig.ConfigurationDefinitionBuilder.loadConfigurationDefinition configuration definition loaded"));
      combinedConfiguration = defaultConfigurationBuilder.getConfiguration(false);
      logger.trace(String.format("AppConfig.ConfigurationDefinitionBuilder.loadConfigurationDefinition configuration generated successfully"));
    }
    catch (Exception e) {
      String errorMessage = "AppConfig.ConfigurationDefinitionBuilder.loadConfigurationDefinition failed to load configuration definition file";
      logger.error(errorMessage,e);
      throw new AppConfigException(errorMessage,e);
    }
    finally {
      try {
        // Delete the temporary file
        configurationDefinitionIs.close();
      }
      catch (Exception ee) {
        // Ignore
      }
    }
    return combinedConfiguration;
  }
  

}
