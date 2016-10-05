package com.loylty.application.service.app.master.dao;

import java.util.List;

import com.loylty.application.entity.bo.master.User;

public interface UserDao
	{
		
		public User create(User user);
		
		public User update(User user);
		
		public User findUserByEmailAddress(String emailAddress);
		
		public List<User> findUsersByUserIds(List<String> userIds);
		
	}
