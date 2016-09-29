package com.loylty.application.service.master.dao;

import com.loylty.application.entity.bo.master.Activity;

public interface UserActivityDao
	{
		Activity findActivityByActivityName(String activityName);
		
		Activity create(Activity activity);
		
		Activity update(Activity activity);
	}
