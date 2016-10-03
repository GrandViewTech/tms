package com.loylty.application.service.rest.api;

import java.util.ArrayList;
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
import com.loylty.application.entity.bo.constants.TenantType;
import com.loylty.application.service.business.master.business.TenantService;

@Path("resourceWebService")
public class ResourceWebService
	{
		@Autowired
		private TenantService tenantService;
		
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
	}
