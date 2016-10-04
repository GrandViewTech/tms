package com.loylty.application.entity.exception;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ErrorResponse implements Serializable
	{
		
		private static final long			serialVersionUID	= -1013893421305631653L;
		
		private String						tenantName;
		
		private String						apiEndPoint;
		
		private String						tenantId;
		
		private String						userId;
		
		private Map<String, List<String>>	privileges			= new LinkedHashMap<String, List<String>>();
		
		private String						applicationId;
		
		private String						sourceId;
		
		private String						message;
		
		private int							responseCode;
		
		private String						responseMessage;
		
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
			
		public Map<String, List<String>> getPrivileges()
			{
				return privileges;
			}
			
		public void setPrivileges(Map<String, List<String>> privileges)
			{
				this.privileges = privileges;
			}
			
		public String getApplicationId()
			{
				return applicationId;
			}
			
		public void setApplicationId(String applicationId)
			{
				this.applicationId = applicationId;
			}
			
		public String getSourceId()
			{
				return sourceId;
			}
			
		public void setSourceId(String sourceId)
			{
				this.sourceId = sourceId;
			}
			
		public String getMessage()
			{
				return message;
			}
			
		public void setMessage(String message)
			{
				this.message = message;
			}
			
		public int getResponseCode()
			{
				return responseCode;
			}
			
		public void setResponseCode(int responseCode)
			{
				this.responseCode = responseCode;
			}
			
		public String getResponseMessage()
			{
				return responseMessage;
			}
			
		public void setResponseMessage(String responseMessage)
			{
				this.responseMessage = responseMessage;
			}
			
		public ErrorResponse(LidsysWebServiceException lidsysWebServiceException)
			{
				super();
				this.tenantName = lidsysWebServiceException.getTenantName();
				this.apiEndPoint = lidsysWebServiceException.getApiEndPoint();
				this.tenantId = lidsysWebServiceException.getTenantId();
				this.userId = lidsysWebServiceException.getUserId();
				this.privileges = lidsysWebServiceException.getPrivileges();
				this.message = lidsysWebServiceException.getMessage();
				this.responseCode = lidsysWebServiceException.getStatus().getStatusCode();
				this.responseMessage = lidsysWebServiceException.getStatus().getReasonPhrase();
				this.sourceId = lidsysWebServiceException.getSourceId();
				this.applicationId = lidsysWebServiceException.getApplicationId();
			}
			
		@Override
		public String toString()
			{
				return "ErrorResponse [tenantName=" + tenantName + ", apiEndPoint=" + apiEndPoint + ", tenantId=" + tenantId + ", userId=" + userId + ", privileges=" + privileges + ", applicationId=" + applicationId + ", sourceId=" + sourceId + ", message=" + message + ", responseCode=" + responseCode + ", responseMessage=" + responseMessage + "]";
			}
			
	}
