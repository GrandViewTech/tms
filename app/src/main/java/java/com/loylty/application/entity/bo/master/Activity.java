package com.loylty.application.entity.bo.master;

import javax.persistence.Entity;

import com.loylty.application.entity.bo.base.BaseMetadata;
import com.loylty.application.entity.bo.constants.Status;

@Entity
public class Activity extends BaseMetadata
	{
		private static final long	serialVersionUID	= -5157339652017283627L;
		
		private String				name;
		
		private Status				status;
		
		public String getName()
			{
				return name;
			}
			
		public void setName(String name)
			{
				this.name = name;
			}
			
		public Status getStatus()
			{
				return status;
			}
			
		public void setStatus(Status status)
			{
				this.status = status;
			}
			
		@Override
		public String toString()
			{
				return "Activity [name=" + name + ", status=" + status + "]";
			}
			
		public Activity()
			{
				super();
			}
			
		public Activity(String name)
			{
				super();
				this.name = name;
			}
			
		public Activity(String name, Status status)
			{
				super();
				this.name = name;
				this.status = status;
			}
			
	}
