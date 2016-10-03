package com.loylty.application.entity.exception;

import java.io.Serializable;

public class ErrorResponse implements Serializable
	{
		
		private static final long	serialVersionUID	= -1013893421305631653L;
		
		private String				tenantName;
		
		private String				apiEndPoint;
		
		private String				tenantId;
		
		private String				userId;
		
		private String[]			requiredRoles;
		
		private String[]			accessLevel;
		
		private String				applicationId;
		
		private String				sourceId;
		
		private String				message;
		
		private int					responseCode;
		
		private String				responseMessage;
		
		public ErrorResponse(LidsysWebServiceException lidsysWebServiceException)
			{
				super();
				this.tenantName = lidsysWebServiceException.getTenantName();
				this.apiEndPoint = lidsysWebServiceException.getApiEndPoint();
				this.tenantId = lidsysWebServiceException.getTenantId();
				this.userId = lidsysWebServiceException.getUserId();
				this.requiredRoles = lidsysWebServiceException.getRequiredRoles();
				this.message = lidsysWebServiceException.getMessage();
				this.responseCode = lidsysWebServiceException.getStatus().getStatusCode();
				this.responseMessage = lidsysWebServiceException.getStatus().getReasonPhrase();
				this.sourceId = lidsysWebServiceException.getSourceId();
				this.applicationId = lidsysWebServiceException.getApplicationId();
				this.accessLevel = lidsysWebServiceException.getAccessLevel();
			}
	}
