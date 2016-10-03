package com.loylty.application.enviornment;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableAsync;

import com.loylty.application.entity.bo.master.Tenant;
import com.loylty.application.service.master.business.TenantService;
import com.loylty.application.service.util.SpringContext;

@EnableAsync
@Configuration
@EnableAspectJAutoProxy
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
						SpringContext.initializeTenantAndLoadBeanFactory(tenant);
					}
			}
	}
