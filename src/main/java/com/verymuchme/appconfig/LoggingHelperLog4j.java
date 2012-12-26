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

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingHelperLog4j implements LoggingHelper {
  
  /*
   * Logger instance
   */
  private static final Logger logger = LoggerFactory.getLogger(LoggingHelperLog4j.class);


  private static final Level DEFAULT_INTERNAL_BOOTSTRAP_LOG_LEVEL = Level.ERROR;
  private static final Level DEFAULT_INTERNAL_LOG_LEVEL = Level.ERROR;
  
  private static final String DEFAULT_CONSOLE_APPENDER_CLASS = "org.apache.log4j.ConsoleAppender";
  private static final String DEFAULT_LAYOUT_CLASS = "org.apache.log4j.PatternLayout";
  private static final String DEFAULT_LAYOUT_PATTERN = "[%d{yyyy-MM-dd HH:mm:ss}]   [%-5p]    [%t]  %m %n";
  private static final String DEFAULT_APPENDER_NAME = "CONSOLE_APPENDER";

    
  public LoggingHelperLog4j(){
    
  }
  
  public LoggingHelperLog4j(Options options) {
    
  }
  
  @Override
  public void bootstrapInternalLogging() {
    bootstrapInternalLogging(null);
  }

  @Override
  public void bootstrapInternalLogging(String loggingLevel) {
    String internalPackageName = LoggingHelperLog4j.class.getPackage().getName();
    loadLog4jLogger(internalPackageName,convertToLog4jLevel(loggingLevel,null));
  }

  
  @Override
  public void quietLoggingInitializationMessages(List<String> packageNames) {
    quietLoggingInitializationMessages(packageNames, null);
  }

  @Override
  public void quietLoggingInitializationMessages(List<String> packageNames, String logLevelString) {
    for (String packageName : packageNames) {
      quietLoggingInitializationMessages(packageName,logLevelString);
    }
  }

  @Override
  public void quietLoggingInitializationMessages(String packageName) {
    quietLoggingInitializationMessages(packageName,null);
  }

  @Override
  public void quietLoggingInitializationMessages(String packageName, String logLevelString) {
    loadLog4jLogger(packageName,logLevelString);
  }
  
  @Override
  public void configureLoggerFromConfigurationFile(String loggingConfigurationFileName, String loggingLevel) {
    loadLog4jConfiguration(loggingConfigurationFileName, loggingLevel);
  }
  
  /**
   * Convert a Log4j logging level expressed as a string to a Log4j Level
   * 
   * @param loggingLevelString String representation of Log4j logging level
   * @param defaultLevel Default Log4j logging level - used if conversion of loggingLevelString fails
   * @return Log4j Level representing specified string level, or defaultLevel if conversion error
   */
  private Level convertToLog4jLevel(String loggingLevelString, Level defaultLevel) {
    defaultLevel = defaultLevel == null ? DEFAULT_INTERNAL_LOG_LEVEL : defaultLevel;
    return  loggingLevelString == null ? defaultLevel : Level.toLevel(loggingLevelString, defaultLevel);
  }
  
  /**
   * Load a log4j logger configuration associated with a specified package name with a specified log level
   * 
   * @param packageName Name of package to log
   * @param logLevelString Logging level as string
   */
  private void loadLog4jLogger(String packageName, String logLevelString) {
    Level logLevel = convertToLog4jLevel(logLevelString,null);
    loadLog4jLogger(packageName,logLevel);
  }

  /**
   * Load a log4j logger configuration associated with a specified package name with a specified log level
   * 
   * @param packageName Name of package to log
   * @param logLevel Logging error level (log4j style)
   */
  private void loadLog4jLogger(String packageName, Level logLevel) {
    try {
      Properties props = new Properties();
      String logLevelString = logLevel.toString();
      String baseAppenderName = String.format("log4j.appender.%s",packageName);
      props.setProperty(String.format("%s.%s",baseAppenderName,DEFAULT_APPENDER_NAME),DEFAULT_CONSOLE_APPENDER_CLASS);
      props.setProperty(String.format("%s.%s.layout",baseAppenderName,DEFAULT_APPENDER_NAME),DEFAULT_LAYOUT_CLASS);
      props.setProperty(String.format("%s.%s.layout.ConversionPattern",baseAppenderName,DEFAULT_APPENDER_NAME),DEFAULT_LAYOUT_PATTERN);
      String baseLoggerName = String.format("log4j.logger.%s",packageName);
      props.setProperty(baseLoggerName,String.format("%s, %s.%s",logLevelString,packageName,DEFAULT_APPENDER_NAME));
      //AppConfigUtils.dumpMap(props);
      PropertyConfigurator.configure(props);
    }
    catch (Exception e) {
      // Should be one of the only places where the console gets written to directly - since there is no configured logging at this point
      String errorMessage = "AppConfig.LoggingHelperLog4j.loadDefaultLogger failed to configure logging.";
      System.out.println(errorMessage);
      System.out.println(e.getMessage());
      e.printStackTrace();
      throw new AppConfigException(errorMessage,e);
    }
  }

  /**
   * Load log4j configuration from a configuration file. For a given name, load only the first file in the list that is found
   * 
   * @param configNames List of possible configuration file names
   * @param loggerContext logger context in which to log loading messages
   * @return true if configuration found and loaded, false otherwise
   */
 private boolean loadLog4jConfiguration(String configName, String loggingLevel) {

    boolean configFileFound = false;

    try {
      String configFileLoaded = null;
      String loadingMethod = null;

      File configFile = new File(configName);
      logger.trace(String.format("AppConfig.LoggingHelperLog4j.loadLog4jConfiguration checking for configuration file at location %s. File %s and %s",
              configName, 
              configFile.isAbsolute() ? "is absolute" : "is relative", 
              configFile.exists() ? "exists" : "does not exist"
          ));
      if (!configFileFound && configFile.isAbsolute() && configFile.exists()) {
        PropertyConfigurator.configure(configName);
        configFileFound = true;
        configFileLoaded = configName;
        loadingMethod = "absolute";
      }
      if (!configFileFound) {
        InputStream configFileIs = getClass().getResourceAsStream(configName);
        //ClassLoader loader = LoggingHelperLog4j.class.getClassLoader();
        //URL url = loader.getResource(configName);
        //logger.trace(String.format("AppConfig.LoggingHelperLog4j.loadLog4jConfiguration checking for configuration file %s using standard classloader. File %s",configName, url == null ? "was not found" : "was found"));
        //if (url != null) {
        if (configFileIs != null) {
          logger.trace(String.format("AppConfig.LoggingHelperLog4j.loadLog4jConfiguration loading configuration file %s using resource relative to class %s",configName,this.getClass().getName() ));
          PropertyConfigurator.configure(configFileIs);
          configFileFound = true;
          configFileLoaded = configName;
          loadingMethod = "classpath";
        }
      }
      if (!configFileFound) {
        URL systemUrl = ClassLoader.getSystemResource(configName);
        logger.trace(String.format("AppConfig.LoggingHelperLog4j.loadLog4jConfiguration checking for configuration file %s using system classloader. File %s",configName, systemUrl == null ? "was not found" : "was found"));
        if (systemUrl != null) {
          PropertyConfigurator.configure(systemUrl);
          configFileFound = true;
          configFileLoaded = configName;
          loadingMethod = "system classpath";
        }
      }

      if (configFileFound) {
        logger.trace(String.format("AppConfig.LoggingHelperLog4j.loadLog4jConfiguration loaded configuration %s using %s",configFileLoaded, loadingMethod));
      }
      else {
        logger.trace(String.format("AppConfig.LoggingHelperLog4j.loadLog4jConfiguration failed to load any configuration"));
      }
    }
    catch (Exception e) {
      String errorMessage = "AppConfig.LoggingHelperLog4j.loadLog4jConfiguration failed to load application logging configuration";
      logger.error(errorMessage,e);
      throw new AppConfigException(errorMessage,e);
    }

    try {
      // Allow a logging level override for the configured logger - cheat by invoking log4j directly - safe because we're in a log4j context here
      String packageName = LoggingHelperLog4j.class.getPackage().getName();
      org.apache.log4j.Logger parentLogger = org.apache.log4j.Logger.getLogger(packageName);
      Level overrideLevel = convertToLog4jLevel(loggingLevel,null);
      parentLogger.setLevel(overrideLevel);
    }
    catch (Exception e) {
      String errorMessage = "AppConfig.LoggingHelperLog4j.loadLog4jConfiguration failed to override logging level";
      logger.warn(errorMessage,e);
    }

    return configFileFound;
  }


  
}
