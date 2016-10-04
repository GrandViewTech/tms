package com.loylty.application.service.rest.validation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.loylty.application.entity.annotations.UserLevelAuthorization;
import com.loylty.application.entity.bo.master.Activity;
import com.loylty.application.entity.bo.master.Role;
import com.loylty.application.entity.bo.master.User;
import com.loylty.application.entity.exception.UnAuthorizedAccessException;
import com.loylty.application.service.app.master.business.UserService;
import com.loylty.application.service.util.SpringContext;

@Aspect
public class RestApiAcessValidation
	{
		private static org.apache.log4j.Logger	logger	= org.apache.log4j.Logger.getLogger(RestApiAcessValidation.class);
		
		@Autowired
		private UserService						userService;
		
		@Before("execution(* com.loylty.application.service.rest.api..*.*(..))")
		public void test()
			{
				logger.info("test");
			}
			
		@Before("execution(* com.loylty.application.service.rest.api..*.*(..)) && @annotation(userLevelAuthorization) && @annotation(path) && args(tenantId,userId)")
		public void validateUserAccess(Path path, UserLevelAuthorization userLevelAuthorization, String tenantId, String userId)
			{
				Map<String, List<String>> privileges = new LinkedHashMap<String, List<String>>();
				for (com.loylty.application.entity.annotations.Role role : userLevelAuthorization.roles())
					{
						String roleName = role.name();
						List<String> activities = new ArrayList<String>();
						for (String activity : role.activities())
							{
								activities.add(activity);
							}
						privileges.put(roleName, activities);
					}
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
						validateUser(path.value(), tenantId, userId, user.getRoles(), privileges);
					}
				else
					{
						logger.error("Tenant Not Configured");
						UnAuthorizedAccessException authorizedAccessException = new UnAuthorizedAccessException("Tenant with tenantId : " + tenantId + " is not configured in system");
						authorizedAccessException.setTenantId(tenantId);
						authorizedAccessException.setUserId(userId);
						authorizedAccessException.setPrivileges(privileges);
						authorizedAccessException.setApi(path.value());
						throw authorizedAccessException;
						
					}
			}
			
		private void validateUser(String api, String tenantId, String userId, List<Role> userRoles, Map<String, List<String>> privileges)
			{
				Map<String, List<String>> requiredPrevileges = new LinkedHashMap<String, List<String>>();
				for (Map.Entry<String, List<String>> privilege : privileges.entrySet())
					{
						String requiredRole = privilege.getKey();
						List<String> requiredActivities = new ArrayList<String>();
						boolean hasAccessToRole = false;
						for (Role userRole : userRoles)
							{
								String name = userRole.getName();
								if ( requiredRole.trim().equalsIgnoreCase(name) )
									{
										boolean hasAccessToActivity = false;
										for (String requiredActivity : privilege.getValue())
											{
												for (Activity userActivity : userRole.getActivities())
													{
														String userActivityName = userActivity.getName();
														if ( requiredActivity.trim().equalsIgnoreCase(userActivityName.trim()) )
															{
																hasAccessToActivity = true;
															}
													}
												if ( !hasAccessToActivity )
													{
														requiredActivities.add(requiredActivity);
													}
											}
											
										hasAccessToRole = true;
										break;
									}
							}
						if ( hasAccessToRole )
							{
								if ( requiredActivities.size() > 0 )
									{
										requiredPrevileges.put(privilege.getKey(), requiredActivities);
									}
							}
						else
							{
								requiredPrevileges.put(privilege.getKey(), privilege.getValue());
							}
					}
				if ( requiredPrevileges.size() > 0 )
					{
						generateException(api, tenantId, userId, privileges);
					}
			}
			
		private void generateException(String api, String tenantId, String userId, Map<String, List<String>> privileges)
			{
				UnAuthorizedAccessException authorizedAccessException = new UnAuthorizedAccessException("Insufficient Privelidge".toUpperCase());
				authorizedAccessException.setTenantId(tenantId);
				authorizedAccessException.setUserId(userId);
				authorizedAccessException.setPrivileges(privileges);
				authorizedAccessException.setApi(api);
				throw authorizedAccessException;
			}
	}
