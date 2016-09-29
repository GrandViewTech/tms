package com.loylty.application.entity.bo.tenant;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Address implements Serializable
	{
		private static final long	serialVersionUID	= 1L;
		@Id
		@GeneratedValue(generator = "uuid")
		@GenericGenerator(name = "uuid", strategy = "uuid")
		@Column(columnDefinition = "CHAR(32)")
		private String				id;
		private String				country;
		private String				state;
		private String				city;
		private String				latitude;
		private String				longitude;
		private String				line1;
		private String				line2;
		
		public String getCountry()
			{
				return country;
			}
			
		public void setCountry(String country)
			{
				this.country = country;
			}
			
		public String getState()
			{
				return state;
			}
			
		public void setState(String state)
			{
				this.state = state;
			}
			
		public String getCity()
			{
				return city;
			}
			
		public void setCity(String city)
			{
				this.city = city;
			}
			
		public String getLatitude()
			{
				return latitude;
			}
			
		public void setLatitude(String latitude)
			{
				this.latitude = latitude;
			}
			
		public String getLongitude()
			{
				return longitude;
			}
			
		public void setLongitude(String longitude)
			{
				this.longitude = longitude;
			}
			
	}
