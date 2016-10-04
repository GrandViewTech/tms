package com.loylty.application.entity.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UnAuthorizedAccessException extends RuntimeException
	{
		private static final long			serialVersionUID	= 6158594750135335525L;
		
		private String						tenantId;
		private String						userId;
		private Map<String, List<String>>	privileges			= new LinkedHashMap<String, List<String>>();
		private String						api;
		
		public String getApi()
			{
				return api;
			}
			
		public void setApi(String api)
			{
				this.api = api;
			}
			
		public String getTenantId()
			{
				if ( tenantId == null || tenantId.trim().length() == 0 )
					{
						tenantId = "anonymous";
					}
				return tenantId;
			}
			
		public void setTenantId(String tenantId)
			{
				this.tenantId = tenantId;
			}
			
		public String getUserId()
			{
				if ( userId == null || userId.trim().length() == 0 )
					{
						userId = "anonymous";
					}
				return userId;
			}
			
		public void setUserId(String userId)
			{
				this.userId = userId;
			}
			
		public Map<String, List<String>> getPrivileges()
			{
				return privileges;
			}
			
		public void setPrivileges(Map<String, List<String>> privileges)
			{
				this.privileges = privileges;
			}
			
		public UnAuthorizedAccessException()
			{
				super();
			}
			
		public UnAuthorizedAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
			{
				super(message, cause, enableSuppression, writableStackTrace);
			}
			
		public UnAuthorizedAccessException(String message, Throwable cause)
			{
				super(message, cause);
			}
			
		public UnAuthorizedAccessException(String message)
			{
				super(message);
			}
			
		public UnAuthorizedAccessException(Throwable cause)
			{
				super(cause);
			}
			
	}
