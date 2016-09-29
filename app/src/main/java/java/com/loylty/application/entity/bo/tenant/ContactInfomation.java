package com.loylty.application.entity.bo.tenant;

import javax.persistence.Column;
import javax.persistence.Entity;
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
		
		private Status	status;
		
	}
