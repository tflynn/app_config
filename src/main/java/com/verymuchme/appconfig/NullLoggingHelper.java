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
 * A class that presents a null LoggingHelper
 * 
 * @author Tracy Flynn
 * @version 3.0
 * @since 3.0
 */
import java.util.List;

import org.slf4j.Logger;

public class NullLoggingHelper implements LoggingHelper {
  
  public static final NullLoggingHelper INSTANCE = new NullLoggingHelper();
  
  public static NullLoggingHelper getInstance() {
    return NullLoggingHelper.INSTANCE;
  }
  
  private NullLoggingHelper() {
    
  }

  @Override
  public void bootstrapInternalLogging() {
  }

  @Override
  public void bootstrapInternalLogging(String loggingLevel) {
  }

  @Override
  public void quietLoggingInitializationMessages(List<String> packageNames,String logLevelString) {
  }

  @Override
  public void quietLoggingInitializationMessages(String packageName,String logLevelString) {
  }

  @Override
  public void quietLoggingInitializationMessages(List<String> packageNames) {
  }

  @Override
  public void quietLoggingInitializationMessages(String packageName) {
  }

  @Override
  public void configureLoggerFromConfigurationFile(String loggingConfigurationFileName, String loggingLevel) {
  }

  @Override
  public void bootstrapInternalLogging(String loggerName,
      String loggingLevelString) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void configureLoggerFromConfigurationFile(
      String loggingConfigurationFileName, String loggingLevel,
      Logger errorLogger) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void configureLoggerFromConfigurationFiles(
      List<String> loggingConfigurationFileNames) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void overrideLogLevel(String loggerName, String loggingLevel) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void configureLoggerFromConfigurationFile(
      String loggingConfigurationFileName, String loggingLevel,
      Logger activeLogger, Class classContext) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setExtendedProperties(ExtendedProperties extendedProperties) {
    // TODO Auto-generated method stub
    
  }
  
}
