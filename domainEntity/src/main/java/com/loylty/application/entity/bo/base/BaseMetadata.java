package com.loylty.application.entity.bo.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class BaseMetadata implements Serializable
	{
		private static final long	serialVersionUID	= 1L;
		@Id
		@GeneratedValue(generator = "uuid")
		@GenericGenerator(name = "uuid", strategy = "uuid")
		@Column(columnDefinition = "CHAR(32)")
		private String				id;
		
		public String getId()
			{
				return id;
			}
			
		public void setId(String id)
			{
				this.id = id;
			}
	}
