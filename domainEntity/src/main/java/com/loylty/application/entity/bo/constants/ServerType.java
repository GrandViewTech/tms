package com.loylty.application.entity.bo.constants;

public enum ServerType
	{
	APPLICATION("APPLICATION"), DATABASE("DATABASE");
		
		private String serverType;
		
		ServerType(String serverType)
			{
				this.serverType = serverType;
			}
			
		public String getServerType()
			{
				return serverType;
			}
	}
