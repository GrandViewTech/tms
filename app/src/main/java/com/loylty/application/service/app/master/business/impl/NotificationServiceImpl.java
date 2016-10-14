package com.loylty.application.service.app.master.business.impl;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.loylty.application.entity.bo.constants.Category;
import com.loylty.application.entity.bo.constants.Level;
import com.loylty.application.entity.bo.constants.Type;
import com.loylty.application.entity.bo.event.Event;
import com.loylty.application.entity.bo.event.Notification;
import com.loylty.application.entity.bo.event.NotificationHistory;
import com.loylty.application.entity.bo.master.Tenant;
import com.loylty.application.service.app.master.business.NotificationService;
import com.loylty.application.service.app.master.respository.NotificationRepository;

@Service("notificationService")
@PropertySource(value = "classpath:properties/application.properties")
@EnableAsync
@EnableScheduling
public class NotificationServiceImpl implements NotificationService
	{
		private static org.apache.log4j.Logger	logger							= org.apache.log4j.Logger.getLogger(NotificationServiceImpl.class);
		private static volatile boolean			isNotificationServerReachable	= true;
		private static ObjectMapper				objectMapper					= new ObjectMapper();
		private static ObjectWriter				objectWriter					= objectMapper.writerWithDefaultPrettyPrinter();
		
		@Autowired
		private NotificationRepository			notificationRepository;
		
		@Override
		public void createNotification(Notification notification)
			{
				Object[] response = sendNotification(notification);
				
				if ( !(Boolean) response[0] )
					{
						int retry = notification.getRetry() + 1;
						notification.setNotified(false);
						List<NotificationHistory> notificationHistories = notification.getNotificationHistories();
						NotificationHistory notificationHistory = new NotificationHistory();
						notificationHistory.setCause((String) response[1]);
						notificationHistory.setDate(new Date());
						notificationHistory.setSuccess(false);
						notificationHistory.setNotificationId(retry);
						notification.setRetry(retry);
						notificationHistories.add(notificationHistory);
						notification.setNotificationHistories(notificationHistories);
						notification = notificationRepository.save(notification);
					}
			}
			
		@Async
		private void createNotificationDump(Notification notification)
			{
				try
					{
						String fileName = "error.json";
						String date = (new SimpleDateFormat("yyyy_M_dd.hh_mm_ss")).format(new Date());
						String path = "D:\\workspace\\defualt\\suite\\app\\output\\logs" + File.separator + notification.getTenantId() + File.separator + StringUtils.split(date, "\\.")[0] + File.separator + StringUtils.split(date, "\\.")[1] + File.separator + "admin";
						File file = new File(path);
						if ( file.exists() == false )
							{
								FileUtils.forceMkdir(file);
							}
						FileWriter fileWriter = new FileWriter(new File(path + File.separator + fileName));
						fileWriter.write(objectWriter.writeValueAsString(notification));
						fileWriter.flush();
						fileWriter.close();
					}
				catch (Exception exception)
					{
						logger.error(exception.getLocalizedMessage(), exception);
					}
			}
			
		//@Scheduled(fixedRate = 300000 * 2)
		public void resendFailedNotification()
			{
				for (Notification failedNotification : notificationRepository.findByIsNotified(false))
					{
						Object[] response = sendNotification(failedNotification);
						List<NotificationHistory> notificationHistories = failedNotification.getNotificationHistories();
						int retry = failedNotification.getRetry() + 1;
						NotificationHistory notificationHistory = new NotificationHistory();
						notificationHistory.setCause((String) response[1]);
						notificationHistory.setDate(new Date());
						notificationHistory.setSuccess((Boolean) response[0]);
						notificationHistory.setNotificationId(retry);
						failedNotification.setRetry(retry);
						notificationHistories.add(notificationHistory);
						failedNotification.setNotificationHistories(notificationHistories);
						failedNotification.setNotified((Boolean) response[0]);
						failedNotification = notificationRepository.save(failedNotification);
					}
					
			}
			
		private Object[] sendNotification(Notification notification)
			{
				boolean isNotified = false;
				String error = "Success";
				try
					{
						
						RestTemplate restTemplate = new RestTemplate();
						String url = "http://192.168.102.234:8081/notificationSystem-0.0.1-SNAPSHOT/ws/rest/configurationService/addEvent";
						ResponseEntity<Response> responseEntity = restTemplate.postForEntity(url, notification.getEvent(), Response.class);
						if ( responseEntity != null )
							{
								Response response = responseEntity.getBody();
								if ( response.getStatus() == Status.OK.getStatusCode() )
									{
										isNotified = true;
									}
							}
						else
							{
								error = "No Response from Notification System";
							}
					}
				catch (Exception exception)
					{
						
						error = exception.getLocalizedMessage();
						logger.error(exception.getLocalizedMessage(), exception);
						createNotificationDump(notification);
					}
				return new Object[]
					{ isNotified, error };
			}
			
		@Override
		public void createNotificationForInitializationOfTenant(Tenant tenant, String message)
			{
				Notification notification = new Notification();
				notification.setCreatedDate(new Date());
				notification.setTenantId(tenant.getTenantId());
				Event event = new Event();
				event.setCategory(Category.NOTIFICATION);
				event.setLevel(Level.INFO);
				event.setType(Type.APPLICATION);
				event.setTenantId(tenant.getTenantId());
				event.setMessage(message);
				notification.setEvent(event);
				createNotification(notification);
			}
	}
