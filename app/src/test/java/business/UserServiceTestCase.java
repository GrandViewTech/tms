package business;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.loylty.application.entity.bo.constants.Status;
import com.loylty.application.entity.bo.master.Activity;
import com.loylty.application.entity.bo.master.Role;
import com.loylty.application.entity.bo.master.User;
import com.loylty.application.service.business.master.business.UserActivityService;
import com.loylty.application.service.business.master.business.UserRoleService;
import com.loylty.application.service.business.master.business.UserService;

import business.util.LoggerUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
	{ "classpath:spring/application-config.xml" })
public class UserServiceTestCase
	{
		
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
				List<Role> roles = new ArrayList<Role>();
				roles.add(userRoleService.findRoleByRoleName("CRUD"));
				userService.assignRolesToUser(emailAddress, roles);
				User user = userService.findUserByEmailAddress(emailAddress);
				LoggerUtil.log("associateRoleWithUser", user);
			}
	}
