package com.loylty.application.webservice.validation;

import javax.ws.rs.Path;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.loylty.application.entity.annotations.ApplicationLevelAuthorization;
import com.loylty.application.entity.annotations.UserLevelAuthorization;
import com.loylty.application.entity.bo.master.Role;
import com.loylty.application.entity.bo.master.User;
import com.loylty.application.entity.exception.UnAuthorizedAccessException;
import com.loylty.application.service.master.business.UserService;
import com.loylty.application.service.util.SpringContext;

@Aspect
public class RestApiAcessValidation
	{
		private static org.apache.log4j.Logger	logger	= org.apache.log4j.Logger.getLogger(RestApiAcessValidation.class);
		@Autowired
		private UserService						userService;
		
		@Before("execution(* com.loylty.application.webservice.api..*.*(..)) && @annotation(applicationLevelAuthorization) && @annotation(path) && args(sourceId,applicationId)")
		public void validateApplicationAccess(Path path, ApplicationLevelAuthorization applicationLevelAuthorization, String sourceId, String applicationId)
			{
				logger.info("Request => sourceIdId :" + sourceId + " | applicationId : " + applicationId);
			}
			
		@Before("execution(* com.loylty.application.webservice.api..*.*(..)) && @annotation(applicationLevelAuthorization) && @annotation(userLevelAuthorization) && @annotation(path) && args(sourceId,applicationId,tenantId,userId)")
		public void validateApplicationAndUserAccess(Path path, ApplicationLevelAuthorization applicationLevelAuthorization, UserLevelAuthorization userLevelAuthorization, String sourceId, String applicationId, String tenantId, String userId)
			{
				logger.info("Request => sourceIdId :" + sourceId + " | applicationId : " + applicationId + " | tenantId : " + tenantId + " | userId : " + userId);
				validateApplicationAccess(path, applicationLevelAuthorization, sourceId, applicationId);
				validateUserAccess(path, userLevelAuthorization, tenantId, userId);
			}
			
		@Before("execution(* com.loylty.application.webservice.api..*.*(..)) && @annotation(userLevelAuthorization) && @annotation(path) && args(tenantId,userId)")
		public void validateUserAccess(Path path, UserLevelAuthorization userLevelAuthorization, String tenantId, String userId)
			{
				if ( SpringContext.isTenantIdConfigured(tenantId) )
					{
						User user = null;
						if ( SpringContext.isInHouseTenantId(tenantId) )
							{
								user = userService.findUserByEmailAddress(userId);
							}
						else
							{
								UserService userService = (UserService) SpringContext.getBean(tenantId, "userService");
								user = userService.findUserByEmailAddress(userId);
							}
						validateUser(path.value(), tenantId, userId, user, userLevelAuthorization.roles(), userLevelAuthorization.activities());
					}
				else
					{
						logger.error("Tenant Not Configured");
						UnAuthorizedAccessException authorizedAccessException = new UnAuthorizedAccessException("Tenant with tenantId : " + tenantId + " is not configured in system");
						authorizedAccessException.setTenantId(tenantId);
						authorizedAccessException.setUserId(userId);
						authorizedAccessException.setRequiredActivities(userLevelAuthorization.activities());
						authorizedAccessException.setRequiredRoles(userLevelAuthorization.roles());
						authorizedAccessException.setApi(path.value());
						throw authorizedAccessException;
						
					}
			}
			
		private void validateUser(String api, String tenantId, String userId, User user, String[] roles, String[] activities)
			{
				if ( user == null )
					{
						UnAuthorizedAccessException authorizedAccessException = new UnAuthorizedAccessException("Invalid User".toUpperCase());
						authorizedAccessException.setTenantId(tenantId);
						authorizedAccessException.setUserId(userId);
						authorizedAccessException.setRequiredActivities(activities);
						authorizedAccessException.setRequiredRoles(roles);
						authorizedAccessException.setApi(api);
						throw authorizedAccessException;
					}
				for (String rs : roles)
					{
						rs = rs.trim();
						boolean hasAccessForRole = false;
						for (Role role : user.getRoles())
							{
								String name = role.getName().trim();
								if ( name.equalsIgnoreCase(rs) )
									{
										hasAccessForRole = true;
										break;
									}
							}
						if ( hasAccessForRole == false )
							{
								UnAuthorizedAccessException authorizedAccessException = new UnAuthorizedAccessException("Insufficient Privelidge".toUpperCase());
								authorizedAccessException.setTenantId(tenantId);
								authorizedAccessException.setUserId(userId);
								authorizedAccessException.setRequiredActivities(activities);
								authorizedAccessException.setRequiredRoles(roles);
								authorizedAccessException.setApi(api);
								throw authorizedAccessException;
							}
					}
			}
			
	}
