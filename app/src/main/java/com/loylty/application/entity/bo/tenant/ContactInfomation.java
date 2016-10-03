package com.loylty.application.entity.bo.tenant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.loylty.application.entity.bo.constants.Status;

@Entity
public class ContactInfomation
	{
		@Id
		@GeneratedValue(generator = "uuid")
		@GenericGenerator(name = "uuid", strategy = "uuid")
		@Column(columnDefinition = "CHAR(32)")
		private String	id;
		
		private String	name;
		
		private String	designation;
		
		private String	mobileNumber;
		
		private Integer	countryCode;
		
		private String	landLine;
		
		private String	faxNumber;
		
		private String	emailId;
		
		private String	site;
		
		private String	note;
		
		@Enumerated(EnumType.STRING)
		private Status	status	= Status.ACTIVE;
		
		public String getId()
			{
				return id;
			}
			
		public void setId(String id)
			{
				this.id = id;
			}
			
		public String getName()
			{
				return name;
			}
			
		public void setName(String name)
			{
				this.name = name;
			}
			
		public String getDesignation()
			{
				return designation;
			}
			
		public void setDesignation(String designation)
			{
				this.designation = designation;
			}
			
		public String getMobileNumber()
			{
				return mobileNumber;
			}
			
		public void setMobileNumber(String mobileNumber)
			{
				this.mobileNumber = mobileNumber;
			}
			
		public Integer getCountryCode()
			{
				return countryCode;
			}
			
		public void setCountryCode(Integer countryCode)
			{
				this.countryCode = countryCode;
			}
			
		public String getLandLine()
			{
				return landLine;
			}
			
		public void setLandLine(String landLine)
			{
				this.landLine = landLine;
			}
			
		public String getFaxNumber()
			{
				return faxNumber;
			}
			
		public void setFaxNumber(String faxNumber)
			{
				this.faxNumber = faxNumber;
			}
			
		public String getEmailId()
			{
				return emailId;
			}
			
		public void setEmailId(String emailId)
			{
				this.emailId = emailId;
			}
			
		public String getSite()
			{
				return site;
			}
			
		public void setSite(String site)
			{
				this.site = site;
			}
			
		public String getNote()
			{
				return note;
			}
			
		public void setNote(String note)
			{
				this.note = note;
			}
			
		public Status getStatus()
			{
				return status;
			}
			
		public void setStatus(Status status)
			{
				this.status = status;
			}
			
	}
