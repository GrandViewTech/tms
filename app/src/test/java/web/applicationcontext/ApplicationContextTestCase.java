package web.applicationcontext;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.loylty.application.entity.bo.master.Tenant;
import com.loylty.application.service.master.business.TenantService;

public class ApplicationContextTestCase
	{
		
		@Test
		public void testTemplateApplicationContext() throws BeansException, IOException
			{
				ApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:spring" + File.separator + "application-config.xml");
				TenantService tenantService = (TenantService) applicationContext.getBean("tenantService");
				String applicationName = applicationContext.getApplicationName();
				System.out.println("applicationName : " + applicationName);
				for (Tenant tenant : tenantService.findAllTenant(true))
					{
						System.out.println("tenantName : " + tenant.getCorporateName());
					}
			}
			
	}
