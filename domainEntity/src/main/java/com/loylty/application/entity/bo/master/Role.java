package com.loylty.application.entity.bo.master;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loylty.application.entity.bo.base.BaseMetadata;
import com.loylty.application.entity.bo.constants.Status;

@Entity
public class Role extends BaseMetadata
	{
		private static final long	serialVersionUID	= -6513021557690386460L;
		private String				name;
		@OneToMany(fetch = FetchType.EAGER)
		@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
		private List<Activity>		activities			= new ArrayList<Activity>();
		private Status				status;
		@JsonIgnore
		@ManyToMany(mappedBy = "roles")
		private List<User>			users;
		
		public String getName()
			{
				return name;
			}
			
		public void setName(String name)
			{
				this.name = name;
			}
			
		public List<Activity> getActivities()
			{
				return activities;
			}
			
		public void setActivities(List<Activity> activities)
			{
				this.activities = activities;
			}
			
		public Status getStatus()
			{
				return status;
			}
			
		public void setStatus(Status status)
			{
				this.status = status;
			}
			
		public List<User> getUsers()
			{
				return users;
			}
			
		public void setUsers(List<User> users)
			{
				this.users = users;
			}
			
		@Override
		public String toString()
			{
				return "Role [name=" + name + ", activities=" + activities + ", status=" + status + "]";
			}
			
	}
