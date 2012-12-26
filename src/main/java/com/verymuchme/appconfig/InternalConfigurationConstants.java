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

/**
 * Constants for internal configuration support
 *  
 * @author Tracy Flynn
 * @version 3.0
 * @since 3.0
 */
public class InternalConfigurationConstants {

  public static final String INTERNAL_PACKAGE_PREFIX_FULL = "com.verymuchme.appconfig";
  public static final String INTERNAL_PACKAGE_PREFIX_SHORT = "appconfig";
  
  public static final String INTERNAL_DEFAULTS_PROPERTY_FILE_NAME_PROPERTY_NAME = "com.verymuchme.appconfig.internalDefaultPropertiesFileName";
  public static final String INTERNAL_DEFAULTS_PROPERTY_FILE_NAME = "internal-defaults.properties";
  
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
  
  public static final String DEFAULT_LOGGING_LEVEL_PROPERTY_NAME = "com.verymuchme.appconfig.logging.logLevel";
  public static final String INTERNAL_LOGGING_CONFIGURATION_FILE_PROPERTY_NAME = "com.verymuchme.appconfig.logging.internalConfigurationFileName";
  
  public static final String DEFAULT_LOGGING_HELPER_CLASS_NAME_PROPERTY_NAME = "com.verymuchme.appconfig.loggingHelper.className";
  public static final String DEFAULT_LOGGING_HELPER_CLASS_NAME = "com.verymuchme.appconfig.LoggingHelperLog4j";
  public static final String DEFAULT_BOOTSTRAP_LOGGING_LEVEL_PROPERTY_NAME = "com.verymuchme.appconfig.boostrapLogging.logLevel";
  public static final String DEFAULT_BOOTSTRAP_LOGGING_LEVEL = "ERROR";
  
  public static final String DEFAULT_CONFIGURATION_BUILDER_CLASS_NAME_PROPERTY_NAME = "com.verymuchme.appconfig.configurationBuilder.className";
  public static final String DEFAULT_CONFIGURATION_BUILDER_CLASS_NAME = "com.verymuchme.appconfig.ConfigurationBuilderCommonsConfiguration";
  
}
