package com.loylty.application.entity.bo.constants;

public enum Designation
	{
	MANAGER("MANAGER"), WORKER("WORKER");
		
		private String designation;
		
		private Designation(String designation)
			{
				this.designation = designation;
			}
			
		public String getDesignation()
			{
				return designation;
			}
	}
