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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Useful string conversion tools
 * 
 * @author Tracy Flynn
 * @version 2.0
 * @since 2.0
 * 
 */
public class AppConfigUtils {
  
  public static final String DEFAULT_LIST_SEPARATOR = ",";
  
  public static final String STRING_VALUE_NULL = "null";
  
  
  /**
   * Get a string as a boolean. 
   * Returns the value true if the string argument is not null and is equal, ignoring case, to the string "true". 
   * Returns false otherwise.
   * 
   * @param value Original string
   * @return true or false
   */
  public static boolean getBooleanValue(String value) {
    Boolean returnValue = new Boolean(value);
    return returnValue.booleanValue();
  }
  
  /**
   * Convert string possibly containing a 'null' string to a null value
   * 
   * Returns the value null  if the property argument is not null and is equal, ignoring case, to the string "null".
   * Returns the value otherwise.
   * 
   * @param value Original string
   * @return the value or null
   */
  public static String getNullValue(String value) {
    String returnValue = value;
    if (value != null && isNullStringValue(value)) {
      returnValue = null;
    }
    return returnValue;
  }
  
  /**
   * Check to see whether the value is the literal string "null"
   * 
   * @param value Value to check
   * @return true if the value is the literal string "null", false otherwise
   */
  public static boolean isNullStringValue(String value) {
    return value.equalsIgnoreCase(STRING_VALUE_NULL);
  }

  /**
   * Check to see whether the value represents a real boolean value - 'true' or 'false'
   * 
   * @param value Value to check
   * @return true if a boolean value, false otherwise
   */
  public static boolean isBooleanValue(String value) {
    return (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"));
  }
  
  /**
   * Does the specified string value represent a delimited list with a default delimiter of ','
   * 
   * @param value String value to test
   * @return true if the default delimiter was found in the string value, false otherwise
   */
  public static boolean isListValue(String value) {
    return isListValue(value,DEFAULT_LIST_SEPARATOR);
  }
  
  /**
   * Does the specified string value represent a delimited list with the specified delimiter
   * 
   * @param value String value to test
   * @param separator Separator to use
   * @return true if the delimiter was found in the string value, false otherwise
   */
  public static boolean isListValue(String value, String separator) {
    return value.indexOf(separator) > -1 ; 
  }
  /**
   * Convert a delimited string value to an ArrayList<String>. Uses ',' as the default separator. 
   * 
   * @param value Original string
   * @return Array of string values, or null if no values are present
   */
  public static ArrayList<String> getListValue(String value) {
    return getListValue(value,DEFAULT_LIST_SEPARATOR);
  }
  
  /**
   * Get a delimited string value as an ArrayList<String> using the specified separator to split the string. 
   * 
   * @param value Original string
   * @param separator Separator 
   * 
   * @return Array of string values, or null if no values are present
   */
  public static ArrayList<String> getListValue(String value, String separator) {
    ArrayList<String> returnList = new ArrayList<String>();
    if (value != null && separator != null) {
      String[] stringValues = value.split(separator);
      for (int i = 0 ; i < stringValues.length ; i++) {
        returnList.add(stringValues[i]);
      }
    }
    if (returnList.size() == 0) {
      returnList = null;
    }
    return returnList;
  }
  

  /**
   * Dump a properties file to System.out
   * 
   * @param properties Properties file
   */
  public static void dumpProperties(Properties properties) {
    dumpProperties(properties, System.out);
  }
  
  /**
   * Dump a properties file to the specified output
   * 
   * @param properties Properties file
   * @param out PrintStream
   */
  public static void dumpProperties(Properties properties, PrintStream out) {
    String lineTerm = System.getProperty("line.separator");
    Set<String> propertyNames = properties.stringPropertyNames();
    Iterator<String> propertyNamesIt = propertyNames.iterator();
    while (propertyNamesIt.hasNext()) {
      String propertyName = propertyNamesIt.next();
      String propertyVal = properties.getProperty(propertyName);
      out.print(String.format("%s=%s%s", propertyName, propertyVal,lineTerm));
    }
  }

  /**
   * Dump a HashMap<String,String> to System.out
   * 
   * @param map HashMap<String,String>
   */
  public static void dumpMap(HashMap<String,String> map) {
    dumpMap(map,System.out);
  }
  
  /**
   * Dump a HashMap<String,String> to the specified PrintStream
   * 
   * @param map HashMap<String,String>
   * @param out PrintStream
   */
  public static void dumpMap(HashMap<String,String> map, PrintStream out) {
    String lineTerm = System.getProperty("line.separator");
    Iterator<String> it = map.keySet().iterator();
    while (it.hasNext()) {
      String currentKey = it.next();
      String currentValue = map.get(currentKey);
      out.print(String.format("%s=%s%s",currentKey,currentValue,lineTerm));
    }
  }
  
  /**
   * Dump a Map to System.out
   * 
   * @param map Map to dump
   */
  public static void dumpMap(Map<Object,Object> map) {
    dumpMap(map,System.out);
  }
  
  /**
   * Dump a Map to the specified PrintStream
   * 
   * @param map Map to dump
   * @param out Output PrintStream
   */
  @SuppressWarnings("unchecked")
  public static void dumpMap(Map<Object,Object> map, PrintStream out) {
    String lineTerm = System.getProperty("line.separator");
    Iterator<Object> it = map.keySet().iterator();
    while (it.hasNext()) {
      String currentKey = it.next().toString();
      String currentValue = map.get(currentKey).toString();
      out.print(String.format("%s=%s%s",currentKey,currentValue,lineTerm));
    }
  }
  
}
