package business;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.loylty.application.entity.bo.constants.Status;
import com.loylty.application.entity.bo.master.Activity;
import com.loylty.application.entity.bo.master.Role;
import com.loylty.application.entity.bo.master.User;
import com.loylty.application.service.app.master.business.UserActivityService;
import com.loylty.application.service.app.master.business.UserRoleService;
import com.loylty.application.service.app.master.business.UserService;

import business.util.LoggerUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
	{ "classpath:spring/application-config.xml" })
public class UserServiceTestCase
	{
		private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserServiceTestCase.class);
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
		final private static String	FILE_NAME		= "USER";
		final private static String	DATA			= FILE_NAME + File.separator + "data";
		final private static String	TESTCASE		= FILE_NAME + File.separator + "testCase";
		
		private static String		emailAddress	= "puneet.sharma@loylty.com";
		@Autowired
		private UserService			userService;
		
		@Autowired
		private UserActivityService	userActivityService;
		
		@Autowired
		private UserRoleService		userRoleService;
		
		@Test
		public void createUser()
			{
				User user = new User();
				user.setDisplayName("Puneet Sharma");
				user.setFirstName("Puneet");
				user.setMiddleName("kamal");
				user.setLastName("Sharma");
				user.setMobileNumber("9930607358");
				user.setEmailAddress(emailAddress);
				userService.createOrUpdateUser(user);
			}
			
		@Test
		public void createUser2()
			{
				String emailAddress = "Utkarsh.deep@lotlty.com";
				User user = new User();
				user.setDisplayName("Utkarsh Deep");
				user.setFirstName("Utkarsh");
				user.setLastName("Deep");
				user.setMobileNumber("9793566416");
				user.setEmailAddress(emailAddress);
				userService.createOrUpdateUser(user);
				User updated = userService.findUserByEmailAddress(emailAddress);
				Assert.assertTrue("Email Address does not match", emailAddress.trim().equalsIgnoreCase(updated.getEmailAddress()));
				
			}
			
		@Test
		public void createActivity()
			{
				Activity activity = new Activity();
				activity.setName("CREATE");
				activity.setStatus(Status.ACTIVE);
				userActivityService.createActivity(activity);
			}
			
		@Test
		public void createRole()
			{
				List<Activity> activities = new ArrayList<Activity>();
				activities.add(new Activity("CREATE"));
				activities.add(new Activity("UPDATE"));
				Role role = new Role();
				role.setName("CRUD");
				role.setStatus(Status.ACTIVE);
				role.setActivities(activities);
				userRoleService.createRoleOrUpdate(role);
			}
			
		@Test
		public void associateRoleWithUser()
			{
				try
					{
						List<Role> roles = new ArrayList<Role>();
						roles.add(userRoleService.findRoleByRoleName("CRUD"));
						userService.assignRolesToUser(emailAddress, roles);
						User user = userService.findUserByEmailAddress(emailAddress);
						LoggerUtil.log(TESTCASE, "associateRoleWithUser", user);
					}
				catch (Exception exception)
					{
						logger.error(exception.getLocalizedMessage(), exception);
					}
			}
			
		@Test
		public void findUsersByUserIds()
			{
				List<String> userIds = new ArrayList<String>();
				userIds.add("4028e6fa578ed4a501578ed4b4be0000");
				userIds.add("4028e6fa579450ed015794513ac00000");
				Map<String, Object> results = new LinkedHashMap<String, Object>();
				results.put("userIds", userIds);
				List<User> users = userService.findUsersByUserIds(userIds);
				results.put("users", users);
				for (User user : users)
					{
						String id = user.getId();
						boolean isUserFound = false;
						for (String testData : userIds)
							{
								if ( testData.trim().equalsIgnoreCase(id.trim()) )
									{
										isUserFound = true;
										break;
									}
							}
						Assert.assertTrue("User Id  does not match", isUserFound);
					}
				LoggerUtil.log(TESTCASE, "findUsersByUserIds", results);
			}
	}
