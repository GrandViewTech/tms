package com.loylty.application.service.app.master.business;

import java.util.List;

import com.loylty.application.entity.bo.master.Country;

public interface CountryStateCityService
	{
		public List<String> findAllCountries();
		
		public List<String> findStatesByCountryName(String countryName);
		
		public List<Country> createOrUpdate(List<Country> countries, Integer batchSize);
		
		public List<String> findCitiesByCountryNameAndStateName(String countryName, String stateName);
	}
