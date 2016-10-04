package com.loylty.application.service.app.master.business.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.loylty.application.entity.bo.event.Notification;
import com.loylty.application.enviornment.Application;
import com.loylty.application.service.app.master.business.NotificationService;
import com.loylty.application.service.app.master.respository.NotificationRepository;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService
	{
		private static org.apache.log4j.Logger	logger	= org.apache.log4j.Logger.getLogger(Application.class);
		@Autowired
		private NotificationRepository			notificationRepository;
		
		@Override
		public void createNotification(Notification notification)
			{
				RestTemplate restTemplate = new RestTemplate();
				try
					{
						String url = "http://192.168.102.234:8081/notificationSystem-0.0.1-SNAPSHOT/ws/rest/configurationService/addEvent";
						Response response = restTemplate.postForObject(url, notification.getEvent(), Response.class);
						if ( response.getStatus() == Status.OK.getStatusCode() )
							{
								notification.setNotified(true);
							}
						else
							{
								notification.setNotified(false);
							}
					}
				catch (Exception exception)
					{
						notification.setNotified(false);
						logger.error(exception.getLocalizedMessage(), exception);
					}
				notificationRepository.save(notification);
			}
	}
