
# AppConfig V4 - Application configuration the easy way

## A thin wrapper for Apache Commons Configuration

Copyright 2009-2013 Tracy Flynn. All rights reserved. Licensed under the Apache License, Version 2.0.

## Introduction

AppConfig is designed to provide painless configuration for multiple runtime environments based on a model similar to the Ruby-on-Rails 'development/production/test' approach with all the appropriate defaulting and overriding of settings.

It is particularly targeted at the problem of configuring components and applications 'from the outside' without needing to modify the packaged artifact.

If certain conventions are observed, it can also independently manage configurations for multiple AppConfig-enabled components within a single application.

It is based on [Apache Commons Configuration](http://commons.apache.org/configuration "Apache Commons Configuration").

## Typical usage

Suppose the settings for a component or a program that's been built with AppConfig support need to be changed.

### Create a configuration directory

      mkdir ~/local_appconfig
    
### Define environment-specific configurations

Put environment-specific settings into the configuration directory in files with the naming patterns:

* application-[environemnt].properties
* database-[environment].properties
* logback-[environment].xml

Where 'environment' is one of 'development','production' or 'test'. The contents of the properties files follow Java Property file syntax. The contents of the logback-xxx.xml files follow logback syntax (See [Logback Home](http://logback.qos.ch/ "Logback Home")).

Override whatever settings need to be changed in the appropriate file. 

    e.g. Change development database connection settings for your machine in 'database-development.properties'

### Tell AppConfig where the directory is

Either

      export com.verymuchme.appconfig.externalConfigurationDirectory=~/local_appconfig

or

      java -Dcom.verymuchme.appconfig.externalConfigurationDirectory=~/local_appconfig ...
      
### For (multiple) web applications in the same container

Enable this setting when running multiple web applications in the same container. This causes the servlet context to be appended to the external configuration directory. This provides a separate configuration directory per web application.

Either

    export com.verymuchme.appconfig.externalConfigurationDirectory.useContextPathAsSuffix=true
    
Or

    java -Dcom.verymuchme.appconfig.externalConfigurationDirectory.useContextPathAsSuffix=true \
         -Dcom.verymuchme.appconfig.externalConfigurationDirectory=~/local_appconfig ...

Note that the application must have been built so that the contextPath is passed to AppConfig as an option at application initialization time using the setting  'application.contextPath'.


### (Production only) Tell AppConfig to load production configurations

Either

      export com.verymuchme.appconfig.runTimeEnvironment=production

or

      java -Dcom.verymuchme.appconfig.externalConfigurationDirectory=~/local_appconfig \
           -Dcom.verymuchme.appconfig.runTimeEnvironment=production ...
      
By default, AppConfig assumes you're running in 'development' mode.



### Run your program

When you run the program, your modified settings will be picked up automatically, will override the default settings and be used to configure the program. 

## That's it!!

## Demo

**Not currently up to date**

You can generate a fully functional demo by using the following Maven archetype and instructions:

    mvn archetype:generate -DarchetypeRepository=https://github.com/tflynn/mvn-repo-public/raw/master/releases -DarchetypeGroupId=com.verymuchme.archetypes -DarchetypeArtifactId=basic_jar -DarchetypeVersion=1.2 -DgroupId=com.example.demo -DartifactId=basic_jar -DartifactVersion=1.0-SNAPSHOT
    cd basic_jar
    mvn clean package
    java -cp ./target/basic_jar-1.0-SNAPSHOT-jar-with-dependencies.jar com.example.demo.App

Examine the following files to understand how the application is being configured:

    ./src/main/java/com/example/demo/App.java
    ./src/main/java/com/example/demo/ConfigurationLoader.java
    ./src/main/resources/com/example/demo/application-defaults.properties
    ./src/main/resources/com/example/demo/application-development.properties
    ./src/main/resources/com/example/demo/application-test.properties
    ./src/main/resources/com/example/demo/database-defaults.properties
    ./src/main/resources/com/example/demo/logback-defaults.xml
    ./src/main/resources/com/example/demo/logback-development.xml
    ./src/main/resources/com/example/demo/logback-test.xml

## (Some of) the gory details

AppConfig is designed to provide painless configuration for multiple runtime environments based on a model similar to the Ruby-on-Rails 'development/production/test' approach with all the appropriate defaulting and overriding of settings.

It is particularly targeted at the problem of configuring components and applications 'from the outside' without needing to modify the packaged artifact.

It is based on [Apache Commons Configuration](http://commons.apache.org/configuration "Apache Commons Configuration").

### Using AppConfig in code

#### Initialize AppConfig

      AppConfig appConfig = new AppConfig();
      appConfig.setApplicationPropertiesPackageName("your base package name");
      appConfig.setExternalConfigurationDirectory("configuration directory name");
      HashMap<String,Object> configurationOptions = new HashMap<String,Object>();
      configurationOptions.put("application.contextPath",servletContext.getContextPath()); // Web app only
      appConfig.setOptions(configurationOptions);
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
      application-defaults.properties 
      database-defaults.properties
      logback-development.xml (optional)
      logback-defaults.xml (optional)

For each file, the search order is the following:

 * the configuration directory (if defined)
 * the user directory
 * any jars in the classpath
 * any jars in the system classpath
 
Following the Apache Commons Configuration convention, if the same key found in multiple files, the value from the first file found and loaded wins. So, in this case, 'development' trumps 'defaults' - just the desired behavior.

In general, if a file is shown, it must be present in one of the locations or an exception will be thrown. It may be empty. 'Optional' indicates that the file can be absent. 

### And configuration settings are available

    Configuration configuration = appConfig.getConfiguration();
    
    // See AppConfig javadocs or Apache Commons Configuration API docs for more information 
    // http://commons.apache.org/configuration/apidocs/index.html
    String configurationValue = configuration.getString("some property name"); 
    
### Logging

The default logging configuration support assumes logback (See [Logback Home](http://logback.qos.ch/ "Logback Home")). Note that the 'LogbackFactory' class is a convenience class provided by the AppConfig package. It mirrors the syntax of 'slf4j.LoggerFactory'.

In code, all you need is something similar to:

    import ch.qos.logback.classic.LogbackFactory;
	import ch.qos.logback.classic.Logger;
    …
    public class SomeClass {
      …
      private static final Logger logger = LogbackFactory.getLogger(SomeClass.class);
      …
      public void someMethod() {
        …
        logger.debug("Some significant message");
        …
      }
      …
    }
    
And in the POM file - or in an equivalent dependency management tool

    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>1.7.2</version>
    </dependency>
    
    <!--  LogBack -->
    <dependency>
		<groupId>ch.qos.logback</groupId>
		<artifactId>logback-core</artifactId>
		<version>1.0.9</version>
	</dependency>
	
    <dependency>
		<groupId>ch.qos.logback</groupId>
		<artifactId>logback-classic</artifactId>
		<version>1.0.9</version>
	</dependency>

Note that the 'slf4j' and 'logback' dependencies are managed by the 'AppConfig' dependency (see below). 

## Advanced information

### Changing logging and configuration implementations

Both the logging and configuration implementations can be changed. 

Because AppConfig follows its own configuration model, it can be configured from the outside, and doesn't require a rebuild of the package to add new support. 

#### Logging

To change logging support:

* Create a class which implements the 'LoggingHelper" interface 
* Override the internal setting "com.verymuchme.appconfig.loggingHelper.className" (See below)
* Consider changing the setting "com.verymuchme.appconfig.logging.ConfigurationPrefix" (See below)
* Consider changing the setting "com.verymuchme.appconfig.loggingConfigurationNameSuffix" (See below)

#### Configuration

To change configuration support:

* Create a builder class which implements the 'ConfigurationBuilder" interface extending "ConfigurationBuilderBase"
* Create a configuration class which implements the 'Configuration" interface, extending "ConfigurationBase"
* Override the internal setting "com.verymuchme.appconfig.configurationBuilder.className" (See below)

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
          logger.error("App failed to configure properly. Exiting abnormally",ace);
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
    
    # File name for internal default properties - only specify a value to override the default 'internal-defaults.properties'
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
    
    # Default prefix for a logback configuration file
    com.verymuchme.appconfig.logging.ConfigurationPrefix = logback
    # Require a default logback configuration file
    com.verymuchme.appconfig.logback.defaultConfigurationEnabled = true
    
    # Default suffix for all configuration files
    com.verymuchme.appconfig.configurationNameSuffix = properties
    
    # Default suffix for logging configuration files
    com.verymuchme.appconfig.loggingConfigurationNameSuffix = xml

    # Default prefix for default configuration files
    com.verymuchme.appconfig.defaultConfigurationName = defaults
    
    # Default name for Freemarker configuration template
    com.verymuchme.appconfig.configurationTemplateName = configuration-xml.ftl
    
    # Base class for Freemarker to use to find template
    com.verymuchme.appconfig.freemarker.baseClassName = null
    
    # Default AppConfig logging level for bootstrap logging
    com.verymuchme.appconfig.boostrapLogging.logLevel = ERROR
    
    # Default AppConfig logging level for internal logging
    com.verymuchme.appconfig.logging.logLevel = ERROR
    
    # Name of AppConfig internal logback configuration file
    com.verymuchme.appconfig.logging.internalConfigurationFileName = logback-internal.xml
    
    # Default value of application properties package name
    application.propertiesPackageName = null
    
    # Default value of application properties package directory
    application.propertiesPackageDir = null
    
    # Default LoggingHelper class name
    com.verymuchme.appconfig.loggingHelper.className = com.verymuchme.appconfig.LoggingHelperLogback
    
    # Default ConfigurationBuilder class name
    com.verymuchme.appconfig.configurationBuilder.className = com.verymuchme.appconfig.ConfigurationBuilderCommonsConfiguration
    
    # Context class for loading logging properties. If null, configuration files loaded relative to 
    # internal logging configuration
    application.logging.contextClass = null
    
    # For Spring Apps, application context
    application.applicationContext = null

    # If there is more than one AppConfig-enabled web application running in the same container, 
    # each application needs to be configured separately.
    #
    # Enable this setting to cause the servlet context to be appended to the external configuration directory.
    # This provides a separate configuration directory per web application.
    # 
    # The contextPath must be passed to AppConfig as an option at application initialization time 
    # using the setting  'application.contextPath' 
    #
    com.verymuchme.appconfig.externalConfigurationDirectory.useContextPathAsSuffix = false
    
    # For web applications, the contextPath at application initialization time - ServletContext.getContextPath()
    application.contextPath = null
    

#### Changing the configuration template

Apache Commons Configuration uses the concept of a configuration definition file. At its heart, all AppConfig is doing is generating a customized configuration definition file and passing it to Apache Commons Configuration to load application and database properties. 

If the default template in AppConfig isn't suitable, it can be changed by setting the internal setting 'com.verymuchme.appconfig.configurationTemplateName' to point to another template. The template is a Freemarker template. Have a look at the AppConfig source before making a change to see what settings are passed to the template.

Note that, because of loading issues, logging configuration is handled separately.

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

    <!--  AppConfig -->
    <!--  Contains dependencies for Logback logging support 1.0.9 -->
    <!--  Contains dependency for slf4j-api 1.7.2 -->
    <dependency>
      <groupId>com.verymuchme.appconfig</groupId>
      <artifactId>app_config</artifactId>
      <version>4.0.1</version>
    </dependency>

