package com.loylty.util;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class PropertyReader
	{
		private static Properties				properties	= new Properties();
		
		private static org.apache.log4j.Logger	logger		= org.apache.log4j.Logger.getLogger(PropertyConfigurator.class);
		static
			{
				try
					{
						Resource resource = new ClassPathResource("properties" + File.separator + "application.properties");
						properties.load(resource.getInputStream());
						Resource loggerResource = new ClassPathResource("properties" + File.separator + "log4j.properties");
						Properties properties = new Properties();
						properties.load(loggerResource.getInputStream());
						PropertyConfigurator.configure(properties);
					}
				catch (Exception exception)
					{
						logger.error(exception.getLocalizedMessage(), exception);
					}
			}
			
		public static String getProperties(String key)
			{
				return (String) properties.get(key);
			}
			
	}
