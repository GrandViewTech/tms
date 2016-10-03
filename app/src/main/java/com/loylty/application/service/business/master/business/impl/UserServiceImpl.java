package com.loylty.application.service.business.master.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.loylty.application.entity.bo.master.Role;
import com.loylty.application.entity.bo.master.User;
import com.loylty.application.service.business.master.business.UserRoleService;
import com.loylty.application.service.business.master.business.UserService;
import com.loylty.application.service.business.master.dao.UserDao;

@Service("userService")
public class UserServiceImpl implements UserService
	{
		@Autowired
		private UserDao			userDao;
		
		@Autowired
		private UserRoleService	userRoleService;
		
		@Override
		@Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
		public User findUserByEmailAddress(String emailAddress)
			{
				User user = userDao.findUserByEmailAddress(emailAddress);
				user.getRoles();
				return user;
			}
			
		@Override
		@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
		public User createOrUpdateUser(User user)
			{
				String emailAddress = user.getEmailAddress();
				User existingUser = userDao.findUserByEmailAddress(emailAddress);
				if ( existingUser == null )
					{
						return userDao.create(user);
					}
				else
					{
						return userDao.update(existingUser);
					}
			}
			
		@Override
		@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
		public User assignRolesToUser(String emailAddress, List<Role> roles)
			{
				User user = findUserByEmailAddress(emailAddress);
				List<Role> resolvedRoles = new ArrayList<Role>();
				for (Role role : roles)
					{
						String roleName = role.getName();
						Role exitingRole = userRoleService.findRoleByRoleName(roleName);
						if ( exitingRole != null )
							{
								resolvedRoles.add(exitingRole);
							}
						else
							{
								// Throw Error
							}
					}
				user.setRoles(resolvedRoles);
				user = userDao.update(user);
				return user;
			}
	}
