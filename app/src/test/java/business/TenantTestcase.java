package business;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.loylty.application.entity.bo.constants.ProgramType;
import com.loylty.application.entity.bo.constants.Status;
import com.loylty.application.entity.bo.constants.TenantType;
import com.loylty.application.entity.bo.master.Program;
import com.loylty.application.entity.bo.master.Tenant;
import com.loylty.application.entity.bo.master.User;
import com.loylty.application.service.business.master.business.TenantService;
import com.loylty.application.service.business.master.business.UserService;

import business.util.LoggerUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
	{ "classpath:spring/application-config.xml" })
public class TenantTestcase
	{
		
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
		
		@Test
		public void createInHouseTenant() throws Exception
			{
				Tenant tenant = new Tenant();
				tenant.setCorporateName("Loylty Rewardz Private Limited");
				tenant.setName("Loylty Rewardz Private Limited");
				tenant.setIsActive(true);
				tenant.setTenantType(TenantType.IN_HOUSE);
				tenantService.configureTenant(tenant);
			}
			
		@Test
		public void createUpdateProgram()
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
