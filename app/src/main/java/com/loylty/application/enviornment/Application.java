package com.loylty.application.enviornment;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import com.loylty.application.entity.bo.master.Tenant;
import com.loylty.application.service.app.master.business.NotificationService;
import com.loylty.application.service.app.master.business.TenantService;
import com.loylty.application.service.util.SpringContext;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@EnableAsync
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
/*
 * @ComponentScan(basePackages = { "com.loylty.application.service.app",
 * "com.loylty.application.service.rest.validation" })
 */

@EnableMongoRepositories(basePackages = "com.loylty.application.service.app.master.respository")
@ComponentScan(basePackages =
	{ "com.loylty.application" })
@PropertySource(value = "classpath:properties/application.properties")
public class Application
	{
		private static org.apache.log4j.Logger	logger	= org.apache.log4j.Logger.getLogger(Application.class);
		@Autowired
		private TenantService					tenantService;
		
		@Bean
		public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer()
			{
				PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
				return propertySourcesPlaceholderConfigurer;
			}
			
		static
			{
				try
					{
						Resource resource = new ClassPathResource("properties" + File.separator + "log4j.properties");
						Properties properties = new Properties();
						properties.load(resource.getInputStream());
						PropertyConfigurator.configure(properties);
					}
				catch (Exception exception)
					{
						logger.error(exception.getLocalizedMessage(), exception);
					}
					
			}
			
		@PostConstruct
		public void init()
			{
				boolean isDeactiveRequired = false;
				List<Tenant> tenants = tenantService.findAllTenant(isDeactiveRequired);
				for (Tenant tenant : tenants)
					{
						String message = "";
						try
							{
								SpringContext.initializeTenantAndLoadBeanFactory(tenant);
								message = ("Tenant with TenantId : " + tenant.getTenantId() + " Successfully Loaded");
							}
						catch (Exception exception)
							{
								logger.error("Error While Loading Tenant with TenantId : " + tenant.getTenantId(), exception);
								message = ("Tenant with TenantId : " + tenant.getTenantId() + " Failed while Loading | Exception : " + exception.getLocalizedMessage());
							}
						notificationService.createNotificationForInitializationOfTenant(tenant, message);
					}
			}
			
		@Value("${mongoDBHost}")
		private String				mongoDBHost;
		
		@Value("${mongoDBPort}")
		private int					mongoDBPort;
		
		@Value("${mongoDBDatabaseName}")
		private String				mongoDBDatabaseName;
		
		@Autowired
		private NotificationService	notificationService;
		
		public @Bean MongoDbFactory mongoDbFactory() throws Exception
			{
				return new SimpleMongoDbFactory(new MongoClient(new MongoClientURI(mongoDBHost + ":" + mongoDBPort)), mongoDBDatabaseName);
			}
			
		public @Bean MongoTemplate mongoTemplate() throws Exception
			{
				MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
				return mongoTemplate;
			}
	}
