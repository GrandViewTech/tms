package com.loylty.application.entity.bo.master;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loylty.application.entity.bo.constants.ProgramType;
import com.loylty.application.entity.bo.constants.Status;

@Entity
public class Program implements Serializable
	{
		private static final long	serialVersionUID	= 1L;
		@javax.persistence.Id
		@GeneratedValue(generator = "uuid")
		@GenericGenerator(name = "uuid", strategy = "uuid")
		@Column(columnDefinition = "CHAR(32)")
		private String				id;
		
		public String getId()
			{
				return id;
			}
			
		public void setId(String id)
			{
				this.id = id;
			}
			
		private Date			createdDate;
		
		private Date			modifiedDate;
		
		@Column(nullable = false)
		private String			createdBy;
		
		@Column(nullable = false)
		private String			modifiedBy;
		
		@Column(nullable = false)
		private String			name;
		
		@Column(nullable = false)
		@Enumerated(EnumType.STRING)
		private ProgramType		type;
		
		@JsonIgnore
		@ManyToMany(mappedBy = "programs")
		private List<Tenant>	tenants	= new ArrayList<Tenant>();
		
		@Column(nullable = false)
		@Enumerated(EnumType.STRING)
		private Status			status;
		
		public String getName()
			{
				return name;
			}
			
		public void setName(String name)
			{
				this.name = name;
			}
			
		public ProgramType getType()
			{
				return type;
			}
			
		public void setType(ProgramType type)
			{
				this.type = type;
			}
			
		public Status getStatus()
			{
				if ( status == null )
					{
						status = Status.ACTIVE;
					}
				return status;
			}
			
		public void setStatus(Status status)
			{
				if ( status == null )
					{
						status = Status.ACTIVE;
					}
				this.status = status;
			}
			
		public Date getCreatedDate()
			{
				return createdDate;
			}
			
		@PrePersist
		public void setCreatedDate()
			{
				this.createdDate = new Date();
				this.modifiedDate = this.createdDate;
			}
			
		public Date getModifiedDate()
			{
				return modifiedDate;
			}
			
		@PreUpdate
		public void setModifiedDate()
			{
				this.modifiedDate = new Date();
			}
			
		public String getCreatedBy()
			{
				return createdBy;
			}
			
		public void setCreatedBy(String createdBy)
			{
				this.createdBy = createdBy;
			}
			
		public String getModifiedBy()
			{
				return modifiedBy;
			}
			
		public void setModifiedBy(String modifiedBy)
			{
				this.modifiedBy = modifiedBy;
			}
			
	}
