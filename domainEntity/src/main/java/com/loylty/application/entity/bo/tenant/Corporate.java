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
		
		public String getId()
			{
				return id;
			}
			
		public void setId(String id)
			{
				this.id = id;
			}
			
		public Address getAddress()
			{
				return address;
			}
			
		public void setAddress(Address address)
			{
				this.address = address;
			}
			
		public ContactInfomation getContactInfomation()
			{
				return contactInfomation;
			}
			
		public void setContactInfomation(ContactInfomation contactInfomation)
			{
				this.contactInfomation = contactInfomation;
			}
			
		public List<Brand> getBrands()
			{
				return brands;
			}
			
		public void setBrands(List<Brand> brands)
			{
				this.brands = brands;
			}
			
		public List<Store> getStores()
			{
				return stores;
			}
			
		public void setStores(List<Store> stores)
			{
				this.stores = stores;
			}
			
	}
