package com.loylty.application.entity.bo.master;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loylty.application.entity.bo.base.BaseMetadata;

@Entity
public class State extends BaseMetadata
	{
		private static final long	serialVersionUID	= -3985797792898835837L;
		private String				name;
		@JsonIgnore
		private String				code;
		
		@JsonIgnore
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "COUNTRY_ID", nullable = false, referencedColumnName = "id")
		private Country				country;
		
		@OrderBy("name ASC")
		@OneToMany(targetEntity = City.class, cascade =
			{ CascadeType.ALL }, mappedBy = "state", fetch = FetchType.EAGER, orphanRemoval = true)
		private List<City>			cities				= new ArrayList<City>();
		
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
			
		public List<City> getCities()
			{
				return cities;
			}
			
		public void setCities(List<City> cities)
			{
				this.cities = cities;
			}
			
		public Country getCountry()
			{
				return country;
			}
			
		public void setCountry(Country country)
			{
				this.country = country;
			}
			
	}
