package com.loylty.application.service.business.master.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.loylty.application.entity.bo.constants.Status;
import com.loylty.application.entity.bo.master.Activity;
import com.loylty.application.service.business.master.business.UserActivityService;
import com.loylty.application.service.business.master.dao.UserActivityDao;

@Service("userActivityService")
public class UserActivityServiceImpl implements UserActivityService
	{
		@Autowired
		private UserActivityDao userActivityDao;
		
		@Override
		public Activity findActivityByActivityName(String activityName)
			{
				return userActivityDao.findActivityByActivityName(activityName);
			}
			
		@Override
		@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
		public Activity createActivity(Activity activity)
			{
				String activityName = activity.getName();
				Activity existingActivity = findActivityByActivityName(activityName);
				if ( existingActivity == null )
					{
						activity.setStatus(Status.ACTIVE);
						return userActivityDao.create(activity);
					}
				else
					{
						return existingActivity;
					}
			}
			
		@Override
		@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
		public Activity changeStatus(String activityName, Status status)
			{
				Activity existingActivity = findActivityByActivityName(activityName);
				if ( existingActivity != null )
					{
						existingActivity.setStatus(status);
						return userActivityDao.create(existingActivity);
					}
				return null;
			}
			
	}
