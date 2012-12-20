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
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dynamically build a configuration definition compatible with Apache Commons Configuration
 *
 * See the documentation in AppConfig for details.
 * 
 * @author Tracy Flynn
 * @version 1.2
 * @since 1.0
 */
public class ConfigurationDefinitionBuilder {

  private static final String INTERNAL_DEFAULTS_PROPERTY_FILE_NAME_PROPERTY_NAME = "com.verymuchme.appconfig.internalDefaultPropertiesFileName";
  private static final String INTERNAL_DEFAULTS_PROPERTY_FILE_NAME = "internal-defaults.properties";
  
  public static final String APPLICATION_PROPERTIES_PACKAGE_NAME_PROPERTY_NAME = "application.propertiesPackageName";
  public static final String APPLICATION_PROPERTIES_PACKAGE_DIR_PROPERTY_NAME = "application.propertiesPackageDir";

  public static final String SYSTEM_PROPERTIES_OVERRIDE_PROPERTY_NAME = "com.verymuchme.appconfig.systemPropertiesOverride";
  public static final String EXTERNAL_CONFIGURATION_DIRECTORY_PROPERTY_NAME = "com.verymuchme.appconfig.externalConfigurationDirectory";
  
  public static final String PERMITTED_RUN_TIME_ENVIRONMENTS_PROPERTY_NAME = "com.verymuchme.appconfig.permittedRunTimeEnvironments";
  public static final String DEFAULT_RUN_TIME_ENVIRONMENT_PROPERTY_NAME = "com.verymuchme.appconfig.defaultRunTimeEnvironment";
  public static final String RUN_TIME_ENVIRONMENT_PROPERTY_NAME = "com.verymuchme.appconfig.runTimeEnvironment";
  
  public static final String APPLICATION_CONFIGURATION_NAME_PREFIX_PROPERTY_NAME = "com.verymuchme.appconfig.applicationConfigurationPrefix";
  
  public static final String DATABASE_CONFIGURATION_NAME_PREFIX_PROPERTY_NAME = "com.verymuchme.appconfig.database.ConfigurationPrefix";
  public static final String DEFAULT_DATABASE_CONFIGURATION_ENABLED_PROPERTY_NAME = "com.verymuchme.appconfig.database.defaultConfigurationEnabled";
  
  public static final String LOG4J_CONFIGURATION_NAME_PREFIX_PROPERTY_NAME = "com.verymuchme.appconfig.log4j.ConfigurationPrefix";
  public static final String DEFAULT_LOG4J_CONFIGURATION_ENABLED_PROPERTY_NAME = "com.verymuchme.appconfig.log4j.defaultConfigurationEnabled";
  
  public static final String CONFIGURATION_NAME_SUFFIX_PROPERTY_NAME = "com.verymuchme.appconfig.configurationNameSuffix";
  
  public static final String DEFAULT_CONFIGURATION_NAME_PROPERTY_NAME = "com.verymuchme.appconfig.defaultConfigurationName";
  
  public static final String DEFAULT_FREEMARKER_CONFIGURATION_TEMPLATE_PROPERTY_NAME = "com.verymuchme.appconfig.configurationTemplateName";
  public static final String FREEMARKER_CONFIGURATION_BASE_CLASS_PROPERTY_NAME = "com.verymuchme.appconfig.freemarker.baseClassName";
  
  public static final String DEFAULT_LOGGING_LEVEL_PROPERTY_NAME = "com.verymuchme.appconfig.log4j.logLevel";
  public static final String INTERNAL_LOG4J_CONFIGURATION_FILE_PROPERTY_NAME = "com.verymuchme.appconfig.log4j.internalConfigurationFileName";
  
  /*
   * Logger instance for this class
   */
  private static final Logger logger = LoggerFactory.getLogger(ConfigurationDefinitionBuilder.class);
  
  /*
   * Configuration options
   */
  private HashMap<String,String> configurationOptions = null;
  
  /*
   * Internal properties object. References for all internal settings 
   */
  private ExtendedProperties internalProperties = null;
   
  /**
   * Create a new instance
   */
  public ConfigurationDefinitionBuilder(HashMap<String,String> configOptions) {
    this.configurationOptions = configOptions;
    String packageName = this.configurationOptions.get(APPLICATION_PROPERTIES_PACKAGE_NAME_PROPERTY_NAME);
    if (packageName != null) {
      String packageDir = packageName.replace(".", "/");
      this.configurationOptions.put(APPLICATION_PROPERTIES_PACKAGE_DIR_PROPERTY_NAME, packageDir);
    }
  }

  /**
   * Set internal properties.
   * 
   * Default in order to options, command=line, environment, defaults
   * 
   * @throws Exception if the internal properties cannot be loaded
   */
  public void setInternalProperties() {
    
    Properties internalDefaults = null;
    InputStream internalDefaultsInputStream = null;
    
    // Load internal defaults
    try {
      internalDefaults = new Properties();
      internalDefaultsInputStream = getClass().getResourceAsStream(INTERNAL_DEFAULTS_PROPERTY_FILE_NAME);
      internalDefaults.load(internalDefaultsInputStream);
      internalDefaultsInputStream.close();
    }
    catch (Exception e) {
      String errorMessage = "AppConfig.ConfigurationDefinitionBuilder.setInternalProperties failed to load the default internal properties for AppConfig";
      logger.error(errorMessage,e);
      throw new AppConfigException(errorMessage,e);
    }

    try {
      boolean overriddenAlready = false; 
      
      while (true) {
  
        // Load the defaults first
        this.internalProperties = new ExtendedProperties();
        // Use putAll rather than the constructor so as not to get multiple copies of the same property
        this.internalProperties.putAll(internalDefaults);
        
        // Do this in reverse order so the value defaulting order works
        @SuppressWarnings("unchecked")
        Enumeration<String> propertyNames = (Enumeration<String>) this.internalProperties.propertyNames();
        while (propertyNames.hasMoreElements()) {
          String propertyName = propertyNames.nextElement();
          String overrideValue = null;
          overrideValue = System.getenv(propertyName);
          if (overrideValue != null) {
            this.internalProperties.put(propertyName, overrideValue);
          }
          overrideValue = System.getProperty(propertyName);
          if (overrideValue != null) {
            this.internalProperties.put(propertyName, overrideValue);
          }
          overrideValue = this.configurationOptions.get(propertyName);
          if (overrideValue != null) {
            this.internalProperties.put(propertyName, overrideValue);
          }
          
        }
        
        // Check for an override to the internal properties file name. If present, reload everything
        String overriddenInternalPropertiesFileName = this.internalProperties.getNullProperty(INTERNAL_DEFAULTS_PROPERTY_FILE_NAME_PROPERTY_NAME);
        if (overriddenInternalPropertiesFileName == null) {
          // No override, so load is complete
          break;
        }
        else {
          if (overriddenAlready) {
            // Only override once
            break;
          }
          else {
            // Go around again to reload everything against the new defaults
            logger.trace(String.format("AppConfig.ConfigurationDefinitionBuilder.setInternalProperties Reloading because found internal property file override %s",overriddenInternalPropertiesFileName));
            internalDefaults = new Properties();
            internalDefaultsInputStream = getClass().getResourceAsStream(overriddenInternalPropertiesFileName);
            internalDefaults.load(internalDefaultsInputStream);
            internalDefaultsInputStream.close();        
            overriddenAlready = true;
          }
        }
      }
    }
    catch (Exception e) {
      String errorMessage = "AppConfig.ConfigurationDefinitionBuilder.setInternalProperties failed to load internal AppConfig properties or overrides";
      logger.error(errorMessage,e);
      throw new AppConfigException(errorMessage,e);
    }
    
    checkRunTimeEnvironment();

    if (logger.isTraceEnabled()) {
      logger.trace(String.format("ConfigurationDefinitionBuilder.setInternalProperties internalProperties settings"));
      AppConfigUtils.dumpMap(this.internalProperties);
    }

  } 
    
  
  /** 
   * Check the run-time environment. Defaults to 'development' if any errors during setting
   */
  public void checkRunTimeEnvironment() {
    boolean matched = false;
    String runTimeEnv = this.internalProperties.getProperty(RUN_TIME_ENVIRONMENT_PROPERTY_NAME);
    for (String env : this.internalProperties.getListProperty(PERMITTED_RUN_TIME_ENVIRONMENTS_PROPERTY_NAME)) {
      if (env.equalsIgnoreCase(runTimeEnv)) {
        runTimeEnv = env;
        matched = true;
        break;
      }
    }
    if (!matched) {
      runTimeEnv = this.internalProperties.getProperty(DEFAULT_RUN_TIME_ENVIRONMENT_PROPERTY_NAME);
      this.internalProperties.setProperty(RUN_TIME_ENVIRONMENT_PROPERTY_NAME,runTimeEnv);
      logger.trace(String.format("AppConfig.setRunTimeEnvironment invalid runtime environment set, defaulting to '%s'",runTimeEnv));
    }
    else {
      logger.trace(String.format("AppConfig.setRunTimeEnvironment setting runtime environment to '%s'",runTimeEnv));
    }
  }
  
  /**
   * Generate log4j configuration file names - ordered so first one on the list that exists should be loaded
   * 
   * @return List of log4j configuration file names
   */
  public List<String> generateLog4jConfigurationNames() {
    List<String> configNames = new ArrayList<String>();
    String packageDir = this.internalProperties.getProperty(APPLICATION_PROPERTIES_PACKAGE_DIR_PROPERTY_NAME);
    String suffix = this.internalProperties.getProperty(CONFIGURATION_NAME_SUFFIX_PROPERTY_NAME);
    String log4jPrefix = this.internalProperties.getProperty(LOG4J_CONFIGURATION_NAME_PREFIX_PROPERTY_NAME);
    String rtEnv = this.internalProperties.getProperty(RUN_TIME_ENVIRONMENT_PROPERTY_NAME);
    String defaultPropName = this.internalProperties.getProperty(DEFAULT_CONFIGURATION_NAME_PROPERTY_NAME);
    String externalConfigurationDirectory = this.internalProperties.getNullProperty(EXTERNAL_CONFIGURATION_DIRECTORY_PROPERTY_NAME);
    if (externalConfigurationDirectory != null ) {
      String confDir = externalConfigurationDirectory;
      String extLog4jDef = String.format("%s/%s-%s.%s",confDir,log4jPrefix,rtEnv,suffix);
      configNames.add(extLog4jDef);
    }
    String log4jDef = String.format("%s/%s-%s.%s",packageDir,log4jPrefix,rtEnv,suffix);
    configNames.add(log4jDef);

    boolean includeLog4jDefault = this.internalProperties.getBooleanProperty(DEFAULT_LOG4J_CONFIGURATION_ENABLED_PROPERTY_NAME);
    if (includeLog4jDefault) {
      String log4jDefault = String.format("%s/%s-%s.%s",packageDir,log4jPrefix,defaultPropName,suffix);
      configNames.add(log4jDefault);
    }
    
    for (String confName : configNames) {
      logger.trace(String.format("AppConfig.generateLog4jConfigurationNames %s",confName));
    }
    
    return configNames;
  }

  /**
   * Load a Configuration Definition file using Apache Commons Configuration DefaultConfigurationBuilder
   * 
   * @param configurationDefinition
   * @return CommonConfiguration instance
   */
  public CombinedConfiguration loadConfigurationDefinition(String configurationDefinition) {
    
    File tempFile = null;
    CombinedConfiguration combinedConfiguration = null;
    
    try {

      // Write it to a temporary file
      File temp = File.createTempFile("tempfile", ".xml");
      BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
      bw.write(configurationDefinition);
      bw.close();
  
      // Create a handle to the temporary file
      tempFile = temp.getAbsoluteFile();
      String tempFileName = tempFile.getAbsolutePath();
      logger.trace(String.format("AppConfig.configure temporary file name %s", tempFileName));
      
      // Load configuration definition in as a file
      DefaultConfigurationBuilder defaultConfigurationBuilder = new DefaultConfigurationBuilder();
      defaultConfigurationBuilder.load(tempFile);
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
        tempFile.delete();
      }
      catch (Exception ee) {
        // Ignore
      }
    }
    return combinedConfiguration;
  }

  /**
   * Get the configuration definition as an (XML) string
   */
  public String generateConfigurationDefinition() {

      try {
        String suffix = this.internalProperties.getProperty(CONFIGURATION_NAME_SUFFIX_PROPERTY_NAME);
        String applicationPrefix = this.internalProperties.getProperty(APPLICATION_CONFIGURATION_NAME_PREFIX_PROPERTY_NAME);
        String databasePrefix = this.internalProperties.getProperty(DATABASE_CONFIGURATION_NAME_PREFIX_PROPERTY_NAME);
        String rtEnv = this.internalProperties.getProperty(RUN_TIME_ENVIRONMENT_PROPERTY_NAME);
        String defaultPropName = this.internalProperties.getProperty(DEFAULT_CONFIGURATION_NAME_PROPERTY_NAME);
        boolean systemOverride = this.internalProperties.getBooleanProperty(SYSTEM_PROPERTIES_OVERRIDE_PROPERTY_NAME);
        String systemOverrideString = systemOverride ? "true" : "false";
        String packageDir = this.internalProperties.getProperty(APPLICATION_PROPERTIES_PACKAGE_DIR_PROPERTY_NAME);
        String externalConfigurationDirectory = this.internalProperties.getNullProperty(EXTERNAL_CONFIGURATION_DIRECTORY_PROPERTY_NAME);
        
        HashMap<String,String> templateData = new HashMap<String,String>();
        templateData.put("suffix", suffix);
        templateData.put("applicationPrefix", applicationPrefix);
        templateData.put("databasePrefix", databasePrefix);
        templateData.put("rtEnv", rtEnv);
        templateData.put("defaultPropName", defaultPropName);
        templateData.put("systemOverride", systemOverrideString);
        templateData.put("packageDir", packageDir);
        if (externalConfigurationDirectory != null) {
          templateData.put("externalConfigurationDirectory",externalConfigurationDirectory);
        }
  
        FreemarkerHandler freemarkerHandler = null;
        String freemarkerBaseClassName = this.internalProperties.getNullProperty(FREEMARKER_CONFIGURATION_BASE_CLASS_PROPERTY_NAME);
        if (freemarkerBaseClassName == null) {
          freemarkerHandler = new FreemarkerHandler();
        }
        else {
          Class baseClass = Class.forName(freemarkerBaseClassName);
          freemarkerHandler = new FreemarkerHandler(baseClass);
        }
        freemarkerHandler.setInternalProperties(this.internalProperties);
        String templateContents = freemarkerHandler.getTemplate(this.internalProperties.getProperty(DEFAULT_FREEMARKER_CONFIGURATION_TEMPLATE_PROPERTY_NAME),templateData);
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
   * Set configuration options
   * 
   * @param configOpts
   */
  public void setOptions(HashMap<String,String> configOpts) {
    this.configurationOptions = configOpts;
  }
  
  /**
   * Get configuration options
   * 
   * @return Configuration options
   */
   public HashMap<String,String> getOptions() {
     return this.configurationOptions;
   }

   /**
    * Get AppConfig internal properties
    * 
    * @return ExtendedProperties instance with all fully resolved settings
    */
   public ExtendedProperties getInternalProperties() {
     return internalProperties;
   }


   
}
