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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LogbackFactory;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class LoggingHelperLogback implements LoggingHelper {

	/*
	 * Logger instance
	 */
	private static Logger logger = null;


	private static final Level DEFAULT_INTERNAL_BOOTSTRAP_LOG_LEVEL = Level.ERROR;
	//private static final Level DEFAULT_INTERNAL_BOOTSTRAP_LOG_LEVEL = Level.TRACE;
	
	private static final Level DEFAULT_INTERNAL_LOG_LEVEL = Level.ERROR;
	  
	private static final String DEFAULT_CONSOLE_APPENDER_CLASS = "ch.qos.logback.core.ConsoleAppender";
	private static final String DEFAULT_LAYOUT_CLASS = "ch.qos.logback.classic.PatternLayout";
	private static final String DEFAULT_LAYOUT_PATTERN = "[%d{yyyy-MM-dd HH:mm:ss}]\t\t[%-5p]\t\t[%t:%c{1}]\t%m %n";
	  
	private static final String DEFAULT_APPENDER_NAME = "CONSOLE_APPENDER";

	private ExtendedProperties internalProperties = null;
	
  private Options options = new Options();

  /**
   * Create a new instance of the helper
   */
  public LoggingHelperLogback(){
    
  }
  
  /**
   * Create a new instance of the helper using the specified options
   * 
   * @param options
   */
  public LoggingHelperLogback(Options options) {
    this.options = options;
  }
  
  

	@Override
	public void bootstrapInternalLogging() {
		bootstrapInternalLogging(null);
	}

	@Override
	public void bootstrapInternalLogging(String loggingLevelString) {
    String internalPackageName = LoggingHelperLogback.class.getPackage().getName();
    bootstrapInternalLogging(internalPackageName,loggingLevelString);
	}

	@Override
	public void bootstrapInternalLogging(String loggerName,String loggingLevelString) {
		if (loggingLevelString == null) {
			loggingLevelString = DEFAULT_INTERNAL_BOOTSTRAP_LOG_LEVEL.toString();
		}
		loadLogbackLogger(loggerName,loggingLevelString,Boolean.TRUE);		
	}

	@Override
	public void quietLoggingInitializationMessages(List<String> loggerNames) {
		quietLoggingInitializationMessages(loggerNames, null);
	}

	@Override
	public void quietLoggingInitializationMessages(List<String> loggerNames,
			String logLevelString) {
    for (String loggerName : loggerNames) {
      quietLoggingInitializationMessages(loggerName,logLevelString);
    }
	}

	@Override
	public void quietLoggingInitializationMessages(String loggerName) {
		quietLoggingInitializationMessages(loggerName,null);
	}

	@Override
	public void quietLoggingInitializationMessages(String loggerName,
			String logLevelString) {
		//TODO Figure out whether silencing works for LogBack
		//loadLogbackLogger(loggerName,logLevelString);
		
	}

	@Override
	public void configureLoggerFromConfigurationFiles(
			List<String> loggingConfigurationFileNames) {
    for (String loggingConfigurationFileName : loggingConfigurationFileNames) {
      Object contextClassObj = this.internalProperties.get(InternalConfigurationConstants.APPLICATION_LOGGING_CONTEXT_CLASS_PROPERTY_NAME);
      Class contextClass = contextClassObj == null ? null : ((Class) contextClassObj);
      if (contextClassObj == null) {
        logger.trace(String.format("AppConfig.LoggingHelperLogback.configureLoggerFromConfigurationFiles contextClassObj is null. Property %s not set",InternalConfigurationConstants.APPLICATION_LOGGING_CONTEXT_CLASS_PROPERTY_NAME));
      }
      else {
        logger.trace(String.format("AppConfig.LoggingHelperLogback.configureLoggerFromConfigurationFiles context class found and cast to %s",contextClass.getName()));
      }
      // Stop after first successful load
      boolean loaded = loadLogbackConfiguration(loggingConfigurationFileName,null,null,contextClass,Boolean.FALSE);
      if (loaded) break;
    }
	}

	@Override
	public void configureLoggerFromConfigurationFile(
			String loggingConfigurationFileName, String loggingLevel) {
    configureLoggerFromConfigurationFile(loggingConfigurationFileName, loggingLevel,LogbackFactory.getLogger(LoggingHelperLogback.class));
	}

	@Override
	public void configureLoggerFromConfigurationFile(
			String loggingConfigurationFileName, String loggingLevel,
			Logger activeLogger) {
    configureLoggerFromConfigurationFile(loggingConfigurationFileName, loggingLevel,activeLogger,null);
	}

	@Override
	public void configureLoggerFromConfigurationFile(
			String loggingConfigurationFileName, String loggingLevel,
			Logger activeLogger, Class classContext) {
		loadLogbackConfiguration(loggingConfigurationFileName, loggingLevel,activeLogger,classContext,Boolean.FALSE);
		
	}

	@Override
	public void overrideLogLevel(String loggerName, String loggingLevelString) {
		Level loggingLevel = Level.toLevel(loggingLevelString,DEFAULT_INTERNAL_LOG_LEVEL);
		Logger localLogger = LogbackFactory.getLogger(loggerName);
		localLogger.setLevel(loggingLevel);
	}

	@Override
	public void setExtendedProperties(ExtendedProperties internalProperties) {
		this.internalProperties = internalProperties;
	}
	
  /**
   * Load a Logback logger configuration associated with a specified logger name with a specified log level
   * 
   * @param loggerName Name of logger to log
   * @param logLevel Logging error level (Logback style)
   * @param reset if true, reset the logging system
   */
  private void loadLogbackLogger(String loggerName, String logLevelString,Boolean reset) {
  	
  	if (logLevelString == null) {
  		logLevelString = DEFAULT_INTERNAL_LOG_LEVEL.toString();
  	}
  	
    try {

    	JoranConfigurator configurator = new JoranConfigurator();
	    LoggerContext loggerContext = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
	    configurator.setContext(loggerContext);
	    if (reset != null && reset) {
	    	loggerContext.reset();
	    }

			StringBuilder props = new StringBuilder();
			props.append("<configuration>\n");
			props.append(String.format("  <appender class=\"%s\" name=\"%s.%s\">\n",DEFAULT_CONSOLE_APPENDER_CLASS,DEFAULT_APPENDER_NAME,loggerName));
			props.append(String.format("    <encoder>\n",DEFAULT_LAYOUT_CLASS));
			props.append(String.format("      <pattern>%s</pattern>\n",DEFAULT_LAYOUT_PATTERN));
			props.append("    </encoder>\n");
			props.append("  </appender>\n");
			props.append(String.format("  <logger name=\"%s\" additivity=\"false\">\n",loggerName));
			props.append(String.format("    <level value=\"%s\"/>\n",logLevelString));
			props.append(String.format("    <appender-ref ref=\"%s.%s\" />\n",DEFAULT_APPENDER_NAME,loggerName));
			props.append("  </logger>\n");
			props.append("</configuration>\n");
      
      InputStream configFileIs = new ByteArrayInputStream(props.toString().getBytes("UTF-8"));
      configurator.doConfigure(configFileIs);
      
      setActiveLogger();
      logger.trace("AppConfig.LoggerHelperLogback.loadLogbackLogger just loaded internal configuration");
      logger.trace("\n" + props.toString());

    }
    catch (Exception e) {
      // Should be one of the only places where the console gets written to directly - since there is no configured logging at this point
      String errorMessage = String.format("AppConfig.LoggingHelperLogback.loadLogbackLogger failed to configure logging for %s at logging level %s",loggerName,logLevelString);
      System.out.println(errorMessage);
      System.out.println(e.getMessage());
      e.printStackTrace();
      throw new AppConfigException(errorMessage,e);
    }
  }

	
	
	
	/**
	 * Load Logback configuration from a configuration file. For a given name, load only the first file in the list that is found
	 * 
	 * @param configName Configuration file name
	 * @param loggingLevel level for the new logger
	 * @param activeLogger logger to use for logging until logger is fully configured
	 * @param classContext Class context for loading resources. If null, current class is used
   * @param reset if true, reset the logging system
	 * @return true if configuration found and loaded, false otherwise
	 */
	private boolean loadLogbackConfiguration(String configName, String loggingLevel, Logger activeLogger, Class classContext,Boolean reset) {

		setActiveLogger();

		if (logger.isTraceEnabled()) {
			logger.trace(String.format("AppConfig.LoggingHelperLogback.loadLogbackConfiguration classContext %s", classContext == null ? "null" : classContext.getName()));
		}

	  boolean configFileFound = false;
	  String configFileLoaded = null;
	  String loadingMethod = null;
    	   
	  try {
	    configFileLoaded = null;
	    loadingMethod = null;
	    JoranConfigurator configurator = new JoranConfigurator();
	    LoggerContext loggerContext = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
	    configurator.setContext(loggerContext);
	    if (reset != null && reset) {
	    	loggerContext.reset();
	    }

	    File configFile = new File(configName);
	    
	    
	    if (logger.isTraceEnabled()) {
				logger.trace(String.format("AppConfig.LoggingHelperLogback.loadLogbackConfiguration checking for configuration file at location %s. File %s and %s",
            configName, 
            configFile.isAbsolute() ? "is absolute" : "is relative", 
            configFile.exists() ? "exists" : "does not exist"
        ));
				
			}
	    if (!configFileFound && configFile.isAbsolute() && configFile.exists()) {
	      configurator.doConfigure(configFile);
	      configFileFound = true;
	      configFileLoaded = configName;
	      loadingMethod = "absolute";
	    }
	    if (!configFileFound) {
	    	if (logger.isTraceEnabled()) {
	    		logger.trace(String.format("AppConfig.LoggingHelperLogback.loadLogbackConfiguration checking for configuration file %s relative to class %s",configName,classContext == null ? this.getClass().getName() : classContext.getName()));
	    	}
	      InputStream configFileIs = null;
	      if (classContext == null) {
	        configFileIs = getClass().getResourceAsStream(configName);
	      }
	      else {
	        configFileIs = classContext.getResourceAsStream(configName);
	      }
	      if (configFileIs != null) {
	      	if (logger.isTraceEnabled()) {
		    		logger.trace(String.format("AppConfig.LoggingHelperLogback.loadLogbackConfiguration loading configuration file %s using resource relative to class %s",configName,classContext == null ? this.getClass().getName() : classContext.getName()));
		    	}
	        configurator.doConfigure(configFileIs);
	        configFileFound = true;
	        configFileLoaded = configName;
	        loadingMethod = "classpath";
	      }
	    }
	    if (!configFileFound) {
	      URL systemUrl = ClassLoader.getSystemResource(configName);
	      if (logger.isTraceEnabled()) {
	    		logger.trace(String.format("AppConfig.LoggingHelperLogback.loadLogbackConfiguration checking for configuration file %s using system classloader. File %s",configName, systemUrl == null ? "was not found" : "was found"));
	    	}
	      if (systemUrl != null) {
	        configurator.doConfigure(systemUrl);
	        configFileFound = true;
	        configFileLoaded = configName;
	        loadingMethod = "system classpath";
	      }
	    }

	  }
	  catch (Exception e) {
	    String errorMessage = "AppConfig.LoggingHelperLogback.loadLogbackConfiguration failed to load application logging configuration";
	  	logger.error(errorMessage,e);
	    throw new AppConfigException(errorMessage,e);
	  }

	  if (logger.isTraceEnabled()) {
		  if (configFileFound) {
		  	logger.trace(String.format("AppConfig.LoggingHelperLogback.loadLogbackConfiguration loaded configuration %s using %s",configFileLoaded, loadingMethod));
		  }
		  else {
		  	logger.trace(String.format("AppConfig.LoggingHelperLogback.loadLogbackConfiguration failed to load any configuration"));
		  }
	  }
	    
	  return configFileFound;
	}

  /**
   * Set the active logger for this class
   */
  public static void setActiveLogger() {
    setActiveLogger(null);
  }
  
  /**
   * Set the active logger for this class
   * 
   * @param activeLogger
   */
  public static void setActiveLogger(Logger activeLogger) {
    logger = activeLogger == null ? LogbackFactory.getLogger(LoggingHelperLogback.class) : activeLogger;
  }
	
	
}
