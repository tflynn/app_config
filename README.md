
# AppConfig V2.3 - Application configuration the easy way

## A thin wrapper for Apache Commons Configuration

Copyright 2009-2013 Tracy Flynn. All rights reserved. Licensed under the Apache License, Version 2.0.

## Introduction

AppConfig is designed to provide painless configuration for multiple runtime environments based on a model similar to the Ruby-on-Rails 'development/production/test' approach with all the appropriate defaulting and overriding of settings.

It is particularly targeted at the problem of configuring components and applications 'from the outside' without needing to modify the packaged artifact.

If certain conventions are observed, it can also independently manage configurations for multiple AppConfig-enabled components within a single application.

It is based on Apache Commons Configuration http://commons.apache.org/configuration .

## Typical usage

Suppose the settings for a component or a program that's been built with AppConfig support need to be changed.

### Create a configuration directory

      mkdir ~/local_appconfig
    
### Define environment-specific configurations

Put environment-specific settings into the configuration directory in files with the naming patterns:

* application-[environemnt].properties
* database-[environment].properties
* log4j-[environment].properties

The contents of the files follow Java Property file syntax. The properties clearly have to match those that configure the component that's being configured.

### Tell AppConfig where the directory is

Either

      export com.verymuchme.appconfig.externalConfigurationDirectory=~/local_appconfig

or

      java … -Dcom.verymuchme.appconfig.externalConfigurationDirectory=~/local_appconfig ...
      
### (Production only) Tell AppConfig to load production configurations

Either

      export com.verymuchme.appconfig.runTimeEnvironment=production

or

      java … -Dcom.verymuchme.appconfig.externalConfigurationDirectory=~/local_appconfig \
             -Dcom.verymuchme.appconfig.runTimeEnvironment=production ...
      
By default, AppConfig assumes you're running in 'development' mode.


## That's it!!


## Some details

AppConfig is designed to provide painless configuration for multiple runtime environments based on a model similar to the Ruby-on-Rails 'development/production/test' approach with all the appropriate defaulting and overriding of settings.

It is particularly targeted at the problem of configuring components and applications 'from the outside' without needing to modify the packaged artifact.

It is based on Apache Commons Configuration http://commons.apache.org/configuration .
AppConfig is designed to provide painless multi-environment, external and internal configuration with default settings for components and programs.

### Using AppConfig in code

#### Initialize AppConfig

      AppConfig appConfig = new AppConfig();
      appConfig.setApplicationPropertiesPackageName("your base package name");
      appConfig.setExternalConfigurationDirectory("configuration directory name");
      appConfig.configure();

(See error handling information below)

##### Where to put configuration files

Configuration files should be stored at the root of your package. 

**Do NOT** place them at the root your source tree. If you do, unpredictable things will happen when you try to load configuations in a project containing multiple AppConfig-enabled components. 

Example:

AppConfig itself is a Maven-based package with a base package of 'com.verymuchme.appconfig'. So, configuration files would be placed in:

    ./src/main/resources/com/verymuchme/appconfig

#### Configurations load sequence

The following files will be automatically loaded in the order shown. 

      application-development.properties (optional)
      database-development.properties (optional)
      log4j-development.properties (optional)
      application-defaults.properties 
      database-defaults.properties
      log4j-defaults.properties (optional)

For each file, the search order is the following:

 * the configuration directory (if defined)
 * the user directory
 * any jars in the classpath
 * any jars in the system classpath
 
Following the Apache Commons Configuration convention, if the same key found in multiple files, the value from the first file found and loaded wins. So, in this case, 'development' trumps 'defsults' - just the desired behavior.

In general, if a file is shown, it must be present in one of the locations or an exception will be thrown. It may be empty. 'Optional' indicates that the file can be absent. 

### And configuration settings are available

    CombinedConfiguration configuration = appConfig.getCombinedConfiguration();
    
    // See Apache Commons Configuration API docs for more information 
    // http://commons.apache.org/configuration/apidocs/index.html
    String configurationValue = combinedConfiguration.getString("some property name"); 
    
### Logging

The logging configuration support assumes that the back-end logging can be configured using Log4j V1 (1.2.14) syntax.  If you use the Slf4j logging front-end, that will be true.

In code, all you need is something similar to:

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    …
    public class SomeClass {
      …
      private static final Logger logger = LoggerFactory.getLogger(SomeClass.class);
      …
      public void someMethod() {
        …
        logger.debug("Some significant message");
        …
      }
      …
    }
    
And in the POM file - or equivalend dependency management

    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>jcl-over-slf4j</artifactId>
      <version>1.5.8</version>
    </dependency>
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>1.5.8</version>
    </dependency>
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-log4j12</artifactId>
      <version>1.5.8</version>
    </dependency>
    <dependency>
      <groupId>log4j</groupId>
      <artifactId>log4j</artifactId>
      <version>1.2.14</version>
    </dependency>

## Advanced information

### Error handling

Application or component configuration normally happens at point where the artifact starts. It's also usually the case that a problem with the configuration is serious if not fatal.

That assumption led to two simple decisions. 

* If AppConfig encounters a problem when configuring the artifact, it will log the problem and throw an exception.
* All exceptions are wrapped with AppConfigException - **an unchecked, Runtime exception**.

So, a try/catch is needed around the AppConfig initialization (as shown below) if the code wants to detect configuration errors and exit gracefully.


      try {
        AppConfig appConfig = new AppConfig();
        appConfig.setApplicationPropertiesPackageName("your base package name");
        appConfig.setExternalConfigurationDirectory("configuration directory name");
        appConfig.configure();
       }
       catch (AppConfigException ace) {
       	 try {
          logger.error("App failed to configure properly. Exiting abnormally",e);
          System.exit(1);  // Exit with error code
         }
         catch (Exception ee) {
           // Can't be sure that logging was configured successfully if app configuration failed
           System.out.println("App failed to configure properly. Logging apparently not configured. Exiting abnormally");
           System.out.println(e.getMessage());
           e.printStackTrace(System.out);
           System.exit(2);  // Exit with error code
         }
       }



### Overriding default behaviors

Most AppConfig internal behaviors can be overriden with appropriate feature switches. 

The value of a particular feature switch is determined hierarchically, with the first value found for a particular setting being used. The searcn order in which the switch settings are examined is:

* Internal, programmatically defined options
  * Specified when initializing AppConfig using the 'appConfig.setOptions(..)' syntax
* Command-line options
  * Specified using JVM '-D[setting name]=[setting value]' syntax
* Environment settings
  * Specified using 'export [setting name]=[setting value]' syntax or equivalent
* Internal default value
  * Default internal settings are managed as property files so property File syntax applies

**The setting name is the same in all cases.**

The following internal settings are available:

    # 
    # Internal defaults for all settings that can be overridden
    #
    # Property values are specified with a slightly extended property syntax
    # 
    # "true" / "false" are recognized as boolean values
    # "null" is recognized as the null value
    # a comma delimited list of values is recognized as an array of string values
    # 
    
    # File name for internal default properties
    com.verymuchme.appconfig.internalDefaultPropertiesFileName = null
    
    # Include the Apache Commons Configuration definition file to include the "<system/>" element as the first definition
    com.verymuchme.appconfig.systemPropertiesOverride = false
    
    # Allowed run time environment names
    com.verymuchme.appconfig.permittedRunTimeEnvironments = development,production,test
    # Current run time environment name
    com.verymuchme.appconfig.runTimeEnvironment = development
    # Default run time environment name
    com.verymuchme.appconfig.defaultRunTimeEnvironment = development
    
    # External configuration directory
    com.verymuchme.appconfig.externalConfigurationDirectory = null
    
    # Default prefix for application configuration files
    com.verymuchme.appconfig.applicationConfigurationPrefix = application
    
    # Default prefix for database configuration files
    com.verymuchme.appconfig.database.ConfigurationPrefix = database
    # Require a default database configuration file
    com.verymuchme.appconfig.database.defaultConfigurationEnabled = true
    
    # Default prefix for a log4j configuration file
    com.verymuchme.appconfig.log4j.ConfigurationPrefix = log4j
    # Require a default log4j configuration file
    com.verymuchme.appconfig.log4j.defaultConfigurationEnabled = true
    
    # Default suffix for all configuration files
    com.verymuchme.appconfig.configurationNameSuffix = properties
    
    # Default prefix for default configuration files
    com.verymuchme.appconfig.defaultConfigurationName = defaults
    
    # Default name for Freemarker configuration template
    com.verymuchme.appconfig.configurationTemplateName = configuration-xml.ftl
    
    # Base class for Freemarker to use to find template
    com.verymuchme.appconfig.freemarker.baseClassName = null
    
    # Default AppConfig logging level at startup
    com.verymuchme.appconfig.log4j.logLevel = ERROR
    
    # Name of AppConfig internal log4j configuration file
    com.verymuchme.appconfig.log4j.internalConfigurationFileName = log4j-internal-logger.properties
    
    # Default value of application properties package name
    application.propertiesPackageName = null
    
    # Default value of application properties package directory
    application.propertiesPackageDir = null


#### Changing the configuration template

Apache Commons Configuration uses the concept of a configuration definition file. At its heart, all AppConfig is doing is generating a customized configuration definition file and passing it to Apache Commons Configuration to load.

If the default template in AppConfig isn't suitable, it can be changed by setting the internal setting 'com.verymuchme.appconfig.configurationTemplateName' to point to another template. The template is a Freemarker template. Have a look at the AppConfig source before making a change to see what settings are passed to the template.

### Accessing AppConfig internal settings

Settings internal to AppConfig itself (the list above) can be accessed from the application configuration object. For instance, to get the current runtime environment:

    configuration.getString("com.verymuchme.appconfig.runTimeEnvironment");

### AppConfig Source

The AppConfig source is managed at https://github.com/tflynn/app_config.



## Maven dependency information

    <repository>
      <id>mvn-repo-releases</id>
      <url>https://github.com/tflynn/mvn-repo-public/raw/master/releases</url>
    </repository>
         
    <repository>
      <id>mvn-repo-snapshots</id>
      <url>https://github.com/tflynn/mvn-repo-public/raw/master/snapshots</url>
    </repository>

    <dependency>
      <groupId>com.verymuchme.appconfig</groupId>
      <artifactId>app_config</artifactId>
      <version>2.3</version>
    </dependency>
