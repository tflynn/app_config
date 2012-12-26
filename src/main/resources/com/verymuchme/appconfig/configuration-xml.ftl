<?xml version="1.0" encoding="UTF-8" ?>

<configuration>

	<#if appconfig_systemPropertiesOverride??><system/></#if>

	<#if appconfig_externalConfigurationDirectory??><properties fileName="${appconfig_externalConfigurationDirectory}/${appconfig_applicationConfigurationPrefix}-${appconfig_runTimeEnvironment}.${appconfig_configurationNameSuffix}" config-optional="true"/></#if>
	<#if application_propertiesPackageDir??><properties fileName="${application_propertiesPackageDir}/${appconfig_applicationConfigurationPrefix}-${appconfig_runTimeEnvironment}.${appconfig_configurationNameSuffix}" config-optional="true"/></#if>
	<#if appconfig_externalConfigurationDirectory??><properties fileName="${appconfig_externalConfigurationDirectory}/${appconfig_applicationConfigurationPrefix}-${appconfig_defaultConfigurationName}.${appconfig_configurationNameSuffix}" config-optional="true"/></#if>
	<#if application_propertiesPackageDir??><properties fileName="${application_propertiesPackageDir}/${appconfig_applicationConfigurationPrefix}-${appconfig_defaultConfigurationName}.${appconfig_configurationNameSuffix}"/></#if>

	<#if appconfig_externalConfigurationDirectory??><properties fileName="${appconfig_externalConfigurationDirectory}/${appconfig_database_ConfigurationPrefix}-${appconfig_runTimeEnvironment}.${appconfig_configurationNameSuffix}" config-optional="true"/></#if>
	<#if application_propertiesPackageDir??><properties fileName="${application_propertiesPackageDir}/${appconfig_database_ConfigurationPrefix}-${appconfig_runTimeEnvironment}.${appconfig_configurationNameSuffix}" config-optional="true"/></#if>
	<#if appconfig_externalConfigurationDirectory??><properties fileName="${appconfig_externalConfigurationDirectory}/${appconfig_database_ConfigurationPrefix}-${appconfig_defaultConfigurationName}.${appconfig_configurationNameSuffix}" config-optional="true"/></#if>
	<#if application_propertiesPackageDir??><properties fileName="${application_propertiesPackageDir}/${appconfig_database_ConfigurationPrefix}-${appconfig_defaultConfigurationName}.${appconfig_configurationNameSuffix}"/></#if>
	
</configuration>

