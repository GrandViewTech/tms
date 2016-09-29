package com.loylty.application.service.master.business;

import com.loylty.application.entity.bo.constants.Status;
import com.loylty.application.entity.bo.master.Activity;

public interface UserActivityService
	{
		public Activity findActivityByActivityName(String activityName);
		
		public Activity createActivity(Activity activity);
		
		public Activity changeStatus(String activityName, Status status);
		
	}
