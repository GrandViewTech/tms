package com.loylty.application.service.master.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.loylty.application.entity.bo.constants.TenantType;
import com.loylty.application.entity.bo.master.Program;
import com.loylty.application.entity.bo.master.Tenant;
import com.loylty.application.service.master.dao.TenantDao;

@Repository("tenantDao")
public class TenantDaoImpl implements TenantDao
	{
		@PersistenceContext
		private EntityManager	entityManager;
		
		@Autowired
		private DataSource		dataSource;
		
		@Override
		public DataSource getDataSource()
			{
				return dataSource;
			}
			
		public void setDataSource(DataSource dataSource)
			{
				this.dataSource = dataSource;
			}
			
		@Override
		public Tenant save(Tenant tenant) throws SQLException
			{
				entityManager.persist(tenant);
				return tenant;
			}
			
		@Override
		public Tenant update(Tenant tenant)
			{
				tenant = entityManager.merge(tenant);
				return tenant;
			}
			
		@Override
		public Tenant findTenantByCorporateNameAndTenantType(String corporateName, TenantType tenantType)
			{
				String hqlQuery = "SELECT tenant FROM Tenant tenant WHERE lower(tenant.corporateName)=lower(:corporateName) AND tenant.tenantType=:tenantType";
				TypedQuery<Tenant> typedQuery = entityManager.createQuery(hqlQuery, Tenant.class);
				typedQuery.setParameter("corporateName", corporateName.trim().toLowerCase());
				typedQuery.setParameter("tenantType", tenantType);
				List<Tenant> tenants = typedQuery.getResultList();
				if ( tenants.size() > 0 ) { return tenants.get(0); }
				return null;
			}
			
		@Override
		public List<Tenant> findAllTenant(boolean isDeactiveRequired)
			{
				String hqlQuery = "SELECT tenant FROM Tenant tenant ";
				if ( isDeactiveRequired == false )
					{
						hqlQuery += "WHERE tenant.isActive =:isActive";
					}
					
				TypedQuery<Tenant> typedQuery = entityManager.createQuery(hqlQuery, Tenant.class);
				if ( isDeactiveRequired == false )
					{
						typedQuery.setParameter("isActive", true);
					}
					
				List<Tenant> tenants = typedQuery.getResultList();
				return tenants;
			}
			
		@Override
		public int countTenantByTenantType(boolean isDeactiveRequired, TenantType tenantType)
			{
				String hqlQuery = "SELECT new java.lang.Long(count(tenant.name)) FROM Tenant tenant WHERE  tenant.tenantType=:tenantType";
				if ( isDeactiveRequired == false )
					{
						hqlQuery += " AND tenant.isActive =:isActive";
					}
					
				TypedQuery<Long> typedQuery = entityManager.createQuery(hqlQuery, Long.class);
				typedQuery.setParameter("tenantType", tenantType);
				if ( isDeactiveRequired == false )
					{
						typedQuery.setParameter("isActive", true);
					}
				List<Long> tenants = typedQuery.getResultList();
				if ( tenants.size() > 0 ) { return tenants.get(0).intValue(); }
				return 0;
			}
			
		@Override
		public Program save(Program program)
			{
				entityManager.persist(program);
				return program;
			}
			
		@Override
		public Program update(Program program)
			{
				program = entityManager.merge(program);
				return program;
			}
			
		@Override
		public Program findProgramById(String programId)
			{
				String hqlQuery = "SELECT program FROM Program program WHERE program.id=:programId";
				TypedQuery<Program> typedQuery = entityManager.createQuery(hqlQuery, Program.class);
				typedQuery.setParameter("programId", programId.toLowerCase().trim());
				List<Program> programs = typedQuery.getResultList();
				return (programs.size() > 0) ? programs.get(0) : null;
			}
			
		@Override
		public Program findProgramByName(String programName)
			{
				String hqlQuery = "SELECT program FROM Program program WHERE LOWER(program.name)=:programName";
				TypedQuery<Program> typedQuery = entityManager.createQuery(hqlQuery, Program.class);
				typedQuery.setParameter("programName", programName.toLowerCase().trim());
				List<Program> programs = typedQuery.getResultList();
				return (programs.size() > 0) ? programs.get(0) : null;
				
			}
			
		@Override
		public Tenant findTenantByTenantID(String tenantId)
			{
				String hqlQuery = "SELECT tenant FROM Tenant tenant WHERE LOWER(tenant.tenantId)=:tenantId";
				TypedQuery<Tenant> typedQuery = entityManager.createQuery(hqlQuery, Tenant.class);
				typedQuery.setParameter("tenantId", tenantId.toLowerCase().trim());
				List<Tenant> tenants = typedQuery.getResultList();
				return (tenants.size() > 0) ? tenants.get(0) : null;
			}
	}
