package web.applicationcontext;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.loylty.application.entity.bo.master.Tenant;
import com.loylty.application.service.business.master.business.TenantService;

import business.util.LoggerUtil;

public class ApplicationContextTestCase
	{
		
		final static Logger logger   =  LoggerFactory.ge
		@Test
		public void testTemplateApplicationContext() throws BeansException, IOException
			{
				ApplicationContext applicationContext = null;
				try
					{
						applicationContext = new FileSystemXmlApplicationContext("classpath:spring" + File.separator + "application-config.xml");
						TenantService tenantService = (TenantService) applicationContext.getBean("tenantService");
						String applicationName = applicationContext.getApplicationName();
						for (Tenant tenant : tenantService.findAllTenant(true))
							{
								System.out.println("tenantName : " + tenant.getCorporateName());
							}
					}
				catch (Exception exception)
					{
					}
				finally
					{
					}
			}
			
	}
