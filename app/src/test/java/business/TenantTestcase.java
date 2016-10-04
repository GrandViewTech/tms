package business;

import java.io.File;
import java.util.Properties;
import java.util.UUID;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.loylty.application.entity.bo.constants.ProgramType;
import com.loylty.application.entity.bo.constants.Status;
import com.loylty.application.entity.bo.master.Program;
import com.loylty.application.entity.bo.master.Tenant;
import com.loylty.application.entity.bo.master.User;
import com.loylty.application.service.app.master.business.TenantService;
import com.loylty.application.service.app.master.business.UserService;

import business.dataset.TenantDataSet;
import business.util.LoggerUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
	{ "classpath:spring/application-config.xml" })
public class TenantTestcase
	{
		private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TenantTestcase.class);
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
		final private static String	FILE_NAME		= "TENANT";
		final private static String	DATA			= FILE_NAME + File.separator + "data";
		final private static String	TESTCASE		= FILE_NAME + File.separator + "testCase";
		@Autowired
		private TenantService		tenantService;
		@Autowired
		private UserService			userService;
		final private static String	emailAddress	= "puneet.sharma@loylty.com";
		final private static String	programName		= "Test Porgram";
		
		final private static String	tenantId		= "0c1e1d29-48ca-46f8-b482-b6b31d1facee";
		
		public void createTenants()
			{
				try
					{
						tenantService.configureTenant(TenantDataSet.getInHouseTenant());
						tenantService.configureTenant(TenantDataSet.getBankTenant());
					}
				catch (Exception exception)
					{
						Assert.assertTrue(exception.getLocalizedMessage(), false);
						logger.error(exception.getLocalizedMessage(), exception);
					}
			}
			
		@Test
		public void createInHouseTenant()
			{
				try
					{
						tenantService.configureTenant(TenantDataSet.getInHouseTenant());
					}
				catch (Exception exception)
					{
						Assert.assertTrue(exception.getLocalizedMessage(), false);
						logger.error(exception.getLocalizedMessage(), exception);
					}
			}
			
		@Test
		public void createBankTenant()
			{
				try
					{
						tenantService.configureTenant(TenantDataSet.getBankTenant());
					}
				catch (Exception exception)
					{
						Assert.assertTrue(exception.getLocalizedMessage(), false);
						logger.error(exception.getLocalizedMessage(), exception);
					}
			}
			
		@Test
		public void createUpdateProgram()
			{
				try
					{
						Program program = new Program();
						program.setName(programName);
						program.setStatus(Status.ACTIVE);
						User user = userService.findUserByEmailAddress(emailAddress);
						program.setCreatedBy(user.getId());
						program.setModifiedBy(user.getId());
						program.setType(ProgramType.ACRUAL);
						tenantService.createOrUpdateProgram(program);
					}
				catch (Exception exception)
					{
						logger.error(exception.getLocalizedMessage(), exception);
					}
			}
			
		@Test
		public void createMultipleProgram()
			{
				try
					{
						int count = 10;
						String programPrefix = "Program_" + UUID.randomUUID();
						User user = userService.findUserByEmailAddress(emailAddress);
						for (Program program : TenantDataSet.getListOfProgram(count, programPrefix, user))
							{
								tenantService.createOrUpdateProgram(program);
								program = tenantService.findProgramByProgramName(program.getName());
								LoggerUtil.log(TESTCASE + File.separator + program.getName(), "createMultipleProgram", program);
							}
					}
				catch (Exception exception)
					{
						logger.error(exception.getLocalizedMessage(), exception);
						Assert.assertTrue(exception.getLocalizedMessage(), false);
					}
			}
			
		@Test
		public void addTenantToProgram() throws Exception
			{
				Program program = tenantService.findProgramByProgramName(programName);
				Tenant tenant = tenantService.findTenantByTenantId(tenantId);
				tenantService.addTenantToProgram(program.getId(), tenant);
				tenant = tenantService.findTenantByTenantId(tenantId);
				boolean isProgramFound = false;
				for (Program prog : tenant.getPrograms())
					{
						String id = prog.getId();
						if ( id.trim().equalsIgnoreCase(program.getId().trim()) )
							{
								isProgramFound = true;
								break;
							}
					}
				LoggerUtil.log(TESTCASE, "addTenantToProgram", tenant);
				Assert.assertTrue("Program with ProgramId :" + program.getId() + " Not Found in Tenant with TenantId : " + tenantId, isProgramFound);
			}
	}
