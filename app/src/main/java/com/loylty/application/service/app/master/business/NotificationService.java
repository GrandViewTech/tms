package com.loylty.application.service.app.master.business;

import com.loylty.application.entity.bo.event.Notification;
import com.loylty.application.entity.bo.master.Tenant;

public interface NotificationService
	{
		
		void createNotification(Notification notification);
		
		void createNotificationForInitializationOfTenant(Tenant tenant, String message);
		
	}
