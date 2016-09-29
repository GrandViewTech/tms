package com.loylty.application.service.master.dao;

import com.loylty.application.entity.bo.master.User;

public interface UserDao
	{
		
		public User create(User user);
		
		public User update(User user);
		
		public User findUserByEmailAddress(String emailAddress);
		
	}
