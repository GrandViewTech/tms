package com.loylty.application.service.business.util;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
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
		private static org.apache.log4j.Logger												logger					= org.apache.log4j.Logger.getLogger(SpringContext.class);
		
		private static volatile String														inHouseTenantId			= "";
		private static volatile Set<String>													tenantIds				= Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
		private static volatile Map<String, org.springframework.beans.factory.BeanFactory>	tenantId_beanDefinition	= new ConcurrentReferenceHashMap<String, org.springframework.beans.factory.BeanFactory>();
		private static volatile Map<String, Tenant>											tenantId_tenant			= new ConcurrentReferenceHashMap<String, Tenant>();
		
		public static void initializeTenantAndLoadBeanFactory(Tenant tenant)
			{
				String outputFolder = "output";
				String tenantId = tenant.getTenantId();
				tenantId = tenantId.trim().toLowerCase();
				try
					{
						tenantIds.add(tenantId);
						if ( tenant.getTenantType().equals(TenantType.IN_HOUSE) == false )
							{
								if ( isTenantIdConfigured(tenantId) == false )
									{
										ApplicationContext applicationContext = createAndUpdateApplicationContextFileForTenant(outputFolder, tenant.getTenantSchemaId());
										tenantId_beanDefinition.put(tenantId, applicationContext);
										logger.info("tenantId_beanDefinition Size : " + tenantId_beanDefinition.size());
									}
								else
									{
										logger.info("Tenant with TenantId is Already Loading");
									}
							}
						else
							{
								inHouseTenantId = tenantId;
								logger.info("inHouseTenantId : " + tenantId);
							}
						tenantId_tenant.put(tenantId, tenant);
						
					}
				catch (Exception e)
					{
						tenantIds.remove(tenantId);
						e.printStackTrace();
					}
					
			}
			
		private static ApplicationContext createAndUpdateApplicationContextFileForTenant(String outputFolder, String tenantName) throws Exception
			{
				String databaseFileLocation = createAndUpdateDatabaseFileForTenant(outputFolder, tenantName);
				String outputDir = outputFolder + File.separator + tenantName;
				String tenantPersistenceXmlFileName = "tenant-persistence-template.xml";
				Resource tenantPersistenceXmlFile = new ClassPathResource("spring" + File.separator + tenantPersistenceXmlFileName);
				File persistenceFile = new File(outputDir + File.separator + tenantPersistenceXmlFileName);
				FileUtils.copyFile(tenantPersistenceXmlFile.getFile(), persistenceFile);
				String applicationContextFileName = "application-config-template.xml";
				Resource applicationContextFile = new ClassPathResource("spring" + File.separator + applicationContextFileName);
				String content = FileUtils.readFileToString(applicationContextFile.getFile(), "UTF-8");
				content = content.replace("DATABASE_FILE_LOCATION", (databaseFileLocation).trim());
				content = content.replace("PERSISTENCE_FILE", (persistenceFile.getAbsolutePath()).trim());
				
				FileUtils.writeStringToFile(new File(outputDir + File.separator + applicationContextFileName), content, "UTF-8");
				String daoXmlFileName = "dao-config.xml";
				Resource daoXmlFile = new ClassPathResource("spring" + File.separator + daoXmlFileName);
				FileUtils.copyFile(daoXmlFile.getFile(), new File(outputDir + File.separator + daoXmlFileName));
				ApplicationContext applicationContext = new FileSystemXmlApplicationContext(outputDir + File.separator + applicationContextFileName);
				return applicationContext;
				
			}
			
		private static String createAndUpdateDatabaseFileForTenant(String outputFolder, String tenantName) throws IOException
			{
				String outputDir = outputFolder + File.separator + tenantName;
				String databaseFileName = "database.properties";
				Resource resource = new ClassPathResource("properties" + File.separator + databaseFileName);
				String content = FileUtils.readFileToString(resource.getFile(), "UTF-8");
				content = content.replace("JDBC.URL", "jdbc:mysql://localhost:3306/" + tenantName);
				content = content.replaceAll("JDBC.USERNAME", "puneets");
				content = content.replaceAll("JDBC.PASSWORD", "pass@123");
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
				else
					{
					}
				return object;
				
			}
			
		public static Tenant getTenant(String tenantId)
			{
				if ( tenantId == null || tenantId.trim().length() == 0 ) { return null; }
				tenantId = tenantId.trim().toLowerCase();
				return tenantId_tenant.get(tenantId);
			}
	}
