package com.loylty.application.entity.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

/**
 * @author puneets
 *
 */
public class LidsysWebServiceException extends RuntimeException
	{
		private static final long			serialVersionUID	= 4002310400527634102L;
		
		private String						sourceId;
		private String						applicationId;
		private String						tenantId;
		private String						userId;
		private String						tenantName;
		private String						apiEndPoint;
		private String						message;
		private Status						status;
		private Map<String, List<String>>	privileges			= new LinkedHashMap<String, List<String>>();
		
		public LidsysWebServiceException()
			{
				super();
			}
			
		public LidsysWebServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
			{
				super(message, cause, enableSuppression, writableStackTrace);
			}
			
		public LidsysWebServiceException(String message, Throwable cause)
			{
				super(message, cause);
			}
			
		public LidsysWebServiceException(String message)
			{
				super(message);
			}
			
		public LidsysWebServiceException(Throwable cause)
			{
				super(cause);
			}
			
		public String getSourceId()
			{
				return sourceId;
			}
			
		public void setSourceId(String sourceId)
			{
				this.sourceId = sourceId;
			}
			
		public String getApplicationId()
			{
				return applicationId;
			}
			
		public void setApplicationId(String applicationId)
			{
				this.applicationId = applicationId;
			}
			
		public String getTenantId()
			{
				return tenantId;
			}
			
		public void setTenantId(String tenantId)
			{
				this.tenantId = tenantId;
			}
			
		public String getUserId()
			{
				return userId;
			}
			
		public void setUserId(String userId)
			{
				this.userId = userId;
			}
			
		public String getTenantName()
			{
				return tenantName;
			}
			
		public void setTenantName(String tenantName)
			{
				this.tenantName = tenantName;
			}
			
		public String getApiEndPoint()
			{
				return apiEndPoint;
			}
			
		public void setApiEndPoint(String apiEndPoint)
			{
				this.apiEndPoint = apiEndPoint;
			}
			
		@Override
		public String getMessage()
			{
				return message;
			}
			
		public void setMessage(String message)
			{
				this.message = message;
			}
			
		public Map<String, List<String>> getPrivileges()
			{
				return privileges;
			}
			
		public void setPrivileges(Map<String, List<String>> privileges)
			{
				this.privileges = privileges;
			}
			
		public Status getStatus()
			{
				return status;
			}
			
		public void setStatus(Status status)
			{
				this.status = status;
			}
			
		@Override
		public String toString()
			{
				return "LidsysWebServiceException [sourceId=" + sourceId + ", applicationId=" + applicationId + ", tenantId=" + tenantId + ", userId=" + userId + ", tenantName=" + tenantName + ", apiEndPoint=" + apiEndPoint + ", message=" + message + ", status=" + status + ", privileges=" + privileges + "]";
			}
			
	}
