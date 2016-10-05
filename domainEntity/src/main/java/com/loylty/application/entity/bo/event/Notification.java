package com.loylty.application.entity.bo.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Notification")
public class Notification
	{
		@Id
		private String						id;
		private String						tenantId;
		private boolean						isNotified;
		private Event						event;
		private Date						createdDate;
		private int							retry					= 0;
		private List<NotificationHistory>	notificationHistories	= new ArrayList<NotificationHistory>();
		
		public String getTenantId()
			{
				return tenantId;
			}
			
		public void setTenantId(String tenantId)
			{
				this.tenantId = tenantId;
			}
			
		public boolean isNotified()
			{
				return isNotified;
			}
			
		public void setNotified(boolean isNotified)
			{
				this.isNotified = isNotified;
			}
			
		public Event getEvent()
			{
				return event;
			}
			
		public void setEvent(Event event)
			{
				this.event = event;
			}
			
		public String getId()
			{
				return id;
			}
			
		public void setId(String id)
			{
				this.id = id;
			}
			
		public Date getCreatedDate()
			{
				return createdDate;
			}
			
		public void setCreatedDate(Date createdDate)
			{
				this.createdDate = createdDate;
			}
			
		public int getRetry()
			{
				return retry;
			}
			
		public void setRetry(int retry)
			{
				this.retry = retry;
			}
			
		public List<NotificationHistory> getNotificationHistories()
			{
				return notificationHistories;
			}
			
		public void setNotificationHistories(List<NotificationHistory> notificationHistories)
			{
				this.notificationHistories = notificationHistories;
			}
			
	}
