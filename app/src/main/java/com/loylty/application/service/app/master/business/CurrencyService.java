package com.loylty.application.service.app.master.business;

import java.util.List;

import com.loylty.application.entity.bo.master.Currency;

public interface CurrencyService
	{
		public Currency createOrUpdate(Currency currency);
		
		public Currency findCurrencyByCurrencyCode(String currencyCode);
		
		public List<Currency> findAll();
	}
