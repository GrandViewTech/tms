package com.loylty.application.service.app.master.business;

import com.loylty.application.entity.bo.master.Role;

public interface UserRoleService
	{
		public Role createRoleOrUpdate(Role role);
		
		public Role findRoleByRoleName(String roleName);
	}
