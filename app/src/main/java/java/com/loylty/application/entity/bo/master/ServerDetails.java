package com.loylty.application.entity.bo.master;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.loylty.application.entity.bo.base.BaseMetadata;
import com.loylty.application.entity.bo.constants.ServerType;

@Entity
public class ServerDetails extends BaseMetadata
	{
		
		private static final long	serialVersionUID	= 1L;
		
		private String				name;
		
		private String				ip;
		
		private String				port;
		
		private String				serviceName;
		
		@Enumerated(EnumType.STRING)
		private ServerType			serverType			= ServerType.APPLICATION;
		
		public String getName()
			{
				return name;
			}
			
		public void setName(String name)
			{
				this.name = name;
			}
			
		public String getIp()
			{
				return ip;
			}
			
		public void setIp(String ip)
			{
				this.ip = ip;
			}
			
		public String getPort()
			{
				return port;
			}
			
		public void setPort(String port)
			{
				this.port = port;
			}
			
		public String getServiceName()
			{
				return serviceName;
			}
			
		public void setServiceName(String serviceName)
			{
				this.serviceName = serviceName;
			}
			
		public ServerType getServerType()
			{
				return serverType;
			}
			
		public void setServerType(ServerType serverType)
			{
				this.serverType = serverType;
			}
			
	}
