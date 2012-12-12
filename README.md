
# AppConfig - Application configuration the easy way

## A thin wrapper for Apache Commons Configuration

Copyright 2009-2013 Tracy Flynn. All rights reserved. Licensed under the Apache License, Version 2.0.

## Introduction

AppConfig is designed to provide painless multi-environment, external and internal configuration with default settings for components and programs.

## Typical usage


### Create a configuration directory

      mkdir ~/local_appconfig
    

### Tell AppConfig where the directory is

Either

      export com.verymuchme.appconfig.externalConfigurationDirectory=~/local_appconfig

or

      java . . . -Dcom.verymuchme.appconfig.externalConfigurationDirectory=~/local_appconfig . . .

### Define the database settings (if needed)

If the component or program requires database information, create a suitable 'database-[environment].properties' file in the configuration directory. (See below).

### Initialize AppConfig in your code

    AppConfig.sConfigure();

### Results

The following files will be loaded in the order shown. 

      application-development.properties (optional)
      database-development.properties (optional)
      log4j-development.properties (optional)
      application-defaults.properties
      log4j-defaults.properties

For each file, the search order is the following:

 * the configuration directory (if defined)
 * the user directory
 * any jars in the classpath
 * any jars in the system classpath
 
In general, if a file is shown, it must be present in one of the locations or an exception will be thrown. 'Optional' indicates that the file can be absent.

## The details

AppConfig is designed to provide painless multi-environment, external and internal configuration with default settings for components and programs.

AppConfig automatically names, finds and loads environment-specific and default application, logging (log4j) and database configuration files. 

Where referenced, [environment] is one of:

* development (default)
* production
* test
* (defaults)

The search order is as described above. For each file, the following locations are searched in order:

 * the configuration directory (if defined)
 * the user directory
 * any jars in the classpath
 * any jars in the system classpath
  
The following files are managed:

* application-[environment].properties (optional)
* application_defaults.properties
* database-[environment].properties (optional)
* log4j-[environment].properties (optional)
* log4j-defaults.properties 

In general, if a file is shown, it must be present in one of the locations or an exception will be thrown. 'Optional' indicates that the file can be absent.

The precendence of the files follows the Apache Commons Configuration convention. The environment-specific files are loaded first, followed by the defaults. This ensures that the environment-specific settings override the defaults. 

Note that no 'database-defaults.properties' are currently supported. So, if database information is required, it must be supplied in an environment-specific file. The best practice here is to manage the database settings, credentials in particular, externally to the component or program.

### Overriding default behaviors

Most AppConfig internal behavior can be overriden with appropriate feature switches. 

The value of a particular feature switch is determined hierarchically, with the first value found for a particular setting being used. The searcn order in which the switch settings are examined is:

* Internal, programmatically defined options. Specified when initializing AppConfig as AppConfig.sConfigure(options);
* Command-line options (specified using JVM '-D[setting name]=[setting value]' syntax)
* Environment settings (specified using 'export [setting name]=[setting value]' syntax or equivalent)
* Internal default value

The setting name is the same in all cases.

The following internal settings are available:

* 'com.verymuchme.appconfig.consoleLog' - Enable/disable bootstrap console logging
* 'com.verymuchme.appconfig.systemPropertiesOverride' - enable/disable the ability for system properties to override others
* 'com.verymuchme.appconfig.runTimeEnvironment' - If specified, one of 'development','production','test'. Defaults to 'development'
* 'com.verymuchme.appconfig.systemPropertiesOverride' - enable/disable the ability for system properties to override others
* 'com.verymuchme.appconfig.runTimeEnvironment' - If specified, one of 'development','production','test'. Defaults to 'development'
* 'com.verymuchme.appconfig.externalConfigurationDirectory' - If specified, defines the external directory location for configuration files specific to particular run-time environments
* 'com.verymuchme.appconfig.applicationConfigurationPrefix' - If specified, defines the prefix for application configuration property files. Defaults to 'application'
* 'com.verymuchme.appconfig.databaseConfigurationPrefix' - If specified, defines the prefix for database configuration property files. Defaults to 'database'
* 'com.verymuchme.appconfig.log4jConfigurationPrefix' - If specified, defines the prefix for log4j configuration property files. Defaults to 'log4j'
* 'com.verymuchme.appconfig.configurationNameSuffix' - If specified, defines the suffix for configuration property files. Defaults to 'properties'
* 'com.verymuchme.appconfig.defaultConfigurationName' - If specified, defines the prefix for default configuration property files. Defaults to 'defaults'
* 'com.verymuchme.appconfig.database.defaultConfigurationEnabled' - If set to true, a default database configuration file ('database-defaults.properties') must be present. Defaults to 'false'
* 'com.verymuchme.appconfig.log4j.defaultConfigurationEnabled' - If set to true, a default log4j configuration file ('log4j-defaults.properties') must be present. Defaults to 'true'

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
      <version>1.1</version>
    </dependency>
