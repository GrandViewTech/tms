package com.loylty.application.service.util;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ConcurrentReferenceHashMap;

import com.loylty.application.entity.bo.constants.TenantType;
import com.loylty.application.entity.bo.master.Tenant;

public class SpringContext
	{
		final private static org.apache.log4j.Logger										logger					= org.apache.log4j.Logger.getLogger(SpringContext.class);
		private static volatile String														inHouseTenantId			= "";
		private static volatile Set<String>													tenantIds				= Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
		private static volatile Map<String, org.springframework.beans.factory.BeanFactory>	tenantId_beanDefinition	= new ConcurrentReferenceHashMap<String, org.springframework.beans.factory.BeanFactory>();
		private static volatile Map<String, Tenant>											tenantId_tenant			= new ConcurrentReferenceHashMap<String, Tenant>();
		private static volatile Properties													properties				= new Properties();
		private static volatile boolean														isPropertyLoaded		= false;
		final static private String															TEMPLATE_LOCATION		= "template";
		
		public static void initializeTenantAndLoadBeanFactory(Tenant tenant)
			{
				String tenantId = tenant.getTenantId();
				tenantId = tenantId.trim().toLowerCase();
				try
					{
						loadConfigProperty();
						if ( tenant.getTenantType().equals(TenantType.IN_HOUSE) == false )
							{
								if ( isTenantIdConfigured(tenantId) == false )
									{
										ApplicationContext applicationContext = createAndUpdateApplicationContextFileForTenant(tenant.getTenantSchemaId());
										tenantId_beanDefinition.put(tenantId, applicationContext);
										logger.info("tenantId_beanDefinition Size : " + tenantId_beanDefinition.size());
									}
								else
									{
										logger.info("Tenant with " + tenantId + " is Already Loading");
									}
							}
						else
							{
								inHouseTenantId = tenantId;
								logger.info("inHouseTenantId : " + tenantId);
							}
						tenantIds.add(tenantId);
						tenantId_tenant.put(tenantId, tenant);
					}
				catch (Exception exception)
					{
						tenantIds.remove(tenantId);
						logger.error(exception.getLocalizedMessage(), exception);
					}
					
			}
			
		private static ApplicationContext createAndUpdateApplicationContextFileForTenant(String tenantName) throws Exception
			{
				String outputFolder = properties.getProperty("fileSystemLocation");
				String databaseFileLocation = createAndUpdateDatabaseFileForTenant(tenantName);
				String outputDir = outputFolder + File.separator + tenantName;
				String tenantPersistenceXmlFileName = "tenant-persistence-template.xml";
				Resource tenantPersistenceXmlFile = new ClassPathResource(TEMPLATE_LOCATION + File.separator + tenantPersistenceXmlFileName);
				File persistenceFile = new File(outputDir + File.separator + tenantPersistenceXmlFileName);
				FileUtils.copyFile(tenantPersistenceXmlFile.getFile(), persistenceFile);
				String applicationContextFileName = "application-config-template.xml";
				Resource applicationContextFile = new ClassPathResource(TEMPLATE_LOCATION + File.separator + applicationContextFileName);
				String content = FileUtils.readFileToString(applicationContextFile.getFile(), "UTF-8");
				content = content.replace("DATABASE_FILE_LOCATION", (databaseFileLocation).trim());
				content = content.replace("PERSISTENCE_FILE", (persistenceFile.getAbsolutePath()).trim());
				FileUtils.writeStringToFile(new File(outputDir + File.separator + applicationContextFileName), content, "UTF-8");
				// String daoXmlFileName = "dao-config.xml";
				// Resource daoXmlFile = new ClassPathResource("template" +
				// File.separator + daoXmlFileName);
				// FileUtils.copyFile(daoXmlFile.getFile(), new File(outputDir +
				// File.separator + daoXmlFileName));
				ApplicationContext applicationContext = new FileSystemXmlApplicationContext(outputDir + File.separator + applicationContextFileName);
				return applicationContext;
				
			}
			
		private static String createAndUpdateDatabaseFileForTenant(String tenantName) throws IOException
			{
				String outputFolder = properties.getProperty("fileSystemLocation");
				String outputDir = outputFolder + File.separator + tenantName;
				String databaseFileName = "database.properties";
				Resource resource = new ClassPathResource(TEMPLATE_LOCATION + File.separator + databaseFileName);
				String content = FileUtils.readFileToString(resource.getFile(), "UTF-8");
				content = content.replace("JDBC.URL", properties.getProperty("JDBC.URL") + "/" + tenantName);
				content = content.replaceAll("JDBC.USERNAME", properties.getProperty("JDBC.USERNAME"));
				content = content.replaceAll("JDBC.PASSWORD", properties.getProperty("JDBC.PASSWORD"));
				File datbaseFile = new File(outputDir + File.separator + databaseFileName);
				FileUtils.writeStringToFile(datbaseFile, content, "UTF-8");
				return datbaseFile.getAbsolutePath();
			}
			
		/**
		 * 
		 * @param tenantId
		 * @return true if tenantId is found
		 */
		public static boolean isTenantIdConfigured(String tenantId)
			{
				if ( tenantId == null || tenantId.trim().length() == 0 ) { return false; }
				tenantId = tenantId.trim().toLowerCase();
				if ( tenantIds.contains(tenantId) == true )
					{
						return true;
					}
				else if ( isInHouseTenantId(tenantId) ) { return true; }
				return false;
			}
			
		/**
		 * 
		 * @param tenantId
		 * @return
		 */
		public static boolean isInHouseTenantId(String tenantId)
			{
				if ( tenantId == null || tenantId.trim().length() == 0 ) { return false; }
				tenantId = tenantId.trim().toLowerCase();
				if ( tenantId.equals(inHouseTenantId) ) { return true; }
				return false;
			}
			
		public static Object getBean(String tenantId, String beanName)
			{
				Object object = null;
				if ( isTenantIdConfigured(tenantId) )
					{
						BeanFactory beanFactory = tenantId_beanDefinition.get(tenantId);
						object = beanFactory.getBean(beanName);
					}
				return object;
				
			}
			
		public static Tenant getTenant(String tenantId)
			{
				if ( tenantId == null || tenantId.trim().length() == 0 ) { return null; }
				tenantId = tenantId.trim().toLowerCase();
				return tenantId_tenant.get(tenantId);
			}
			
		private static boolean loadConfigProperty() throws IOException
			{
				try
					{
						if ( !isPropertyLoaded )
							{
								Resource resource = new ClassPathResource("properties" + File.separator + "application.properties");
								properties.load(resource.getInputStream());
								isPropertyLoaded = true;
							}
						return isPropertyLoaded;
					}
				catch (IOException exception)
					{
						logger.error(exception.getLocalizedMessage(), exception);
						throw exception;
					}
			}
	}
