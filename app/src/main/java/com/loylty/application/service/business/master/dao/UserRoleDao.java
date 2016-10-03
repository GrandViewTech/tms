package com.loylty.application.service.business.master.dao;

import com.loylty.application.entity.bo.master.Role;

/**
 * 
 * @author puneets
 *
 */
public interface UserRoleDao
	{
		public Role create(Role role);
		
		public Role update(Role role);
		
		public Role findRoleByRoleId(String roleId);
		
		public Role findRoleByRoleName(String name);
	}
