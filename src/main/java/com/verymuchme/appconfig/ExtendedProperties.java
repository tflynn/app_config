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

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrao standard Properties object with a few convenience features
 * 
 * @author Tracy Flynn
 * @version 2.0
 * @since 2.0
 * 
 */
public class ExtendedProperties extends Properties {

  /*
   * Logger instance
   */
  private static final Logger logger = LoggerFactory.getLogger(ExtendedProperties.class);

  /**
   * Creates an empty property list with no default values.
   */
  public ExtendedProperties() {
    super();
  }
  
  /**
   * Creates an empty property list with the specified defaults.
   * 
   * @param defaults
   */
  public ExtendedProperties(Properties defaults) {
    super(defaults);
  }
  
  /*
   * Convert properties using extended syntax for values to objects as needed 
   */
  public void convertProperties() {
    Properties replacementProperties = new Properties();
    @SuppressWarnings("unchecked")
    Enumeration<String> propertyNames = (Enumeration<String>) this.propertyNames();
    while (propertyNames.hasMoreElements()) {
      Object propertyValue = NullObject.INSTANCE;
      String propertyName = propertyNames.nextElement();
      String rawPropertyValue = this.getProperty(propertyName);
      if (rawPropertyValue == null) {
        propertyValue = NullObject.INSTANCE;
      }
      else if (AppConfigUtils.isNullStringValue(rawPropertyValue)) {
        propertyValue = NullObject.INSTANCE;
      }
      else if (AppConfigUtils.isListValue(rawPropertyValue)) {
        propertyValue = AppConfigUtils.getListValue(rawPropertyValue);
      }
      else if (AppConfigUtils.isBooleanValue(rawPropertyValue)) {
        propertyValue = AppConfigUtils.getBooleanValue(rawPropertyValue);
      }
      else {
        propertyValue = rawPropertyValue;
      }
      try {
        if (propertyValue == NullObject.INSTANCE) {
          logger.trace(String.format("AppConfig.ExtendedProperties.convertProperties found a property without a value %s",propertyName));
        }
        replacementProperties.put(propertyName,propertyValue);
      }
      catch (Exception e) {
        String errorMsg = String.format("AppConfig.ExtendedProperties.convertProperties error");
        logger.trace(errorMsg,e);
        throw new AppConfigException(errorMsg,e);
      }
    }
    // Replace all properties with the converted properties
    this.putAll(replacementProperties);
  }

  @Override
  public String getProperty(String key) {
    Object oVal = super.get(key);
    if (oVal == null || oVal == NullObject.INSTANCE) {
      return null;
    }
    else {
      return super.getProperty(key);
    }
  }
  
  /**
   * Get a property as a boolean. 
   * Returns the value true if the string argument is not null and is equal, ignoring case, to the string "true". 
   * Returns false otherwise.
   * 
   * @param key Property name
   * @return true or false
   */
  public boolean getBooleanProperty(String key) {
    if (this.get(key) == NullObject.INSTANCE) {
      return false;
    }
    else {
      return AppConfigUtils.getBooleanValue(getProperty(key));
    }
  }
  
  /**
   * Get a property as a null value.
   * Returns the value null either if the property is undefined or if the property argument is not null and is equal, ignoring case, to the string "null".
   * Returns the value otherwise.
   * 
   * @param key Property name
   * @return the value or null
   */
  public String getNullProperty(String key) {
    if (this.get(key) == NullObject.INSTANCE) {
      return null;
    }
    else {
      return AppConfigUtils.getNullValue(getProperty(key));
    }
  }
  
  /**
   * Get a property as an ArrayList<String>. Uses ',' as the default separator. 
   * 
   * @param key Property name
   * 
   * @return Array of string values, or null if no property present
   */
  @SuppressWarnings("unchecked")
  public ArrayList<String> getListProperty(String key) {
    if (this.get(key) == NullObject.INSTANCE) {
      return null;
    }
    else {
      return (ArrayList<String>) this.get(key);
    }
  }
  
  /**
   * Get a property as a String value. Return null if unable to convert property to string
   * 
   * @param key Property name
   * 
   * @return Object as string value or null if conversion failed
   */
  public String getStringProperty(String key) {
    if (this.get(key) == NullObject.INSTANCE) {
      return null;
    }
    else {
      String returnString = AppConfigUtils.getStringValue(getProperty(key));
      return returnString == "" ? null : returnString;
    }
  }
  
  
}
