package com.loylty.application.entity.bo.master;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.loylty.application.entity.bo.base.BaseMetadata;

@Entity
public class Country extends BaseMetadata
	{
		private static final long	serialVersionUID	= 5011436659222205195L;
		@Column(unique = true)
		private String				name;
		@Column(unique = true)
		private String				code;
		
		@OrderBy("name ASC")
		@OneToMany(targetEntity = State.class, mappedBy = "country", cascade =
			{ CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true)
		private List<State>			states				= new ArrayList<State>();
		
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
			
		public List<State> getStates()
			{
				return states;
			}
			
		public void setStates(List<State> states)
			{
				this.states = states;
			}
			
	}
