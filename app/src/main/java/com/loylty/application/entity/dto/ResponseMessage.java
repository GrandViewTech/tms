package com.loylty.application.entity.dto;

import java.io.Serializable;

public class ResponseMessage implements Serializable
	{
		/**
		* 
		*/
		private static final long	serialVersionUID	= -4530879886249758932L;
		private String				errorMessage;
		private Boolean				success				= false;
		
		public String getErrorMessage()
			{
				return errorMessage;
			}
			
		public void setErrorMessage(String errorMessage)
			{
				this.errorMessage = errorMessage;
			}
			
		public Boolean getSuccess()
			{
				return success;
			}
			
		public void setSuccess(Boolean success)
			{
				this.success = success;
			}
			
		@Override
		public String toString()
			{
				return "ResponseMessage [errorMessage=" + errorMessage + ", success=" + success + "]";
			}
			
	}
