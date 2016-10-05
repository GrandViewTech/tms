package com.loylty.api;

import com.loylty.util.PropertyReader;

public class ApiList
	{
		
		private static String getBaseUrl()
			{
				return PropertyReader.getProperties("hostAddress") + "/" + PropertyReader.getProperties("appUrl");
			}
			
		public static String findUsersByUserIds()
			{
				return getBaseUrl() + "/" + "resourceWebService/findUsersByUserIds/tenantId/" + PropertyReader.getProperties("tenantId") + "/userId/" + PropertyReader.getProperties("userId");
			}
	}
