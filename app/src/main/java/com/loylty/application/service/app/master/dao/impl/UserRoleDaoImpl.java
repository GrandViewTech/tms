package com.loylty.application.service.app.master.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.loylty.application.entity.bo.master.Role;
import com.loylty.application.service.app.master.dao.UserRoleDao;

@Repository("userRoleDao")
public class UserRoleDaoImpl implements UserRoleDao
	{
		
		@PersistenceContext
		private EntityManager entityManager;
		
		@Override
		public Role create(Role role)
			{
				entityManager.persist(role);
				return role;
			}
			
		@Override
		public Role update(Role role)
			{
				role = entityManager.merge(role);
				return role;
			}
			
		@Override
		public Role findRoleByRoleId(String roleId)
			{
				String hqlQuery = "SELECT role FROM Role role WHERE LOWER(role.id) =:roleId";
				TypedQuery<Role> typedQuery = entityManager.createQuery(hqlQuery, Role.class);
				typedQuery.setParameter("roleId", roleId.trim().toLowerCase());
				List<Role> roles = typedQuery.getResultList();
				Role role = null;
				if ( roles.size() > 0 )
					{
						role = roles.get(0);
					}
				return role;
			}
			
		@Override
		public Role findRoleByRoleName(String roleName)
			{
				String hqlQuery = "SELECT role FROM Role role WHERE LOWER(role.name)=:roleName";
				TypedQuery<Role> typedQuery = entityManager.createQuery(hqlQuery, Role.class);
				typedQuery.setParameter("roleName", roleName.trim().toLowerCase());
				List<Role> roles = typedQuery.getResultList();
				Role role = null;
				if ( roles.size() > 0 )
					{
						role = roles.get(0);
					}
				return role;
			}
			
	}
