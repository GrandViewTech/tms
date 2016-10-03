package com.loylty.application.entity.exception;

import java.util.Arrays;

import javax.ws.rs.core.Response.Status;

/**
 * @author puneets
 *
 */
public class LidsysWebServiceException extends RuntimeException
	{
		private static final long	serialVersionUID	= 4002310400527634102L;
		
		private String				sourceId;
		private String				applicationId;
		private String				tenantId;
		private String				userId;
		private String				tenantName;
		private String				apiEndPoint;
		private String[]			requiredRoles;
		private String				message;
		private Status				status;
		private String[]			accessLevel;
		
		public String[] getAccessLevel()
			{
				return accessLevel;
			}
			
		public void setAccessLevel(String[] accessLevel)
			{
				this.accessLevel = accessLevel;
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
			
		public String[] getRequiredRoles()
			{
				return requiredRoles;
			}
			
		public void setRequiredRoles(String[] requiredRoles)
			{
				this.requiredRoles = requiredRoles;
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
			
		public Status getStatus()
			{
				return status;
			}
			
		public void setStatus(Status status)
			{
				this.status = status;
			}
			
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
			
		@Override
		public String toString()
			{
				return "LidsysWebServiceException [sourceId=" + sourceId + ", applicationId=" + applicationId + ", tenantId=" + tenantId + ", userId=" + userId + ", tenantName=" + tenantName + ", apiEndPoint=" + apiEndPoint + ", requiredRoles=" + Arrays.toString(requiredRoles) + ", message=" + message + ", status=" + status + ", accessLevel=" + Arrays.toString(accessLevel) + "]";
			}
			
	}
