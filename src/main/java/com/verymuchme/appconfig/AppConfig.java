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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
*
*<h1>AppConfig - Application configuration the easy way</h1>
*
*<h2>A thin wrapper for Apache Commons Configuration</h2>
*
*<p>Copyright 2009-2013 Tracy Flynn. All rights reserved. Licensed under the Apache License, Version 2.0.</p>
*
*<h2>Introduction</h2>
*
*<p>AppConfig is designed to provide painless multi-environment, external and internal configuration with default settings for components and programs.</p>
*
*<h2>Typical usage</h2>
*
*<h3>Create a configuration directory</h3>
*
*<pre><code>  mkdir ~/local_appconfig
*</code></pre>
*
*<h3>Tell AppConfig where the directory is</h3>
*
*<p>Either</p>
*
*<pre><code>  export com.verymuchme.appconfig.externalConfigurationDirectory=~/local_appconfig
*</code></pre>
*
*<p>or</p>
*
*<pre><code>  java . . . -Dcom.verymuchme.appconfig.externalConfigurationDirectory=~/local_appconfig . . .
*</code></pre>
*
*<h3>Define the database settings (if needed)</h3>
*
*<p>If the component or program requires database information, create a suitable &#39;database-[environment].properties&#39; file in the configuration directory. (See below).</p>
*
*<h3>Initialize AppConfig in your code</h3>
*
*<pre><code>AppConfig.sConfigure();
*</code></pre>
*
*<h3>Results</h3>
*
*<p>The following files will be loaded in the order shown. </p>
*
*<pre><code>  application-development.properties (optional)
*  database-development.properties (optional)
*  log4j-development.properties (optional)
*  application-defaults.properties
*  log4j-defaults.properties
*</code></pre>
*
*<p>For each file, the search order is the following:</p>
*
*<ul>
*<li>the configuration directory (if defined)</li>
*<li>the user directory</li>
*<li>any jars in the classpath</li>
*<li>any jars in the system classpath</li>
*</ul>
*
*<p>In general, if a file is shown, it must be present in one of the locations or an exception will be thrown. &#39;Optional&#39; indicates that the file can be absent.</p>
*
*<h2>The details</h2>
*
*<p>AppConfig is designed to provide painless multi-environment, external and internal configuration with default settings for components and programs.</p>
*
*<p>AppConfig automatically names, finds and loads environment-specific and default application, logging (log4j) and database configuration files. </p>
*
*<p>Where referenced, [environment] is one of:</p>
*
*<ul>
*<li>development (default)</li>
*<li>production</li>
*<li>test</li>
*<li>(defaults)</li>
*</ul>
*
*<p>The search order is as described above. For each file, the following locations are searched in order:</p>
*
*<ul>
*<li>the configuration directory (if defined)</li>
*<li>the user directory</li>
*<li>any jars in the classpath</li>
*<li>any jars in the system classpath</li>
*</ul>
*
*<p>The following files are managed:</p>
*
*<ul>
*<li>application-[environment].properties (optional)</li>
*<li>application_defaults.properties</li>
*<li>database-[environment].properties (optional)</li>
*<li>log4j-[environment].properties (optional)</li>
*<li>log4j-defaults.properties </li>
*</ul>
*
*<p>In general, if a file is shown, it must be present in one of the locations or an exception will be thrown. &#39;Optional&#39; indicates that the file can be absent.</p>
*
*<p>The precendence of the files follows the Apache Commons Configuration convention. The environment-specific files are loaded first, followed by the defaults. This ensures that the environment-specific settings override the defaults. </p>
*
*<p>Note that no &#39;database-defaults.properties&#39; are currently supported. So, if database information is required, it must be supplied in an environment-specific file. The best practice here is to manage the database settings, credentials in particular, externally to the component or program.</p>
*
*<h3>Overriding default behaviors</h3>
*
*<p>Most AppConfig internal behavior can be overriden with appropriate feature switches. </p>
*
*<p>The value of a particular feature switch is determined hierarchically, with the first value found for a particular setting being used. The searcn order in which the switch settings are examined is:</p>
*
*<ul>
*<li>Internal, programmatically defined options. Specified when initializing AppConfig as AppConfig.sConfigure(options);</li>
*<li>Command-line options (specified using JVM &#39;-D[setting name]=[setting value]&#39; syntax)</li>
*<li>Environment settings (specified using &#39;export [setting name]=[setting value]&#39; syntax or equivalent)</li>
*<li>Internal default value</li>
*</ul>
*
*<p>The setting name is the same in all cases.</p>
*
*<p>The following internal settings are available:</p>
*
*<ul>
*<li>&#39;com.verymuchme.appconfig.consoleLog&#39; - Enable/disable bootstrap console logging</li>
*<li>&#39;com.verymuchme.appconfig.systemPropertiesOverride&#39; - enable/disable the ability for system properties to override others</li>
*<li>&#39;com.verymuchme.appconfig.runTimeEnvironment&#39; - If specified, one of &#39;development&#39;,&#39;production&#39;,&#39;test&#39;. Defaults to &#39;development&#39;</li>
*<li>&#39;com.verymuchme.appconfig.systemPropertiesOverride&#39; - enable/disable the ability for system properties to override others</li>
*<li>&#39;com.verymuchme.appconfig.runTimeEnvironment&#39; - If specified, one of &#39;development&#39;,&#39;production&#39;,&#39;test&#39;. Defaults to &#39;development&#39;</li>
*<li>&#39;com.verymuchme.appconfig.externalConfigurationDirectory&#39; - If specified, defines the external directory location for configuration files specific to particular run-time environments</li>
*<li>&#39;com.verymuchme.appconfig.applicationConfigurationPrefix&#39; - If specified, defines the prefix for application configuration property files. Defaults to &#39;application&#39;</li>
*<li>&#39;com.verymuchme.appconfig.databaseConfigurationPrefix&#39; - If specified, defines the prefix for database configuration property files. Defaults to &#39;database&#39;</li>
*<li>&#39;com.verymuchme.appconfig.log4jConfigurationPrefix&#39; - If specified, defines the prefix for log4j configuration property files. Defaults to &#39;log4j&#39;</li>
*<li>&#39;com.verymuchme.appconfig.configurationNameSuffix&#39; - If specified, defines the suffix for configuration property files. Defaults to &#39;properties&#39;</li>
*<li>&#39;com.verymuchme.appconfig.defaultConfigurationName&#39; - If specified, defines the prefix for default configuration property files. Defaults to &#39;defaults&#39;</li>
*<li>&#39;com.verymuchme.appconfig.database.defaultConfigurationEnabled&#39; - If set to true, a default database configuration file (&#39;database-defaults.properties&#39;) must be present. Defaults to &#39;false&#39;</li>
*<li>&#39;com.verymuchme.appconfig.log4j.defaultConfigurationEnabled&#39; - If set to true, a default log4j configuration file (&#39;log4j-defaults.properties&#39;) must be present. Defaults to &#39;true&#39;</li>
*</ul>
*
*<h2>Maven dependency information</h2>
*
*<pre><code>&lt;repository&gt;
*  &lt;id&gt;mvn-repo-releases&lt;/id&gt;
*  &lt;url&gt;https://github.com/tflynn/mvn-repo-public/raw/master/releases&lt;/url&gt;
*&lt;/repository&gt;
*
*&lt;repository&gt;
*  &lt;id&gt;mvn-repo-snapshots&lt;/id&gt;
*  &lt;url&gt;https://github.com/tflynn/mvn-repo-public/raw/master/snapshots&lt;/url&gt;
*&lt;/repository&gt;
*
*&lt;dependency&gt;
*  &lt;groupId&gt;com.verymuchme.appconfig&lt;/groupId&gt;
*  &lt;artifactId&gt;app_config&lt;/artifactId&gt;
*  &lt;version&gt;1.1&lt;/version&gt;
*&lt;/dependency&gt;
*</code></pre>
 * 
 * @author Tracy Flynn
 * @version 1.0
 * @since 1.0
 */
public class AppConfig {
  
  //TODO logging for AppConfig initialization separate from APP being configured

  /*
   * Singleton AppConfig instance for invocation via singleton shortcuts
   */
  private static final AppConfig singletonInstance = new AppConfig(null);

  /*
   * Bootstrap logger instance 
   */
  private BootstrapLogger bootstrapLogger = null;
  
  /*
   * Configuration options
   */
  private HashMap<String,String> configurationOptions = null;
  
  
  /**
   * Create a new AppConfig instance
   */
  public AppConfig(HashMap<String,String> configOptions) {
    this.configurationOptions = configOptions;
  }
  
  /**
   * Configure AppConfig
   * @throws ConfigurationException 
   */
  public void configure() throws Exception {
    this.bootstrapLogger = new BootstrapLogger();

    // Get the configuration definition
    ConfigurationDefinitionBuilder configurationBuilder = new ConfigurationDefinitionBuilder(this.configurationOptions);
    configurationBuilder.setBootstrapLogger(this.bootstrapLogger);
    String configurationDefinition = configurationBuilder.build();
    
    // Configure log4j first - deals with logging configuration in dependent packages
    List<String> log4jConfigNames = configurationBuilder.generateLog4jConfigurationNames();
    configurationBuilder.loadLog4jConfiguration(log4jConfigNames);

    
    File tempFile = null;
    
    try {
      // Write it to a temporary file
      File temp = File.createTempFile("tempfile", ".xml");
      BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
      bw.write(configurationDefinition);
      bw.close();
  
      // Create a handle to the temporary file
      tempFile = temp.getAbsoluteFile();
      String tempFileName = tempFile.getAbsolutePath();
      this.bootstrapLogger.debug(String.format("AppConfig.configure temporary file name %s", tempFileName));
      
      // Load configuration definition in as a file
      DefaultConfigurationBuilder defaultConfigurationBuilder = new DefaultConfigurationBuilder();
      defaultConfigurationBuilder.load(tempFile);
      this.bootstrapLogger.debug(String.format("AppConfig.configure configuration definition loaded"));
      defaultConfigurationBuilder.getConfiguration();
      this.bootstrapLogger.debug(String.format("AppConfig.configure configuration generated successfully"));
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      try {
        // Delete the temporary file
        tempFile.delete();
      }
      catch (Exception ee) {
        // Ignore
      }
    }
    
  }
  
  /**
   * Configure the singleton AppConfig instance
   * @throws ConfigurationException 
   */
  public static void sConfigure() throws Exception {
    AppConfig.singletonInstance.configure();
  }

  /**
   * Utility function to get system or environment variable
   *
   * @return Named system variable, then environment variable then null
   */
  public static String sSystemOrEnvironmentValue(String variableName) {
    String variableValue = System.getProperty(variableName);
    if (variableValue == null) {
      variableValue = System.getenv(variableName);
    }
    return variableValue;
  }
  
  
  /**
   * Set configuration options
   * 
   * @param configOpts
   */
  public void setOptions(HashMap<String,String> configOpts) {
    this.configurationOptions = configOpts;
  }
  
  /**
   * Get configuration options
   * 
   * @return Configuration options
   */
   public HashMap<String,String> getOptions() {
     return this.configurationOptions;
   }
   
   public static void main(String[] args) throws Exception {
     AppConfig.sConfigure();
   }

}
