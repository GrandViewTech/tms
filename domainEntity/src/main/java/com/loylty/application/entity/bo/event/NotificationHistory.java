package com.loylty.application.entity.bo.event;

import java.io.Serializable;
import java.util.Date;

public class NotificationHistory implements Serializable
	{
		
		private static final long	serialVersionUID	= -6494092522741939112L;
		private int					notificationId;
		private String				cause;
		private Date				date;
		private Boolean				success				= false;
		
		public int getNotificationId()
			{
				return notificationId;
			}
			
		public void setNotificationId(int notificationId)
			{
				this.notificationId = notificationId;
			}
			
		public String getCause()
			{
				return cause;
			}
			
		public void setCause(String cause)
			{
				this.cause = cause;
			}
			
		public Date getDate()
			{
				return date;
			}
			
		public void setDate(Date date)
			{
				this.date = date;
			}
			
		public Boolean getSuccess()
			{
				return success;
			}
			
		public void setSuccess(Boolean success)
			{
				this.success = success;
			}
			
	}
