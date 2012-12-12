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

/*
 * Basic logging before standard logging is enabled.
 * 
 * Settings available from the Java/JVM command-line and the environment. The command-line value always overrides the environment value.
 * 
 * <ul>
 * <li>'com.verymuchme.appconfig.consoleLog' - Enable/disable bootstrap console logging</li>
 * </ul> 
 * 
 * @author Tracy Flynn
 * @version 1.0
 * @since 1.0
 */
public class BootstrapLogger {
  
  private static final String CONSOLE_LOG_ENABLED_PROPERTY_NAME = "com.verymuchme.appconfig.consoleLog";
  
  /**
   * Console tracing state
  */
  private boolean consoleLog = false;

  /**
   * Create a new BootstrapLogger instance
   * 
   */
  public BootstrapLogger() {
    super();
    
    String consoleLogEnabled = AppConfig.sSystemOrEnvironmentValue(CONSOLE_LOG_ENABLED_PROPERTY_NAME);
    if (consoleLogEnabled != null && consoleLogEnabled.equalsIgnoreCase("true")) {
      this.consoleLog = true;
    }
    else {
      this.consoleLog = false;
    }
  }
  
  /**
   * Is console logging enabled?
   * 
   * @return true if enabled, false otherwise
   */
  public boolean isConsoleLogging() {
    return consoleLog;
  }

  /**
   * Set the console logging state
   * 
   * @param consoleLogging if true, enable console logging, if false, disable.
   */
  public void setConsoleLogging(boolean consoleLogging) {
    this.consoleLog = consoleLogging;
  }

  /**
   * Log a message at the DEBUG level
   *
   * @param msg the message string to be logged
   */
  public void debug(String msg) {
    consoleLog(msg);
  }

  /**
   * Log a message at the ERROR level
   *
   * @param msg the message string to be logged
   */
  public void error(String msg) {
    consoleLog(msg);
  }

  /**
   * Log a message at the INFO level
   *
   * @param msg the message string to be logged
   */
  public void info(String msg) {
    consoleLog(msg);
  }

  /**
   * Log a message at the TRACE level
   *
   * @param msg the message string to be logged
   */
  public void trace(String msg) {
    consoleLog(msg);
  }

  
  /**
   * Log a message at the WARN level
   *
   * @param msg the message string to be logged
   */
  public void warn(String msg) {
    consoleLog(msg);
  }

  /**
   * Send a message to the console
   * 
   * @param msg the message string to be logged
   */
  public void consoleLog(String msg) {
    if (this.consoleLog) {
        //String[] parts = this.toString().split("@");
        //System.out.println("consoleLog: (" + parts[0] + ") " + msg);
        System.out.println("consoleLog: " + msg);
        System.out.flush();
    }
  }
}
