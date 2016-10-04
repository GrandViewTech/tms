package com.loylty.application.entity.bo.constants;

public enum TenantType
	{
	IN_HOUSE("IN_HOUSE"), BANK("BANK"), PARTNER("PARTNER"), RETAINER("RETAINER");
		
		private String tenantType;
		
		private TenantType(String tenantType)
			{
				this.tenantType = tenantType;
			}
			
		public String getTenantType()
			{
				return tenantType;
			}
	}
