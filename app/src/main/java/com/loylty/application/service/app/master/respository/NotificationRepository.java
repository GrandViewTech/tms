package com.loylty.application.service.app.master.respository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.loylty.application.entity.bo.event.Notification;

@Repository("notificationRepository")
public interface NotificationRepository extends CrudRepository<Notification, String>
	{
		
	}
