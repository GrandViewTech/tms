package com.loylty.application.entity.bo.tenant;

import javax.persistence.Entity;

import com.loylty.application.entity.bo.base.BaseMetadata;
import com.loylty.application.entity.bo.constants.RuleType;
import com.loylty.application.entity.bo.constants.Status;
import com.mysql.cj.jdbc.Blob;

@Entity
public class Rule extends BaseMetadata
	{
		private static final long	serialVersionUID	= -6932424634101212950L;
		
		private String				ruleName;
		
		private RuleType			ruleType;
		
		private Blob				blob;
		
		private Status				status;
		
		public String getRuleName()
			{
				return ruleName;
			}
			
		public void setRuleName(String ruleName)
			{
				this.ruleName = ruleName;
			}
			
		public RuleType getRuleType()
			{
				return ruleType;
			}
			
		public void setRuleType(RuleType ruleType)
			{
				this.ruleType = ruleType;
			}
			
		public Status getStatus()
			{
				return status;
			}
			
		public void setStatus(Status status)
			{
				this.status = status;
			}
			
		public Blob getBlob()
			{
				return blob;
			}
			
		public void setBlob(Blob blob)
			{
				this.blob = blob;
			}
			
		@Override
		public String toString()
			{
				return "Rule [ruleName=" + ruleName + ", ruleType=" + ruleType + ", status=" + status + "]";
			}
			
	}
