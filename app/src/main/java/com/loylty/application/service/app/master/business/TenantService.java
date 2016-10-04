package com.loylty.application.service.app.master.business;

import java.util.List;

import com.loylty.application.entity.bo.constants.TenantType;
import com.loylty.application.entity.bo.master.Program;
import com.loylty.application.entity.bo.master.Tenant;

public interface TenantService
	{
		
		public Tenant configureTenant(Tenant tenant) throws Exception;
		
		public Integer countTenantByTenantType(boolean isDeactiveRequired, TenantType tenantType);
		
		public List<Tenant> findAllTenant(boolean isDeactiveRequired);
		
		public Program createOrUpdateProgram(Program program);
		
		public Tenant addTenantToProgram(String programId, Tenant tenant) throws Exception;
		
		public Program removeTenantFromProgram(String programId, String tenantId);
		
		public Program findProgramByProgramName(String programName);
		
		public Tenant findTenantByTenantId(String tenantId);
		
		public List<Program> findAllProgram(boolean isDeactivatedRequired);
		
	}
