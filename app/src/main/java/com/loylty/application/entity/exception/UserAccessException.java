package com.loylty.application.entity.exception;

public class UserAccessException extends RuntimeException
	{
		
		private static final long serialVersionUID = 9115595441757143517L;
		
		public UserAccessException()
			{
			}
			
		public UserAccessException(String message)
			{
				super(message);
			}
			
		public UserAccessException(Throwable cause)
			{
				super(cause);
			}
			
		public UserAccessException(String message, Throwable cause)
			{
				super(message, cause);
			}
			
		public UserAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
			{
				super(message, cause, enableSuppression, writableStackTrace);
			}
			
	}
