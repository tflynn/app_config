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

public class NullConfigurationBuilder implements ConfigurationBuilder {

  public static final NullConfigurationBuilder INSTANCE = new NullConfigurationBuilder();
  
  public static NullConfigurationBuilder getInstance() {
    return NullConfigurationBuilder.INSTANCE;
  }
  
  private NullConfigurationBuilder() {
    
  }

  @Override
  public void setInternalProperties(ExtendedProperties internalProperties) {
  }

  @Override
  public Configuration buildConfiguration() {
    // TODO Auto-generated method stub
    return null;
  }
}
