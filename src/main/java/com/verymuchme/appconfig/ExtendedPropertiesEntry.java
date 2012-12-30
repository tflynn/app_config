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
 * A single entry in an ExtendedProperties collection
 * 
 * @author Tracy Flynn
 * @version 3.0
 * @since 3.0
 * 
 */
public class ExtendedPropertiesEntry {

  public static final String TYPE_STRING = "String";
  public static final String TYPE_BOOLEAN = "Boolean";
  public static final String TYPE_ARRAY_LIST_STRING = "ArrayList.String";
  public static final String TYPE_ARRAY_LIST = "ArrayList";
  public static final String TYPE_OBJECT = "Object";

  private String entryName = null;
  private Object entryValue = null;
  private String targetType = null;
  
  public String getEntryName() {
    return this.entryName;
  }
  public void setEntryName(String entryName) {
    this.entryName = entryName;
  }
  public Object getEntryValue() {
    return this.entryValue;
  }
  public void setEntryValue(Object entryValue) {
    this.entryValue = entryValue;
  }
  public String getTargetType() {
    return this.targetType;
  }
  public void setTargetType(String targetType) {
    this.targetType = targetType;
  }
  
  
}
