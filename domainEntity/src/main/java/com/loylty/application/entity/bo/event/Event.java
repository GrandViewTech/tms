package com.loylty.application.entity.bo.event;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.loylty.application.entity.bo.constants.Category;
import com.loylty.application.entity.bo.constants.Level;
import com.loylty.application.entity.bo.constants.Type;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event implements Serializable
	{
		private static final long	serialVersionUID	= -6902781297529502505L;
		
		private Type				type				= Type.WEB;
		
		private String				message;
		
		private String				createdBy;
		
		private String				tenantId;
		
		private Category			category			= Category.EMAIL;
		
		private Level				level				= Level.INFO;
		
		public Level getLevel()
			{
				return level;
			}
			
		public void setLevel(Level level)
			{
				this.level = level;
			}
			
		public Type getType()
			{
				return type;
			}
			
		public void setType(Type type)
			{
				this.type = type;
			}
			
		public String getMessage()
			{
				return message;
			}
			
		public void setMessage(String message)
			{
				this.message = message;
			}
			
		public String getCreatedBy()
			{
				return createdBy;
			}
			
		public void setCreatedBy(String createdBy)
			{
				this.createdBy = createdBy;
			}
			
		public String getTenantId()
			{
				return tenantId;
			}
			
		public void setTenantId(String tenantId)
			{
				this.tenantId = tenantId;
			}
			
		public Category getCategory()
			{
				return category;
			}
			
		public void setCategory(Category category)
			{
				this.category = category;
			}
			
	}
