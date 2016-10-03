package com.loylty.application.entity.bo.master;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loylty.application.entity.bo.base.BaseMetadata;

@Entity
public class City extends BaseMetadata
	{
		private static final long	serialVersionUID	= -5590552836276396056L;
		private String				name;
		@JsonIgnore
		private String				code;
		
		@JsonIgnore
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "STATE_ID", referencedColumnName = "id", nullable = false)
		private State				state;
		
		public String getName()
			{
				return name;
			}
			
		public void setName(String name)
			{
				this.name = name;
			}
			
		public String getCode()
			{
				return code;
			}
			
		public void setCode(String code)
			{
				this.code = code;
			}
			
		public State getState()
			{
				return state;
			}
			
		public void setState(State state)
			{
				this.state = state;
			}
			
	}
