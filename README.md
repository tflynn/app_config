
AppConfig - Application configuration the easy way - a thin wrapper for Apache Commons Configuration

Typical usage

AppConfig.sConfigure();

The following files will be loaded in the order shown. 'Optional' indicates that the file will be loaded only if found.

    application-development.properties (optional)
    database-development.properties (optional)
    log4j-development.properties (optional)
    application-defaults.properties
    log4j-defaults.properties

Automatically names, finds and loads environment-specific and default application, logging (log4j) and database configuration files. If an external directory is specified, that directory is searched for environment-specific files only. Then the user directory, current and system classpaths are searched in that order. Missing default configuration files for application and logging configurations will cause an exception if not present. The default database configuration file is by default disabled. These behaviors can be changed with appropriate option settings.

The order of loading of the files follows the Apache Commons Configuration convention. The environment-specific files are loaded first, followed by the defaults. That ensures that the environment-specific settings override the defaults.

THe file name convention is [application|database|log4j]-[development|test|production|defaults].properties.

The following internal settings are taken starting with internal options, command-line and then the environment value.

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
