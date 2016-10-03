package com.loylty.application.entity.bo.constants;

public enum RuleType
	{
	CAPPING_RULE("CAPPING_RULE"), PRIVILEDGE_RULE("PRIVILEDGE_RULE");
		
		private String ruleType;
		
		RuleType(String ruleType)
			{
				this.ruleType = ruleType;
			}
			
		public String getRuleType()
			{
				return ruleType;
			}
	}
