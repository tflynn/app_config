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
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.slf4j.LoggerFactory;

/**
 * Get rid of those pesky Log4j warnings because of missing Log4j configuration info for certain packages. 
 * 
 * Probably should only be used for testing.
 * 
 * @author Tracy Flynn
 * @version 1.2
 * @since 1.2
 *
 */
public class Log4jHelper {

  private static Logger internalLogger = null;
  
  private static final String DEFAULT_CONSOLE_APPENDER_CLASS = "org.apache.log4j.ConsoleAppender";
  private static final String DEFAULT_LAYOUT_CLASS = "org.apache.log4j.PatternLayout";
  private static final String DEFAULT_LAYOUT_PATTERN = "%d{ISO8601}   [%-5p]    [%t]  %m %n";
  private static final String DEFAULT_LOG_LEVEL = "ERROR";
  private static final String DEFAULT_APPENDER_NAME = "CONSOLE_APPENDER";
  
  private static final String DEFAULT_INTERNAL_LOG_LEVEL = "ERROR";
  
  /**
   * Silence the loggers associated with a list of package names
   * 
   * @param packageNames List of package names (as strings)
   */
  public static void silencePackages(List<String> packageNames) {
    for (String packageName : packageNames) {
      silencePackage(packageName);
    }
  }
  
  /**
   * Silence the loggers associated with a specified package name
   * 
   * @param packageName Name of package to silence
   */
  public static void silencePackage(String packageName) {
    loadLogger(packageName,DEFAULT_LOG_LEVEL);
  }

  /**
   * Load a logger associated with a specified package name with a specified log level
   * 
   * @param packageName Name of package to log
   * @param errorLevel Logging error level (log4j style)
   */
  public static void loadLogger(String packageName, String errorLevel) {
    try {
      Properties props = new Properties();
      errorLevel = errorLevel.toUpperCase();
      String baseAppenderName = String.format("log4j.appender.%s",packageName);
      props.setProperty(String.format("%s.%s",baseAppenderName,DEFAULT_APPENDER_NAME),DEFAULT_CONSOLE_APPENDER_CLASS);
      props.setProperty(String.format("%s.%s.layout",baseAppenderName,DEFAULT_APPENDER_NAME),DEFAULT_LAYOUT_CLASS);
      props.setProperty(String.format("%s.%s.layout.ConversionPattern",baseAppenderName,DEFAULT_APPENDER_NAME),DEFAULT_LAYOUT_PATTERN);
      String baseLoggerName = String.format("log4j.logger.%s",packageName);
      props.setProperty(baseLoggerName,String.format("%s, %s.%s",errorLevel,packageName,DEFAULT_APPENDER_NAME));
      //AppConfigUtils.dumpMap(props);
      PropertyConfigurator.configure(props);
    }
    catch (Exception e) {
      // Should be one of the only places where the console gets written to directly - since there is no configured logging at this point
      String errorMessage = "AppConfig.Log4jHelper.loadLogger failed to configure logging.";
      System.out.println(errorMessage);
      System.out.println(e.getMessage());
      e.printStackTrace();
      throw new AppConfigException(errorMessage,e);
    }
  }
  
  /**
   * Load the initial internal logger 
   */
  public static void loadInitialInternalLogger(){
    loadInitialInternalLogger(DEFAULT_INTERNAL_LOG_LEVEL);
  }

  /**
   * Load the initial internal logger 
   * 
   * @param log4jLevel Logging level as Log4j Level as a string
   */
  public static void loadInitialInternalLogger(String log4jLevel){
    if (log4jLevel == null) {
      log4jLevel = DEFAULT_INTERNAL_LOG_LEVEL;
    }
    String internalPackageName = Log4jHelper.class.getPackage().getName();
    loadLogger(internalPackageName,log4jLevel);
  }
  
  /**
   * Load the 'real' internal logging settings from a properties file
   * 
   * @param internalProperties Internal settings
   */
  public static void loadInternalLogger(ExtendedProperties internalProperties) {
    try {

      // Configure the internal logger from the internal properties file
      Properties internalLoggingProps = new Properties();
      String internalLoggingPropertiesFileName = internalProperties.getProperty(ConfigurationDefinitionBuilder.INTERNAL_LOG4J_CONFIGURATION_FILE_PROPERTY_NAME);
      InputStream internalLoggingPropertiesInputStream = Log4jHelper.class.getResourceAsStream(internalLoggingPropertiesFileName);
      internalLoggingProps.load(internalLoggingPropertiesInputStream);
      internalLoggingPropertiesInputStream.close();
      // PropertyConfigurator.configure causes the actual logger configuration
      PropertyConfigurator.configure(internalLoggingProps);
      
      // Allow a logging level override for the internal logger 
      String internalPackageName = Log4jHelper.class.getPackage().getName();
      Log4jHelper.internalLogger = Logger.getLogger(internalPackageName);
      String internalLoggerLevelString = getDefaultedInternalLoggingLevel(internalProperties);
      Level internalLoggerLevel = Level.toLevel(internalLoggerLevelString);
      Log4jHelper.internalLogger.setLevel(internalLoggerLevel);
      
      if (Log4jHelper.internalLogger.isTraceEnabled())
        Log4jHelper.internalLogger.trace(String.format("AppConfig.Log4jHelper.loadInternalLogger loaded internal logger settings from properties file and overrode logging level to %s",internalLoggerLevelString));
    }
    catch (Exception e) {
      // Should be one of the only places where the console gets written to directly - since there is no configured logging at this point
      String errorMessage = "AppConfig.Log4jHelper.loadInternalLogger failed to load internal logging settings";
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
  public static boolean loadLog4jConfiguration(List<String> configNames, org.slf4j.Logger loggerContext) {

    boolean configFileFound = false;

    try {
      String configFileLoaded = null;
      String loadingMethod = null;
      
      for (String configName : configNames) {
        File configFile = new File(configName);
        Log4jHelper.internalLogger.trace(String.format("AppConfig.Log4jHelper.loadLog4jConfiguration checking for configuration file at location %s. File %s and %s",
                configName, 
                configFile.isAbsolute() ? "is absolute" : "is relative", 
                configFile.exists() ? "exists" : "does not exist"
            ));
        if (configFile.isAbsolute() && configFile.exists()) {
          PropertyConfigurator.configure(configName);
          configFileFound = true;
          configFileLoaded = configName;
          loadingMethod = "absolute";
          break;
        }
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(configName);
        Log4jHelper.internalLogger.trace(String.format("AppConfig.Log4jHelper.loadLog4jConfiguration checking for configuration file %s using standard classloader. File %s",configName, url == null ? "was not found" : "was found"));
        if (url != null) {
          PropertyConfigurator.configure(url);
          configFileFound = true;
          configFileLoaded = configName;
          loadingMethod = "classpath";
          break;
        }
        URL systemUrl = ClassLoader.getSystemResource(configName);
        Log4jHelper.internalLogger.trace(String.format("AppConfig.Log4jHelper.loadLog4jConfiguration checking for configuration file %s using system classloader. File %s",configName, systemUrl == null ? "was not found" : "was found"));
        if (systemUrl != null) {
          PropertyConfigurator.configure(url);
          configFileFound = true;
          configFileLoaded = configName;
          loadingMethod = "system classpath";
          break;
        }
      }
      
      if (configFileFound) {
        loggerContext.trace(String.format("AppConfig.loadLog4jConfiguration loaded configuration %s using %s",configFileLoaded, loadingMethod));
      }
      else {
        loggerContext.trace(String.format("AppConfig.loadLog4jConfiguration failed to load any configuration"));
      }
    }
    catch (Exception e) {
      String errorMessage = "AppConfig.ConfigurationDefinitionBuilder.oadLog4jConfiguration failed to load application logging configuration";
      loggerContext.error(errorMessage,e);
      throw new AppConfigException(errorMessage,e);
    }
    return configFileFound;
  }

  
  /*
   * Get the defaulted internal logging level
   * 
   * @return Log4j level as a string
   */
  private static String getDefaultedInternalLoggingLevel(ExtendedProperties internalProperties) {
    
    String loggingLevelPropName = ConfigurationDefinitionBuilder.DEFAULT_LOGGING_LEVEL_PROPERTY_NAME;
    
    String overrideValue = null;
    overrideValue = internalProperties.getNullProperty(loggingLevelPropName);
    if (overrideValue == null) {
      overrideValue = System.getProperty(loggingLevelPropName);
    }
    if (overrideValue == null) {
      overrideValue = System.getenv(loggingLevelPropName);
    }
    if (overrideValue == null) {
      overrideValue = DEFAULT_INTERNAL_LOG_LEVEL;
    }
    return overrideValue;
    
  }
}
