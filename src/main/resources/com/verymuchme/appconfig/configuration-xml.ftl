<?xml version="1.0" encoding="UTF-8" ?>

<configuration>

	<#if systemOverride == "true"><system/></#if>

	<#if externalConfigurationDirectory??><properties fileName="${externalConfigurationDirectory}/${applicationPrefix}-${rtEnv}.${suffix}" config-optional="true"/></#if>
	<properties fileName="${packageDir}/${applicationPrefix}-${rtEnv}.${suffix}" config-optional="true"/>
	<#if externalConfigurationDirectory??><properties fileName="${externalConfigurationDirectory}/${applicationPrefix}-${defaultPropName}.${suffix}" config-optional="true"/></#if>
	<properties fileName="${packageDir}/${applicationPrefix}-${defaultPropName}.${suffix}"/>

	<#if externalConfigurationDirectory??><properties fileName="${externalConfigurationDirectory}/${databasePrefix}-${rtEnv}.${suffix}" config-optional="true"/></#if>
	<properties fileName="${packageDir}/${databasePrefix}-${rtEnv}.${suffix}" config-optional="true"/>
	<#if externalConfigurationDirectory??><properties fileName="${externalConfigurationDirectory}/${databasePrefix}-${defaultPropName}.${suffix}" config-optional="true"/></#if>
	<properties fileName="${packageDir}/${databasePrefix}-${defaultPropName}.${suffix}"/>
	
</configuration>

