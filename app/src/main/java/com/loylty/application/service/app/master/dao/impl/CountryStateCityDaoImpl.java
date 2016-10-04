package com.loylty.application.service.app.master.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.loylty.application.entity.bo.master.City;
import com.loylty.application.entity.bo.master.Country;
import com.loylty.application.entity.bo.master.State;
import com.loylty.application.service.app.master.dao.CountryStateCityDao;

@Repository("countryStateCityDao")
public class CountryStateCityDaoImpl implements CountryStateCityDao
	{
		@PersistenceContext
		private EntityManager entityManager;
		
		@Override
		public City createOrUpdateCity(City city)
			{
				if ( city.getId() == null || city.getId().trim().length() == 0 )
					{
						entityManager.persist(city);
					}
				else
					{
						city = entityManager.merge(city);
					}
				return city;
			}
			
		@Override
		public State createOrUpdateState(State state)
			{
				if ( state.getId() == null || state.getId().trim().length() == 0 )
					{
						entityManager.persist(state);
					}
				else
					{
						state = entityManager.merge(state);
					}
				return state;
			}
			
		@Override
		public Country createOrUpdateCountry(Country country)
			{
				if ( country.getId() == null || country.getId().trim().length() == 0 )
					{
						entityManager.persist(country);
					}
				else
					{
						country = entityManager.merge(country);
					}
				return country;
			}
			
		@Override
		public List<City> createOrUpdateCities(List<City> cities, Integer batchSize)
			{
				List<City> updated = new ArrayList<City>();
				if ( cities == null || cities.size() == 0 ) { return updated; }
				int counter = 0;
				batchSize = (batchSize == null) ? 50 : batchSize;
				for (City city : cities)
					{
						counter = counter + 1;
						updated.add(createOrUpdateCity(city));
						if ( counter == batchSize )
							{
								entityManager.flush();
							}
					}
				if ( counter > 0 )
					{
						entityManager.flush();
					}
				return updated;
			}
			
		@Override
		public List<State> createOrUpdateStates(List<State> states, Integer batchSize)
			{
				List<State> updated = new ArrayList<State>();
				if ( states == null || states.size() == 0 ) { return updated; }
				int counter = 0;
				batchSize = (batchSize == null) ? 50 : batchSize;
				for (State state : states)
					{
						counter = counter + 1;
						updated.add(createOrUpdateState(state));
						if ( counter == batchSize )
							{
								entityManager.flush();
							}
					}
				if ( counter > 0 )
					{
						entityManager.flush();
					}
				return updated;
			}
			
		@Override
		public List<Country> createOrUpdateCountries(List<Country> countries, Integer batchSize)
			{
				List<Country> updated = new ArrayList<Country>();
				if ( countries == null || countries.size() == 0 ) { return updated; }
				int counter = 0;
				batchSize = (batchSize == null) ? 50 : batchSize;
				for (Country country : countries)
					{
						counter = counter + 1;
						updated.add(createOrUpdateCountry(country));
						if ( counter == batchSize )
							{
								entityManager.flush();
							}
					}
				if ( counter > 0 )
					{
						entityManager.flush();
					}
				return updated;
			}
			
		@Override
		public Country findCountryByCountryName(String countryName)
			{
				String hqlQuery = "SELECT country FROM Country country WHERE LOWER(country.name)=:countryName";
				TypedQuery<Country> typedQuery = entityManager.createQuery(hqlQuery, Country.class);
				typedQuery.setParameter("countryName", countryName.trim().toLowerCase());
				List<Country> countries = typedQuery.getResultList();
				return (countries.size() > 0) ? countries.get(0) : null;
			}
			
		@Override
		public List<String> findStateByCountryName(String countryName)
			{
				String hqlQuery = "SELECT new java.lang.String(state.name) FROM State state JOIN state.country as country WHERE LOWER(country.name)=:countryName";
				TypedQuery<String> typedQuery = entityManager.createQuery(hqlQuery, String.class);
				typedQuery.setParameter("countryName", countryName.trim().toLowerCase());
				return typedQuery.getResultList();
				
			}
			
		@Override
		public List<String> findCitiesByCountryNameAndStateName(String countryName, String stateName)
			{
				String hqlQuery = "SELECT new java.lang.String(city.name) From City city JOIN city.state as state JOIN state.country as country WHERE LOWER(country.name)=:countryName AND LOWER(state.name)=:stateName";
				TypedQuery<String> typedQuery = entityManager.createQuery(hqlQuery, String.class);
				typedQuery.setParameter("countryName", countryName.trim().toLowerCase());
				typedQuery.setParameter("stateName", stateName.trim().toLowerCase());
				return typedQuery.getResultList();
			}
			
		@Override
		public List<String> findAllCountries()
			{
				String hqlQuery = "SELECT new java.lang.String(country.name) From Country country";
				TypedQuery<String> typedQuery = entityManager.createQuery(hqlQuery, String.class);
				return typedQuery.getResultList();
			}
			
	}
