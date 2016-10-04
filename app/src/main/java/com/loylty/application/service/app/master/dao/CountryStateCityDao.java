package com.loylty.application.service.app.master.dao;

import java.util.List;

import com.loylty.application.entity.bo.master.City;
import com.loylty.application.entity.bo.master.Country;
import com.loylty.application.entity.bo.master.State;

public interface CountryStateCityDao
	{
		public City createOrUpdateCity(City city);
		
		public State createOrUpdateState(State state);
		
		public Country createOrUpdateCountry(Country country);
		
		public List<City> createOrUpdateCities(List<City> cities, Integer batchSize);
		
		public List<State> createOrUpdateStates(List<State> states, Integer batchSize);
		
		public List<Country> createOrUpdateCountries(List<Country> countries, Integer batchSize);
		
		public Country findCountryByCountryName(String countryName);
		
		public List<String> findStateByCountryName(String countryName);
		
		public List<String> findCitiesByCountryNameAndStateName(String countryName, String stateName);
		
		public List<String> findAllCountries();
		
	}
