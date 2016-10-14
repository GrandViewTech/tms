package com.loylty.application.entity.bo.constants;

public enum ProgramType
	{
		
	ACRUAL("ACRUAL"), REDEMPTION("REDEMPTION"), ACRUAL_REDEMPTION("ACRUAL REDEMPTION");
		
		private String programType;
		
		private ProgramType(String programType)
			{
				this.programType = programType;
			}
			
		public String getProgramType()
			{
				return programType;
			}
	}
