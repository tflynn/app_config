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

import java.util.ArrayList;
import java.util.Properties;

/**
 * Wrao standard Properties object with a few convenience features
 * 
 * @author Tracy Flynn
 * @version 2.0
 * @since 2.0
 * 
 */
public class ExtendedProperties extends Properties {

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
  
  /**
   * Get a property as a boolean. 
   * Returns the value true if the string argument is not null and is equal, ignoring case, to the string "true". 
   * Returns false otherwise.
   * 
   * @param key Property name
   * @return true or false
   */
  public boolean getBooleanProperty(String key) {
    return AppConfigUtils.getBooleanValue(getProperty(key));
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
    return AppConfigUtils.getNullValue(getProperty(key));
  }
  
  /**
   * Get a property as an ArrayList<String>. Uses ',' as the default separator. 
   * 
   * @param key Property name
   * 
   * @return Array of string values, or null if no property present
   */
  public ArrayList<String> getListProperty(String key) {
    return getListProperty(key,",");
  }
  
  /**
   * Get a property as an ArrayList<String> using the specified separator to split the string. 
   * 
   * @param key Property name
   * @param separator Separator 
   * 
   * @return Array of string values, or null if no property present
   */
  public ArrayList<String> getListProperty(String key, String separator) {
    return AppConfigUtils.getListValue(getProperty(key),separator);
  }
  
}
