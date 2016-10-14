package com.loylty.api;

import com.loylty.util.PropertyReader;

public class ApiList
	{
		private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ApiList.class);
		
		private static String getBaseUrl()
			{
				return PropertyReader.getProperties("hostAddress") + "/" + PropertyReader.getProperties("appUrl");
			}
			
		public static String findUsersByUserIds()
			{
				String url = getBaseUrl() + "/" + "resourceWebService/findUsersByUserIds/tenantId/" + PropertyReader.getProperties("tenantId") + "/userId/" + PropertyReader.getProperties("userId");
				logger.info("getTenantTypes : " + url);
				return url;
			}
			
		public static String getIndustries()
			{
				String url = getBaseUrl() + "/" + "resourceWebService/getIndustries/tenantId/" + PropertyReader.getProperties("tenantId") + "/userId/" + PropertyReader.getProperties("userId");
				logger.info("getTenantTypes : " + url);
				return url;
			}
			
		public static String getFeatures()
			{
				String url = getBaseUrl() + "/" + "resourceWebService/getFeatures/tenantId/" + PropertyReader.getProperties("tenantId") + "/userId/" + PropertyReader.getProperties("userId");
				logger.info("getTenantTypes : " + url);
				return url;
			}
			
		public static String getTenantTypes()
			{
				String url = getBaseUrl() + "/" + "resourceWebService/getTenantTypes/tenantId/" + PropertyReader.getProperties("tenantId") + "/userId/" + PropertyReader.getProperties("userId");
				logger.info("getTenantTypes : " + url);
				return url;
			}
			
		public static String getProgramTypes()
			{
				String url = getBaseUrl() + "/" + "resourceWebService/getProgramTypes/tenantId/" + PropertyReader.getProperties("tenantId") + "/userId/" + PropertyReader.getProperties("userId");
				logger.info("getTenantTypes : " + url);
				return url;
			}
			
		public static String getProgramByProgramId(String programId)
			{
				String url = getBaseUrl() + "/" + "resourceWebService/getProgramByProgramId/programId/" + programId + "/tenantId/" + PropertyReader.getProperties("tenantId") + "/userId/" + PropertyReader.getProperties("userId");
				logger.info("getTenantTypes : " + url);
				return url;
			}
	}
