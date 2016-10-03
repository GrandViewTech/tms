package com.loylty.application.service.business.master.dao;

import java.util.List;

import com.loylty.application.entity.bo.master.Currency;

public interface CurrencyDao
	{
		public Currency create(Currency currency);
		
		public Currency update(Currency currency);
		
		public Currency findCurrencyByCurrencyCode(String currencyCode);
		
		public List<Currency> findAll();
	}
