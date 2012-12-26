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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base Configuration class - provides null implementations of all required methods
 * 
 * @author Tracy Flynn
 * @version 3.0
 * @since 3.0
 */
public class ConfigurationBase implements Configuration {

  
  /*
   * Logger instance
   */
  private static final Logger logger = LoggerFactory.getLogger(ConfigurationBase.class);

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#subset(java.lang.String)
   */
  @Override
  public Configuration subset(String prefix) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#isEmpty()
   */
  @Override
  public boolean isEmpty() {
    return false;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#containsKey(java.lang.String)
   */
  @Override
  public boolean containsKey(String key) {
    return false;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#addProperty(java.lang.String, java.lang.Object)
   */
  @Override
  public void addProperty(String key, Object value) {
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#setProperty(java.lang.String, java.lang.Object)
   */
  @Override
  public void setProperty(String key, Object value) {
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#clearProperty(java.lang.String)
   */
  @Override
  public void clearProperty(String key) {
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#clear()
   */
  @Override
  public void clear() {
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getProperty(java.lang.String)
   */
  @Override
  public Object getProperty(String key) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getKeys(java.lang.String)
   */
  @Override
  public Iterator<String> getKeys(String prefix) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getKeys()
   */
  @Override
  public Iterator<String> getKeys() {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getProperties(java.lang.String)
   */
  @Override
  public Properties getProperties(String key) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getBoolean(java.lang.String)
   */
  @Override
  public boolean getBoolean(String key) {
    return false;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getBoolean(java.lang.String, boolean)
   */
  @Override
  public boolean getBoolean(String key, boolean defaultValue) {
    return false;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getBoolean(java.lang.String, java.lang.Boolean)
   */
  @Override
  public Boolean getBoolean(String key, Boolean defaultValue) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getByte(java.lang.String)
   */
  @Override
  public byte getByte(String key) {
    return 0;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getByte(java.lang.String, byte)
   */
  @Override
  public byte getByte(String key, byte defaultValue) {
    return 0;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getByte(java.lang.String, java.lang.Byte)
   */
  @Override
  public Byte getByte(String key, Byte defaultValue) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getDouble(java.lang.String)
   */
  @Override
  public double getDouble(String key) {
    return 0;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getDouble(java.lang.String, double)
   */
  @Override
  public double getDouble(String key, double defaultValue) {
    return 0;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getDouble(java.lang.String, java.lang.Double)
   */
  @Override
  public Double getDouble(String key, Double defaultValue) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getFloat(java.lang.String)
   */
  @Override
  public float getFloat(String key) {
    return 0;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getFloat(java.lang.String, float)
   */
  @Override
  public float getFloat(String key, float defaultValue) {
    return 0;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getFloat(java.lang.String, java.lang.Float)
   */
  @Override
  public Float getFloat(String key, Float defaultValue) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getInt(java.lang.String)
   */
  @Override
  public int getInt(String key) {
    return 0;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getInt(java.lang.String, int)
   */
  @Override
  public int getInt(String key, int defaultValue) {
    return 0;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getInteger(java.lang.String, java.lang.Integer)
   */
  @Override
  public Integer getInteger(String key, Integer defaultValue) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getLong(java.lang.String)
   */
  @Override
  public long getLong(String key) {
    return 0;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getLong(java.lang.String, long)
   */
  @Override
  public long getLong(String key, long defaultValue) {
    return 0;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getLong(java.lang.String, java.lang.Long)
   */
  @Override
  public Long getLong(String key, Long defaultValue) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getShort(java.lang.String)
   */
  @Override
  public short getShort(String key) {
    return 0;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getShort(java.lang.String, short)
   */
  @Override
  public short getShort(String key, short defaultValue) {
    return 0;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getShort(java.lang.String, java.lang.Short)
   */
  @Override
  public Short getShort(String key, Short defaultValue) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getBigDecimal(java.lang.String)
   */
  @Override
  public BigDecimal getBigDecimal(String key) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getBigDecimal(java.lang.String, java.math.BigDecimal)
   */
  @Override
  public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getBigInteger(java.lang.String)
   */
  @Override
  public BigInteger getBigInteger(String key) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getBigInteger(java.lang.String, java.math.BigInteger)
   */
  @Override
  public BigInteger getBigInteger(String key, BigInteger defaultValue) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getString(java.lang.String)
   */
  @Override
  public String getString(String key) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getString(java.lang.String, java.lang.String)
   */
  @Override
  public String getString(String key, String defaultValue) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getStringArray(java.lang.String)
   */
  @Override
  public String[] getStringArray(String key) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getList(java.lang.String)
   */
  @Override
  public List<Object> getList(String key) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.verymuchme.appconfig.Configuration#getList(java.lang.String, java.util.List)
   */
//  @Override
//  public List<Object> getList(String key, List<Object> defaultValue) {
//    return null;
//  }

  
}
