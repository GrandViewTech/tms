package com.loylty.application.service.app.master.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.loylty.application.entity.bo.master.Activity;
import com.loylty.application.entity.bo.master.Role;
import com.loylty.application.service.app.master.business.UserActivityService;
import com.loylty.application.service.app.master.business.UserRoleService;
import com.loylty.application.service.app.master.dao.UserRoleDao;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService
	{
		@Autowired
		private UserActivityService	userActivityService;
		
		@Autowired
		private UserRoleDao			userRoleDao;
		
		@Override
		@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
		public Role createRoleOrUpdate(Role role)
			{
				String name = role.getName();
				Role exitingRole = userRoleDao.findRoleByRoleName(name);
				List<Activity> activities = role.getActivities();
				List<Activity> resolvedActivities = new ArrayList<Activity>();
				for (Activity activity : activities)
					{
						String activityName = activity.getName();
						Activity existingActivity = userActivityService.findActivityByActivityName(activityName);
						if ( existingActivity == null )
							{
								existingActivity = userActivityService.createActivity(activity);
							}
						resolvedActivities.add(existingActivity);
					}
				if ( exitingRole == null )
					{
						// create
						role.setActivities(resolvedActivities);
						userRoleDao.create(role);
						return role;
					}
				else
					{
						// update
						exitingRole.setActivities(resolvedActivities);
						userRoleDao.update(exitingRole);
						return exitingRole;
					}
			}
			
		@Override
		public Role findRoleByRoleName(String roleName)
			{
				if ( roleName == null || roleName.trim().length() == 0 ) { return null; }
				return userRoleDao.findRoleByRoleName(roleName);
			}
			
	}
