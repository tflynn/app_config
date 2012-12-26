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
 * Configuration instance generated by CommonsConfiguration - most methods proxied from underlying org.apache.commons.configuration.CombinedConfiguration object
 * 
 * @author Tracy Flynn
 * @version 3.0
 * @since 3.0
 */
public class ConfigurationCommonsConfiguration  extends ConfigurationBase implements Configuration {

  /*
   * Logger instance
   */
  private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

  /*
   * Underlying combinedConfiguration instance
   */
  private org.apache.commons.configuration.CombinedConfiguration combinedConfiguration = new org.apache.commons.configuration.CombinedConfiguration();

  /**
   * Create a new instance
   */
  public ConfigurationCommonsConfiguration() {
  }
  
  /**
   * Create a new instance from the specified CombinedConfiguration instance
   * 
   * @param combinedConfiguration
   */
  public ConfigurationCommonsConfiguration(org.apache.commons.configuration.CombinedConfiguration combinedConfiguration) {
    this.combinedConfiguration = combinedConfiguration;
  }
  
  @Override
  public Configuration subset(String prefix) {
    return (Configuration) this.combinedConfiguration.subset(prefix);
  }

  @Override
  public boolean isEmpty() {
    return  this.combinedConfiguration.isEmpty();
  }

  @Override
  public boolean containsKey(String key) {
    return  this.combinedConfiguration.containsKey(key);
  }

  @Override
  public void addProperty(String key, Object value) {
    this.combinedConfiguration.addProperty(key, value);
  }

  @Override
  public void setProperty(String key, Object value) {
    this.combinedConfiguration.setProperty(key, value);
  }

  @Override
  public void clearProperty(String key) {
    this.combinedConfiguration.clearProperty(key);
  }

  @Override
  public void clear() {
    this.combinedConfiguration.clear();
  }

  @Override
  public Object getProperty(String key) {
    return this.combinedConfiguration.getProperty(key);
  }

  @Override
  public Iterator<String> getKeys(String prefix) {
    return this.combinedConfiguration.getKeys();
  }

  @Override
  public Iterator<String> getKeys() {
    return this.combinedConfiguration.getKeys();
  }

  @Override
  public Properties getProperties(String key) {
    return this.combinedConfiguration.getProperties(key);
  }

  @Override
  public boolean getBoolean(String key) {
    return this.combinedConfiguration.getBoolean(key);
  }

  @Override
  public boolean getBoolean(String key, boolean defaultValue) {
    return this.combinedConfiguration.getBoolean(key, defaultValue);
  }

  @Override
  public Boolean getBoolean(String key, Boolean defaultValue) {
    return this.combinedConfiguration.getBoolean(key, defaultValue);
  }

  @Override
  public byte getByte(String key) {
    return this.combinedConfiguration.getByte(key);
  }

  @Override
  public byte getByte(String key, byte defaultValue) {
    return this.combinedConfiguration.getByte(key, defaultValue);
  }

  @Override
  public Byte getByte(String key, Byte defaultValue) {
    return this.combinedConfiguration.getByte(key, defaultValue);
  }

  @Override
  public double getDouble(String key) {
    return this.combinedConfiguration.getDouble(key);
  }

  @Override
  public double getDouble(String key, double defaultValue) {
    return this.combinedConfiguration.getDouble(key, defaultValue);
  }

  @Override
  public Double getDouble(String key, Double defaultValue) {
    return this.combinedConfiguration.getDouble(key, defaultValue);
  }

  @Override
  public float getFloat(String key) {
    return this.combinedConfiguration.getFloat(key);
  }

  @Override
  public float getFloat(String key, float defaultValue) {
    return this.combinedConfiguration.getFloat(key, defaultValue);
  }

  @Override
  public Float getFloat(String key, Float defaultValue) {
    return this.combinedConfiguration.getFloat(key, defaultValue);
  }

  @Override
  public int getInt(String key) {
    return this.combinedConfiguration.getInt(key);
  }

  @Override
  public int getInt(String key, int defaultValue) {
    return this.combinedConfiguration.getInt(key, defaultValue);
  }

  @Override
  public Integer getInteger(String key, Integer defaultValue) {
    return this.combinedConfiguration.getInteger(key, defaultValue);
  }

  @Override
  public long getLong(String key) {
    return this.combinedConfiguration.getLong(key);
  }

  @Override
  public long getLong(String key, long defaultValue) {
    return this.combinedConfiguration.getLong(key, defaultValue);
  }

  @Override
  public Long getLong(String key, Long defaultValue) {
    return this.combinedConfiguration.getLong(key, defaultValue);
  }

  @Override
  public short getShort(String key) {
    return this.combinedConfiguration.getShort(key);
  }

  @Override
  public short getShort(String key, short defaultValue) {
    return this.combinedConfiguration.getShort(key, defaultValue);
  }

  @Override
  public Short getShort(String key, Short defaultValue) {
    return this.combinedConfiguration.getShort(key, defaultValue);
  }

  @Override
  public BigDecimal getBigDecimal(String key) {
    return this.combinedConfiguration.getBigDecimal(key);
  }

  @Override
  public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
    return this.combinedConfiguration.getBigDecimal(key, defaultValue);
  }

  @Override
  public BigInteger getBigInteger(String key) {
    return this.combinedConfiguration.getBigInteger(key);
  }

  @Override
  public BigInteger getBigInteger(String key, BigInteger defaultValue) {
    return this.combinedConfiguration.getBigInteger(key, defaultValue);
  }

  @Override
  public String getString(String key) {
    return this.combinedConfiguration.getString(key);
  }

  @Override
  public String getString(String key, String defaultValue) {
    return this.combinedConfiguration.getString(key, defaultValue);
  }

  @Override
  public String[] getStringArray(String key) {
    return this.combinedConfiguration.getStringArray(key);
  }

  @Override
  public List<Object> getList(String key) {
    return this.combinedConfiguration.getList(key);
  }

//  @Override
//  public List<Object> getList(String key, List<Object> defaultValue) {
//    return null;
//  }

  
}
