package com.loylty.application.service.business.master.business.impl;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.loylty.application.entity.bo.constants.TenantType;
import com.loylty.application.entity.bo.master.Program;
import com.loylty.application.entity.bo.master.Tenant;
import com.loylty.application.service.business.master.business.TenantService;
import com.loylty.application.service.business.master.dao.TenantDao;
import com.loylty.application.service.business.util.SpringContext;

@Service("tenantService")
public class TenantServiceImpl implements TenantService
	{
		
		@Autowired
		private TenantDao tenantDao;
		
		@Override
		public List<Tenant> findAllTenant(boolean isDeactiveRequired)
			{
				return tenantDao.findAllTenant(isDeactiveRequired);
			}
			
		@Override
		@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
		public Tenant configureTenant(Tenant tenant) throws Exception
			{
				Connection connection = null;
				Statement statement = null;
				try
					{
						String name = tenant.getCorporateName();
						TenantType tenantType = tenant.getTenantType();
						Tenant existingTenant = tenantDao.findTenantByCorporateNameAndTenantType(name, tenantType);
						if ( existingTenant == null )
							{
								String tenantId = UUID.randomUUID().toString();
								String userId = "USER_ID_" + UUID.randomUUID().toString();
								String tenantSchemaId = ("TENANT_" + System.nanoTime()).toLowerCase().trim();
								tenant.setTenantId(tenantId);
								tenant.setIsActive(true);
								tenant.setTenantSchemaId(tenantSchemaId);
								tenant.setUserId(userId);
								tenant.setDatabaseUrl("");
								DataSource dataSource = tenantDao.getDataSource();
								connection = dataSource.getConnection();
								connection.setAutoCommit(false);
								tenantDao.save(tenant);
								String sql = "CREATE DATABASE " + tenant.getTenantSchemaId();
								statement = connection.createStatement();
								statement.executeUpdate(sql);
								connection.commit();
								SpringContext.initializeTenantAndLoadBeanFactory(tenant);
								return tenant;
							}
						else
							{
								return existingTenant;
							}
					}
				catch (Exception exception)
					{
						if ( connection != null )
							{
								connection.rollback();
							}
						throw exception;
					}
				finally
					{
						if ( statement != null )
							{
								statement.close();
							}
						if ( connection != null )
							{
								connection.close();
							}
					}
					
			}
			
		@Override
		public Integer countTenantByTenantType(boolean isDeactiveRequired, TenantType tenantType)
			{
				return tenantDao.countTenantByTenantType(isDeactiveRequired, tenantType);
			}
			
		@Override
		@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
		public Program createOrUpdateProgram(Program program)
			{
				String programName = program.getName();
				
				Program existingProgram = tenantDao.findProgramByName(programName);
				
				if ( existingProgram == null )
					{
						// Create
						return tenantDao.save(program);
					}
				else
					{
						// Update
						existingProgram.setStatus(program.getStatus());
						return tenantDao.update(existingProgram);
					}
			}
			
		@Override
		@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
		public Tenant addTenantToProgram(String programId, Tenant tenant) throws Exception
			{
				tenant = configureTenant(tenant);
				Program program = tenantDao.findProgramById(programId);
				boolean isProgramFound = false;
				for (Program pgm : tenant.getPrograms())
					{
						String existingProgramId = pgm.getId();
						if ( existingProgramId.trim().equalsIgnoreCase(program.getId().trim()) )
							{
								isProgramFound = true;
								break;
							}
					}
				if ( !isProgramFound )
					{
						List<Program> updatedList = tenant.getPrograms();
						updatedList.add(program);
						tenant.setPrograms(updatedList);
						tenant = tenantDao.update(tenant);
					}
				return tenant;
			}
			
		@Override
		public Program removeTenantFromProgram(String programId, String tenantId)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
		@Override
		public Program findProgramByProgramName(String programName)
			{
				return tenantDao.findProgramByName(programName);
			}
			
		@Override
		public Tenant findTenantByTenantId(String tenantId)
			{
				return tenantDao.findTenantByTenantID(tenantId);
			}
			
	}
