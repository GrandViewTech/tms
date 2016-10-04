package com.loylty.application.entity.bo.master;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.loylty.application.entity.bo.base.BaseMetadata;

@Entity
public class User extends BaseMetadata
	{
		private static final long	serialVersionUID	= -4762319980894026830L;
		
		private String				firstName;
		
		private String				lastName;
		
		private String				middleName;
		
		private String				displayName;
		
		private String				mobileNumber;
		
		private String				emailAddress;
		
		@ManyToMany(fetch = FetchType.EAGER)
		@JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))
		private List<Role>			roles				= new ArrayList<Role>();
		
		public String getFirstName()
			{
				return firstName;
			}
			
		public void setFirstName(String firstName)
			{
				this.firstName = firstName;
			}
			
		public String getLastName()
			{
				return lastName;
			}
			
		public void setLastName(String lastName)
			{
				this.lastName = lastName;
			}
			
		public String getMiddleName()
			{
				return middleName;
			}
			
		public void setMiddleName(String middleName)
			{
				this.middleName = middleName;
			}
			
		public String getDisplayName()
			{
				return displayName;
			}
			
		public void setDisplayName(String displayName)
			{
				this.displayName = displayName;
			}
			
		public List<Role> getRoles()
			{
				return roles;
			}
			
		public void setRoles(List<Role> roles)
			{
				this.roles = roles;
			}
			
		public String getMobileNumber()
			{
				return mobileNumber;
			}
			
		public void setMobileNumber(String mobileNumber)
			{
				this.mobileNumber = mobileNumber;
			}
			
		public String getEmailAddress()
			{
				return emailAddress;
			}
			
		public void setEmailAddress(String emailAddress)
			{
				this.emailAddress = emailAddress;
			}
			
		@Override
		public String toString()
			{
				return "User [firstName=" + firstName + ", lastName=" + lastName + ", middleName=" + middleName + ", displayName=" + displayName + ", mobileNumber=" + mobileNumber + ", emailAddress=" + emailAddress + ", roles=" + roles + "]";
			}
			
	}
