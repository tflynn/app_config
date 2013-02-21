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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

/**
 * Base class for special loggers - bootstrap and test loggers
 * 
 * These classes exist because there's no easy way to remove a logger that's already been initialized.
 * Reloading the configuration for a logger that's already been configured can lead to unpredictable results 
 * and errors attempting to access closed loggers.
 *
 * @author Tracy Flynn
 * @version 3.0
 * @since 3.0
 *
 */
public class SpecialLogger {

	public static final int OFF = 0;
	public static final int ON = 10;
	public static final int ERROR = 1;
	public static final int WARN = 2;
	public static final int INFO = 3; 
	public static final int DEBUG = 4;
	public static final int TRACE = 5;

	private static final String[] LOG_LEVEL_STRINGS = new String[]{"","ERROR","WARN ", "INFO ", "DEBUG", "TRACE"};
	
  // LOGGER_NAME is deliberately in a different namespace to all the other package loggers
  // to prevent problems with reinitializing open loggers
  private static final String LOGGER_NAME = "appconfig.specialLogger"; 
  // Actual logger name
  private String loggerName = null;
  
  // Effective logging level 
  private int level = OFF; 

  // Default logging level if none specified in override
  private static final String DEFAULT_LOGGER_LEVEL = "TRACE";
  
  public SpecialLogger() {
    setLoggerName(LOGGER_NAME);
  }

  /**
   * Set the logging level
   * 
   * @param level Level
   */
  public void setLevel(int level) {
  	this.level = level;
  }
  
  /**
   * Get the current logging level
   * 
   * @return Current logging level
   */
  public int getLevel() {
  	return this.level;
  }
  
  /**
   * Is TRACE level enabled
   * 
   * @return true if TRACE is enabled, false otherwise
   */
  public boolean isTraceEnabled() {
  	return this.level >= TRACE;
  }
  
  /*
   * Log a message checking the requested logging level
   * 
   * @param logLevel Level at which to log
   * @param msg Message to log
   */
  private void log(int logLevel,String msg) {
  	if (logLevel <= this.level) {
  		System.out.println(formatMessage(logLevel,msg));
  	}
  }
  
  /*
   * Log a message checking the requested logging level
   * 
   * @param logLevel Level at which to log
   * @param msg Message to log
   * @param e Exception
   */
  private void log(int logLevel,String msg,Exception e) {
  	if ((logLevel >= OFF) && (logLevel <= ON) ) {
	  	if (logLevel <= this.level) {
	  		log(logLevel,msg);
	  		System.out.println(e.toString());
	  		e.printStackTrace(System.out);
	  	}
  	}
  }
  
  /*
   * Format a log string - imitate logging format
   */
  private String formatMessage(int logLevel, String msg) {
  	///[%d{yyyy-MM-dd HH:mm:ss}]		[%-5p]		[%t]	%m %n
  	String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
  	String threadId = String.valueOf(Thread.currentThread().getId());
  	String logString = String.format("[%s]\t\t[%s]\t\t[%s]\t%s", dateString,LOG_LEVEL_STRINGS[logLevel],threadId,msg);
  	return logString;
  }
  
  /**
   * Log a message at the TRACE level
   * 
   * @param msg Message to log
   */
  public void trace(String msg) {
  	log(TRACE,msg);
  }
  
  /**
   * Log a message at the TRACE level
   * 
   * @param msg Message to log
   * @param e Exception
   */
  public void trace(String msg,Exception e) {
  	log(TRACE,msg,e);
  }

  /**
   * Log a message at the DEBUG level
   * 
   * @param msg Message to log
   */
  public void debug(String msg) {
    log(DEBUG,msg);
  }

  /**
   * Log a message at the DEBUG level
   * 
   * @param msg Message to log
   * @param e Exception
   * 
   */
  public void debug(String msg,Exception e) {
    log(DEBUG,msg,e);
  }

  
  /**
   * Log a message at the INFO level
   * 
   * @param msg Message to log
   */
  public void info(String msg) {
  	log(INFO,msg);
  }

  /**
   * Log a message at the INFO level
   * 
   * @param msg Message to log
   * @param e Exception
   */
  public void info(String msg,Exception e) {
  	log(INFO,msg,e);
  }
  
  
  /**
   * Log a message at the WARN level
   * 
   * @param msg Message to log
   */
  public void warn(String msg) {
  	log(WARN,msg);
  }

  /**
   * Log a message at the WARN level
   * 
   * @param msg Message to log
   * @param e Exception
   */
  public void warn(String msg,Exception e) {
  	log(WARN,msg,e);
  }
  
  /**
   * Log a message at the ERROR level
   * 
   * @param msg Message to log
   */
  public void error(String msg) {
  	log(ERROR,msg);
  }

  /**
   * Log a message at the ERROR level
   * 
   * @param msg Message to log
   * @param e Exception
   */
  public void error(String msg,Exception e) {
  	log(ERROR,msg,e);
  }

  /**
   * Set the logger name
   * 
   * @param loggerName
   */
  protected void setLoggerName(String loggerName) {
    this.loggerName = loggerName;
  }
  
  /**
   * Get the logger name
   * 
   * @return Logger name
   */
  protected String getLoggerName() {
  	return this.loggerName;
  }
  
  /**
   * Set the current log level as a string
   * 
   * @param levelString Log level as a string
   */
  public void setLevelString(String levelString) {
  	if (levelString == null) {
  		this.setLevelString(DEFAULT_LOGGER_LEVEL);
  	}
  	else {
	  	levelString = levelString.toUpperCase();
	  	for (int i = 0 ; i < LOG_LEVEL_STRINGS.length ; i++) {
	  		if (levelString.equals(LOG_LEVEL_STRINGS[i])) {
	  			this.setLevel(i);
	  			break;
	  		}
	  	}
  	}
  }
  
}
