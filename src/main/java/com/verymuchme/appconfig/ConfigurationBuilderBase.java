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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for all ConfigurationBuilder classes
 * 
 * @author Tracy Flynn
 * @version 3.0
 * @since 3.0
 */
public class ConfigurationBuilderBase {

  /*
   * Logger instance
   */
  private static final Logger logger = LoggerFactory.getLogger(ConfigurationBuilderBase.class);

  /*
   * Internal defaults
   */
  protected ExtendedProperties internalProperties = null;

  public void setInternalProperties(ExtendedProperties internalProperties) {
    this.internalProperties= internalProperties;
  }

}
