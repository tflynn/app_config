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
import java.io.StringWriter;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * Manage template for Apache Commons Configuration DefaultConfigurationBuilder
 * 
 * @author Tracy Flynn
 * @version 2.0
 * @since 1.2
 *
 */
public class FreemarkerHandler {

  /*
   * Logger instance
   */
  private static final Logger logger = LoggerFactory.getLogger(FreemarkerHandler.class);
 
  /*
   * Local Freemarker configuration instance
   */
  private Configuration configuration = null;
  
  /*
   * Load relative to this base class
   */
  private Class classBase = null;
  
  /*
   * Internal Properties from ConfigurationDefinitionBuilder
   */
  private ExtendedProperties internalProperties = null;
  
  
  /**
   * Create a new FreemarkerHandler instance. Load relative to this class (FreemarkerHandler)
   */
  public FreemarkerHandler() {
    this(FreemarkerHandler.class);
  }
  /**
   * Create a new FreemarkerHandler instance
   * 
   * @param classBase Base class to use to find template
   */
  public FreemarkerHandler(Class classBase) {
    
    this.classBase = classBase;
    
    this.configuration = new Configuration();
    this.configuration.setClassForTemplateLoading(this.classBase, "");
    this.configuration.setObjectWrapper(new DefaultObjectWrapper());  
    
  }
  
  /**
   * Get the specified template
   * 
   * @param templateName Name of template to load
   * @param data Data hash to be used for the template
   * @return Filled out template if found, null if any errors
   */
  public String getTemplate(String templateName, HashMap data) {
    
    if (templateName == null) {
      templateName = internalProperties.getProperty(ConfigurationDefinitionBuilder.DEFAULT_FREEMARKER_CONFIGURATION_TEMPLATE_PROPERTY_NAME);
    }
    
    Template template = null;
    try {
      template = this.configuration.getTemplate(templateName);
    }
    catch (IOException ioe) {
      String errorMessage = String.format("FreemarkerHandler: failed to load template %s relative to class %s", templateName, this.classBase.getName() );
      logger.error(errorMessage,ioe);
      throw new AppConfigException(errorMessage,ioe);
    }
    String templateContents = null;
    try {
      if (template != null) {
        StringWriter outputWriter = new StringWriter();
        template.process(data, outputWriter);
        templateContents = outputWriter.toString();
      }
    }
    catch (Exception e) {
      String errorMessage = String.format("FreemarkerHandler: failed to generate template %s", templateName );
      logger.error(errorMessage,e);
      throw new AppConfigException(errorMessage,e);
    }
    return templateContents;
  }

  /**
   * Get the internal properties
   * 
   * @return Internal properties
   */
  public ExtendedProperties getInternalProperties() {
    return internalProperties;
  }
  
  /**
   * Set the internal properties
   * 
   * @param internalProperties Internal Properties
   */
  public void setInternalProperties(ExtendedProperties internalProperties) {
    this.internalProperties = internalProperties;
  }

  
  
}
