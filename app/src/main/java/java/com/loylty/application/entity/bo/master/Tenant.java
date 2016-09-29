package com.loylty.application.entity.bo.master;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.loylty.application.entity.bo.base.BaseMetadata;
import com.loylty.application.entity.bo.constants.TenantType;

@Entity
@javax.persistence.Table(uniqueConstraints =
	{ @javax.persistence.UniqueConstraint(columnNames =
				{ "corporateName", "tenantType" }) })
public class Tenant extends BaseMetadata
	{
		private static final long	serialVersionUID	= 6937429530840778405L;
		
		private String				name;
		
		@Column(unique = true)
		private String				tenantId;
		
		private String				corporateName;
		
		private Boolean				isActive;
		
		@Temporal(TemporalType.TIMESTAMP)
		private Date				createdDate;
		@Temporal(TemporalType.TIMESTAMP)
		private Date				modifiedDate;
		
		private String				userId;
		
		@Enumerated(EnumType.STRING)
		private TenantType			tenantType;
		
		@Column(unique = true)
		private String				tenantSchemaId;
		
		private String				databaseUrl;
		
		@ManyToMany(fetch = FetchType.EAGER)
		@JoinTable(name = "PROGRAM_TENANT", joinColumns = @JoinColumn(name = "TENANT_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "PROGRAM_ID", referencedColumnName = "ID"))
		private List<Program>		programs			= new ArrayList<Program>();
		
		public String getName()
			{
				return name;
			}
			
		public void setName(String name)
			{
				this.name = name;
			}
			
		public String getTenantId()
			{
				return tenantId;
			}
			
		public void setTenantId(String tenantId)
			{
				this.tenantId = tenantId;
			}
			
		public String getCorporateName()
			{
				return corporateName;
			}
			
		public void setCorporateName(String corporateName)
			{
				this.corporateName = corporateName;
			}
			
		public Boolean getIsActive()
			{
				return isActive;
			}
			
		public void setIsActive(Boolean isActive)
			{
				this.isActive = isActive;
			}
			
		public Date getCreatedDate()
			{
				return createdDate;
			}
			
		@PrePersist
		public void setCreatedDate()
			{
				this.createdDate = new Date();
				this.modifiedDate = new Date();
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
			
		public String getUserId()
			{
				return userId;
			}
			
		public void setUserId(String userId)
			{
				this.userId = userId;
			}
			
		public TenantType getTenantType()
			{
				return tenantType;
			}
			
		public void setTenantType(TenantType tenantType)
			{
				this.tenantType = tenantType;
			}
			
		public String getTenantSchemaId()
			{
				return tenantSchemaId;
			}
			
		public void setTenantSchemaId(String tenantSchemaId)
			{
				this.tenantSchemaId = tenantSchemaId;
			}
			
		public String getDatabaseUrl()
			{
				return databaseUrl;
			}
			
		public void setDatabaseUrl(String databaseUrl)
			{
				this.databaseUrl = databaseUrl;
				
			}
			
		public List<Program> getPrograms()
			{
				return programs;
			}
			
		public void setPrograms(List<Program> programs)
			{
				this.programs = programs;
			}
			
		@Override
		public int hashCode()
			{
				final int prime = 31;
				int result = 1;
				result = prime * result + ((tenantId == null) ? 0 : tenantId.hashCode());
				return result;
			}
			
		@Override
		public String toString()
			{
				return "Tenant [name=" + name + ", tenantId=" + tenantId + ", corporateName=" + corporateName + ", isActive=" + isActive + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", userId=" + userId + ", tenantType=" + tenantType + ", tenantSchemaId=" + tenantSchemaId + ", databaseUrl=" + databaseUrl + "]";
			}
			
		@Override
		public boolean equals(Object obj)
			{
				if ( this == obj ) { return true; }
				if ( obj == null ) { return false; }
				if ( getClass() != obj.getClass() ) { return false; }
				Tenant other = (Tenant) obj;
				if ( tenantId == null )
					{
						if ( other.tenantId != null ) { return false; }
					}
				else if ( !tenantId.equals(other.tenantId) ) { return false; }
				return true;
			}
			
	}
