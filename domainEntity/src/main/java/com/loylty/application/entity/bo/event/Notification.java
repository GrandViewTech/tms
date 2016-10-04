package com.loylty.application.entity.bo.event;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Notification")
public class Notification
	{
		@Id
		private String	id;
		
		private boolean	isNotified;
		private Event	event;
		
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
			
	}
