package com.loylty.application.service.rest.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.loylty.application.entity.annotations.ApplicationLevelAuthorization;
import com.loylty.application.entity.annotations.Role;
import com.loylty.application.entity.annotations.UserLevelAuthorization;
import com.loylty.application.entity.bo.constants.TenantType;
import com.loylty.application.entity.bo.master.Program;
import com.loylty.application.entity.bo.master.User;
import com.loylty.application.service.app.master.business.TenantService;
import com.loylty.application.service.app.master.business.UserService;
import com.loylty.application.service.util.SpringContext;

@Path("resourceWebService")
public class ResourceWebService
	{
		@Autowired
		private TenantService	tenantService;
		
		@Autowired
		private UserService		userService;
		
		@GET
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@ApplicationLevelAuthorization
		@Path("countTenantByTenantType/sourceId/{sourceId}/applicationId/{applicationId}/tenantType/{tenantType}")
		public int countTenantByTenantType(@PathParam("sourceId") String sourceId, @PathParam("applicationId") String applicationId, @PathParam("tenantType") TenantType tenantType, @DefaultValue("false") @QueryParam("isDeactiveRequired") Boolean isDeactiveRequired) throws Exception
			{
				return tenantService.countTenantByTenantType(isDeactiveRequired, tenantType);
			}
			
		@GET
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@ApplicationLevelAuthorization
		@Path("getTenantType/sourceId/{sourceId}/applicationId/{applicationId}")
		public List<String> getTenantType(@PathParam("sourceId") String sourceId, @PathParam("applicationId") String applicationId) throws Exception
			{
				List<String> tenantTypes = new ArrayList<String>();
				for (TenantType tenantType : TenantType.values())
					{
						tenantTypes.add(tenantType.getTenantType());
					}
				return tenantTypes;
			}
			
		@GET
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@Path("getProgramList/tenantId/{tenantId}/userId/{userId}")
		@UserLevelAuthorization(roles =
			{ @Role(name = "CRUD", activities =
						{ "CREATE" }) })
		public List<Program> getProgramList(@PathParam("tenantId") String tenantId, @PathParam("userId") String userId, @DefaultValue("false") @QueryParam("isDeactivatedRequired") boolean isDeactivatedRequired)
			{
				return tenantService.findAllProgram(isDeactivatedRequired);
			}
			
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@Path("findUsersByUserIds/tenantId/{tenantId}/userId/{userId}")
		@UserLevelAuthorization(roles =
			{ @Role(name = "CRUD", activities =
						{ "CREATE" }) })
		public List<User> findUsersByUserIds(@PathParam("tenantId") String tenantId, @PathParam("userId") String userId, List<String> userIds)
			{
				if ( SpringContext.isInHouseTenantId(tenantId) )
					{
						return userService.findUsersByUserIds(userIds);
					}
				else
					{
						UserService userService = (UserService) SpringContext.getBean(tenantId, "userService");
						return userService.findUsersByUserIds(userIds);
					}
			}
	}
