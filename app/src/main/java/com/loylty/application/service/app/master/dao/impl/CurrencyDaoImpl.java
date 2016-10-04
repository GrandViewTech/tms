package com.loylty.application.service.app.master.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.loylty.application.entity.bo.master.Currency;
import com.loylty.application.service.app.master.dao.CurrencyDao;

@Repository("currencyDao")
public class CurrencyDaoImpl implements CurrencyDao
	{
		@PersistenceContext
		private EntityManager entityManager;
		
		@Override
		public Currency create(Currency currency)
			{
				entityManager.persist(currency);
				return currency;
			}
			
		@Override
		public Currency update(Currency currency)
			{
				return entityManager.merge(currency);
			}
			
		@Override
		public Currency findCurrencyByCurrencyCode(String currencyCode)
			{
				String hqlQuery = "SELECT currency FROM Currency currency WHERE LOWER(currency.currencyCode) =:currencyCode";
				TypedQuery<Currency> typedQuery = entityManager.createQuery(hqlQuery, Currency.class);
				typedQuery.setParameter("currencyCode", currencyCode.trim().toLowerCase());
				List<Currency> currencies = typedQuery.getResultList();
				if ( currencies.size() > 0 ) { return currencies.get(0); }
				return null;
			}
			
		@Override
		public List<Currency> findAll()
			{
				String hqlQuery = "SELECT currency FROM Currency currency ";
				TypedQuery<Currency> typedQuery = entityManager.createQuery(hqlQuery, Currency.class);
				List<Currency> currencies = typedQuery.getResultList();
				return currencies;
			}
			
	}
