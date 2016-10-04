package web.applicationcontext;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.loylty.application.entity.bo.master.Tenant;
import com.loylty.application.service.app.master.business.TenantService;

public class ApplicationContextTestCase
	{
		
		private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ApplicationContextTestCase.class);
		
		@Test
		public void testTemplateApplicationContext() throws BeansException, IOException
			{
				ApplicationContext applicationContext = null;
				try
					{
						applicationContext = new FileSystemXmlApplicationContext("classpath:spring" + File.separator + "application-config.xml");
						TenantService tenantService = (TenantService) applicationContext.getBean("tenantService");
						String applicationName = applicationContext.getApplicationName();
						logger.info("applicationName : " + applicationName);
						for (Tenant tenant : tenantService.findAllTenant(true))
							{
								logger.info("tenantName : " + tenant.getCorporateName());
							}
					}
				catch (Exception exception)
					{
						logger.error(exception.getLocalizedMessage(), exception);
					}
				finally
					{
						if ( applicationContext != null )
							{
								applicationContext = null;
							}
					}
			}
			
	}
