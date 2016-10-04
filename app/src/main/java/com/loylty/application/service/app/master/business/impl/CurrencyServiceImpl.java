package com.loylty.application.service.app.master.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.loylty.application.entity.bo.master.Currency;
import com.loylty.application.service.app.master.business.CurrencyService;
import com.loylty.application.service.app.master.dao.CurrencyDao;

@Service("currencyService")
public class CurrencyServiceImpl implements CurrencyService
	{
		@Autowired
		private CurrencyDao currencyDao;
		
		@Override
		@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
		public Currency createOrUpdate(Currency currency)
			{
				String currencyCode = currency.getCurrencyCode();
				Currency existingCurrency = findCurrencyByCurrencyCode(currencyCode);
				if ( existingCurrency != null )
					{
						existingCurrency.setConversionRatio(currency.getConversionRatio());
						existingCurrency.setName(currency.getName());
						existingCurrency.setSymbol(currency.getSymbol());
						existingCurrency.setBase(currency.getBase());
						return currencyDao.update(existingCurrency);
					}
				else
					{
						currency = currencyDao.create(currency);
						return currency;
					}
			}
			
		@Override
		public Currency findCurrencyByCurrencyCode(String currencyCode)
			{
				return currencyDao.findCurrencyByCurrencyCode(currencyCode);
			}
			
		@Override
		public List<Currency> findAll()
			{
				return currencyDao.findAll();
			}
			
	}
