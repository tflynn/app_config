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
 * Factory for LoggingHelpers
 * 
 * @author Tracy Flynn
 * @version 3.0
 * @since 1.0
 */
public class LoggingHelperFactory {


  /**
   * Get an instance of the default LoggerHelper
   * 
   * @return Instance of requested LoggerHelper class, or NullLoggerHelper if any instantiation or other errors 
   */
  public static LoggingHelper instance() {
    return LoggingHelperFactory.instance(null);
  }
  
  /**
   * Get an instance of the default LoggerHelper allowing for class name overrides
   * 
   * @param options Runtime options
   * @return Instance of requested LoggerHelper class, or NullLoggerHelper if any instantiation or other errors 
   */
  public static LoggingHelper instance(Options options) {
    options = options == null ? new Options() : options;
    ConfigurationHelper configurationHelper = new ConfigurationHelper();
    String loggingHelperClassName = null;
    LoggingHelper loggingHelperInstance = null;
    try {
      loggingHelperClassName = (String) configurationHelper.getSettingFromOptionsEnvSystem(InternalConfigurationConstants.DEFAULT_LOGGING_HELPER_CLASS_NAME_PROPERTY_NAME, options);
    } 
    catch (Exception e) {
      loggingHelperClassName = InternalConfigurationConstants.DEFAULT_LOGGING_HELPER_CLASS_NAME;
    }
    if (loggingHelperClassName == null) {
      loggingHelperClassName = InternalConfigurationConstants.DEFAULT_LOGGING_HELPER_CLASS_NAME;
    }
    try {
      Class loggingHelperClass = Class.forName(loggingHelperClassName);
      Constructor constructor = loggingHelperClass.getConstructor(Options.class);
      loggingHelperInstance = (LoggingHelper) constructor.newInstance(options);
    }
    catch (Exception e) {
      loggingHelperInstance = NullLoggingHelper.INSTANCE;
    }
    return loggingHelperInstance;
  }
}
