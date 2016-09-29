package com.loylty.application.entity.bo.master;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Lob;

import com.loylty.application.entity.bo.base.BaseMetadata;

@Entity
public class Currency extends BaseMetadata implements Serializable
	{
		private static final long	serialVersionUID	= 7255629405529920504L;
		@Lob
		private String				name;
		private String				currencyCode;
		@Lob
		private String				symbol;
		private Double				conversionRatio		= 0.0;
		private String				base;
		
		public String getName()
			{
				return name;
			}
			
		public void setName(String name)
			{
				this.name = name;
			}
			
		public String getSymbol()
			{
				return symbol;
			}
			
		public void setSymbol(String symbol)
			{
				this.symbol = symbol;
			}
			
		public Double getConversionRatio()
			{
				return conversionRatio;
			}
			
		public void setConversionRatio(Double conversionRatio)
			{
				this.conversionRatio = conversionRatio;
			}
			
		public String getCurrencyCode()
			{
				return currencyCode;
			}
			
		public void setCurrencyCode(String currencyCode)
			{
				this.currencyCode = currencyCode;
			}
			
		public String getBase()
			{
				return base;
			}
			
		public void setBase(String base)
			{
				this.base = base;
			}
			
	}
