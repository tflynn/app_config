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
   * Quieten the loggers associated with a list of package names. Defaults to ERROR level if logLevelString is not specified
   * 
   * @param packageNames List of package names (as strings)
   */
  public void quietLoggingInitializationMessages(List<String> packageNames);
  
  /**
   * Quieten the loggers associated with a list of package names. Defaults to ERROR level if logLevelString is not specified
   * 
   * @param packageNames List of package names (as strings)
   * @param logLevelString Log level as a string to set logger to. Defaults to "ERROR".
   */
  public void quietLoggingInitializationMessages(List<String> packageNames, String logLevelString);
  
  /**
   * Quieten the loggers associated with a package names. Defaults to ERROR level if logLevelString is not specified
   * 
   * @param packageName Package name (as strings)
   */
  public void quietLoggingInitializationMessages(String packageName);
  
  /**
   * Quieten the loggers associated with a package names. Defaults to ERROR level if logLevelString is not specified
   * 
   * @param packageName Package name (as strings)
   * @param logLevelString Log level as a string to set logger to. Defaults to "ERROR".
   */
  public void quietLoggingInitializationMessages(String packageName, String logLevelString);

  /**
   * Create a logger from the specified configuration file name with the specified logging level as an override
   * 
   * @param loggingConfigurationFileName
   * @param loggingLevel
   */
  public void configureLoggerFromConfigurationFile(String loggingConfigurationFileName, String loggingLevel);
  
  
}
