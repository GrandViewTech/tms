package com.loylty.application.service.business.master.business;

import java.util.List;

import com.loylty.application.entity.bo.master.Role;
import com.loylty.application.entity.bo.master.User;

public interface UserService
	{
		public User findUserByEmailAddress(String emailAddress);
		
		public User createOrUpdateUser(User user);
		
		public User assignRolesToUser(String emailAddress, List<Role> roles);
	}
