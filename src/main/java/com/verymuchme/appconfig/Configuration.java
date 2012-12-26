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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Configuration interface - copy of org.apache.commons.configuration.Configuration with some methods removed
 * 
 * @author Tracy Flynn
 * @version 3.0
 * @since 3.0
 */
public interface Configuration {

  public Configuration subset(String prefix);

  public boolean isEmpty();

  public boolean containsKey(String key);

  public void addProperty(String key, Object value);

  public void setProperty(String key, Object value);

  public void clearProperty(String key);

  public void clear();

  public Object getProperty(String key);

  public Iterator<String> getKeys(String prefix);

  public Iterator<String> getKeys();

  public Properties getProperties(String key);

  public boolean getBoolean(String key);

  public boolean getBoolean(String key, boolean defaultValue);

  public Boolean getBoolean(String key, Boolean defaultValue);

  public byte getByte(String key);

  public byte getByte(String key, byte defaultValue);

  public Byte getByte(String key, Byte defaultValue);

  public double getDouble(String key);

  public double getDouble(String key, double defaultValue);

  public Double getDouble(String key, Double defaultValue);

  public float getFloat(String key);

  public float getFloat(String key, float defaultValue);

  public Float getFloat(String key, Float defaultValue);

  public int getInt(String key);

  public int getInt(String key, int defaultValue);

  public Integer getInteger(String key, Integer defaultValue);

  public long getLong(String key);

  public long getLong(String key, long defaultValue);

  public Long getLong(String key, Long defaultValue);

  public short getShort(String key);

  public short getShort(String key, short defaultValue);

  public Short getShort(String key, Short defaultValue);

  public BigDecimal getBigDecimal(String key);

  public BigDecimal getBigDecimal(String key, BigDecimal defaultValue);

  public BigInteger getBigInteger(String key);

  public BigInteger getBigInteger(String key, BigInteger defaultValue);

  public String getString(String key);

  public String getString(String key, String defaultValue);

  public String[] getStringArray(String key);

  public List<Object> getList(String key);

  //public List<Object> getList(String key, List<Object> defaultValue);

}