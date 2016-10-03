package com.loylty.application.service.business.master.dao;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.loylty.application.entity.bo.constants.TenantType;
import com.loylty.application.entity.bo.master.Program;
import com.loylty.application.entity.bo.master.Tenant;

public interface TenantDao
	{
		
		List<Tenant> findAllTenant(boolean isDeactiveRequired);
		
		Tenant save(Tenant tenant) throws SQLException;
		
		Tenant update(Tenant tenant);
		
		DataSource getDataSource();
		
		Tenant findTenantByCorporateNameAndTenantType(String corporateName, TenantType tenantType);
		
		int countTenantByTenantType(boolean isDeactiveRequired, TenantType tenantType);
		
		public Program save(Program program);
		
		public Program update(Program program);
		
		public Program findProgramById(String programId);
		
		public Program findProgramByName(String programName);
		
		public Tenant findTenantByTenantID(String tenantId);
		
	}
