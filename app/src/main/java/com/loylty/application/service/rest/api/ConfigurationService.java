package com.loylty.application.webservice.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.loylty.application.entity.annotations.ApplicationLevelAuthorization;
import com.loylty.application.entity.annotations.UserLevelAuthorization;
import com.loylty.application.entity.bo.master.Tenant;
import com.loylty.application.service.master.business.TenantService;

@Path("configurationService")
public class ConfigurationService
	{
		@Autowired
		private TenantService tenantService;
		
		@GET
		@ApplicationLevelAuthorization
		@Path("findAllTenant/sourceId/{sourceId}/applicationId/{applicationId}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public List<Tenant> getConfiguredTenant(@PathParam("sourceId") String sourceId, @PathParam("applicationId") String applicationId, @DefaultValue("false") @QueryParam("isDeactiveRequired") Boolean isDeactiveRequired, Tenant object) throws Exception
			{
				return tenantService.findAllTenant(isDeactiveRequired);
			}
			
		@GET
		@UserLevelAuthorization(roles =
			{ "DELETE" }, activities =
			{ "CREATE" })
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@Path("test/source/{sourceId}/applicationId/{applicationId}/tenantId/{tenantId}/userId/{userId}")
		public void testPrivilege(@PathParam("sourceId") String sourceId, @PathParam("applicationId") String applicationId, @PathParam("tenantId") String tenantId, @PathParam("userId") String userId) throws Exception
			{
			}
			
	}
