package com.loylty.application.service.app.master.dao;

import com.loylty.application.entity.bo.master.Activity;

public interface UserActivityDao
	{
		Activity findActivityByActivityName(String activityName);
		
		Activity create(Activity activity);
		
		Activity update(Activity activity);
	}
