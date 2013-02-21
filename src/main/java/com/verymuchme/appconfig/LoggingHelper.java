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

import java.util.List;

import ch.qos.logback.classic.Logger;

public interface LoggingHelper {

  /**
   * Configure a logger for bootstrap purposes. No external settings are available at this point
   */
  public void bootstrapInternalLogging();
  
  /**
   * Configure a logger for bootstrap purposes. No external settings are available at this point
   * 
   * @param loggingLevelString Log level as a string - Logging implementation specific
   */
  public void bootstrapInternalLogging(String loggingLevelString);

  /**
   * Configure a logger for bootstrap purposes. No external settings are available at this point
   * 
   * @param loggerName Name of logger to bootstrap
   * @param loggingLevelString Log level as a string - Logging implementation specific
   */
  public void bootstrapInternalLogging(String loggerName,String loggingLevelString);
  
  
  /**
   * Quieten the loggers associated with a list of package names. Defaults to ERROR level if logLevelString is not specified
   * 
   * @param loggerNames List of logger names (as strings)
   */
  public void quietLoggingInitializationMessages(List<String> loggerNames);
  
  /**
   * Quieten the loggers associated with a list of logger names. Defaults to ERROR level if logLevelString is not specified
   * 
   * @param loggerNames List of logger names (as strings)
   * @param logLevelString Log level as a string to set logger to. Defaults to "ERROR".
   */
  public void quietLoggingInitializationMessages(List<String> loggerNames, String logLevelString);
  
  /**
   * Quieten the loggers associated with a logger names. Defaults to ERROR level if logLevelString is not specified
   * 
   * @param loggerName Package name (as strings)
   */
  public void quietLoggingInitializationMessages(String loggerName);
  
  /**
   * Quieten the loggers associated with a logger name. Defaults to ERROR level if logLevelString is not specified
   * 
   * @param loggerName Logger name (as strings)
   * @param logLevelString Log level as a string to set logger to. Defaults to "ERROR".
   */
  public void quietLoggingInitializationMessages(String loggerName, String logLevelString);

  /**
   * Create loggers from the specified configuration files. Stop after first successful load.
   * 
   * @param loggingConfigurationFileNames
   */
  public void configureLoggerFromConfigurationFiles(List<String> loggingConfigurationFileNames);

  /**
   * Create a logger from the specified configuration file name with the specified logging level as an override
   * 
   * @param loggingConfigurationFileName
   * @param loggingLevel
   */
  public void configureLoggerFromConfigurationFile(String loggingConfigurationFileName, String loggingLevel);
  
  
  /**
   * Create a logger from the specified configuration file name with the specified logging level as an override. 
   * Use the specified logger to log any error messages
   * 
   * @param loggingConfigurationFileName
   * @param loggingLevel
   * @param activeLogger logger to use for logging until logging is configured for the specified logger. Null indicates no override
   */
  public void configureLoggerFromConfigurationFile(String loggingConfigurationFileName, String loggingLevel, Logger activeLogger);
 
  /**
   * Create a logger from the specified configuration file name with the specified logging level as an override. 
   * Use the specified logger to log any error messages. Specify the class context.
   * 
   * @param loggingConfigurationFileName
   * @param loggingLevel
   * @param activeLogger logger to use for logging until logging is configured for the specified logger. Null indicates no override
   * @param classContext Specify class for resource loading. If null, loading is relative to LoggingHelper class instance
   */
  public void configureLoggerFromConfigurationFile(String loggingConfigurationFileName, String loggingLevel, Logger activeLogger, Class classContext);
  
  /**
   * Override logging level for specified logger
   * 
   * @param loggerName Name of logger
   * @param loggingLevel Logging level
   */
  public void overrideLogLevel(String loggerName, String loggingLevel);
  
  /**
   * Save off the extended properties. After initialization, these values override options
   * 
   * @param internalProperties ExtendedProperties instance
   */
  public void setExtendedProperties(ExtendedProperties internalProperties); 
}
