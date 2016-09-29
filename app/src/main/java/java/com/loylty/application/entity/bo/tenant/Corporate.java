package com.loylty.application.entity.bo.tenant;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Corporate
	{
		
		@Id
		@GeneratedValue(generator = "uuid")
		@GenericGenerator(name = "uuid", strategy = "uuid")
		@Column(columnDefinition = "CHAR(32)")
		private String				id;
		@OneToOne
		private Address				address;
		@OneToOne
		private ContactInfomation	contactInfomation;
		private List<Brand>			brands	= new ArrayList<Brand>();
		private List<Store>			stores	= new ArrayList<Store>();
	}
