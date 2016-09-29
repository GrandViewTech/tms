package com.loylty.application.service.master.business.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.loylty.application.entity.bo.master.City;
import com.loylty.application.entity.bo.master.Country;
import com.loylty.application.entity.bo.master.State;
import com.loylty.application.service.master.business.CountryStateCityService;
import com.loylty.application.service.master.dao.CountryStateCityDao;

@Service("countryStateCityService")
public class CountryStateCityServiceImpl implements CountryStateCityService
	{
		@Autowired
		private CountryStateCityDao countryStateCityDao;
		
		@Override
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
		public List<Country> createOrUpdate(List<Country> countries, Integer batchSize)
			{
				List<Country> updatedCountries = new ArrayList<Country>();
				for (Country country : countries)
					{
						String countryName = country.getName();
						Country existingCountry = countryStateCityDao.findCountryByCountryName(countryName);
						if ( existingCountry == null )
							{
								List<State> states = new ArrayList<State>();
								for (State state : country.getStates())
									{
										List<City> cities = new ArrayList<City>();
										for (City city : state.getCities())
											{
												city.setState(state);
												cities.add(city);
											}
										state.setCities(cities);
										state.setCountry(country);
										states.add(state);
									}
								country.setStates(states);
								updatedCountries.add(country);
							}
						else
							{
								List<State> existingStates = existingCountry.getStates();
								Map<String, State> existingStateName_existingState = new LinkedHashMap<String, State>();
								for (State existingState : existingStates)
									{
										String existingStateName = existingState.getName();
										existingStateName = existingStateName.trim().toLowerCase();
										existingStateName_existingState.put(existingStateName, existingState);
									}
								List<State> states = new ArrayList<State>();
								for (State state : country.getStates())
									{
										String stateName = state.getName();
										stateName = stateName.trim().toLowerCase();
										State existingState = existingStateName_existingState.get(stateName);
										if ( existingState == null )
											{
												List<City> tempCities = new ArrayList<>();
												for (City city : state.getCities())
													{
														city.setId(null);
														city.setState(state);
														tempCities.add(city);
													}
												state.setCities(tempCities);
												state.setCountry(existingCountry);
												state = countryStateCityDao.createOrUpdateState(state);
												states.add(state);
											}
										else
											{
												List<City> cities = new ArrayList<City>();
												List<City> existingCities = existingState.getCities();
												Map<String, City> existingCityName_existingCity = new LinkedHashMap<String, City>();
												for (City existingCity : existingCities)
													{
														String existingCityName = existingCity.getName();
														existingCityName = existingCityName.trim().toLowerCase();
														existingCityName_existingCity.put(existingCityName, existingCity);
													}
												for (City city : state.getCities())
													{
														String cityName = city.getName();
														cityName = cityName.trim().toLowerCase();
														City exisitingCity = existingCityName_existingCity.get(cityName);
														if ( exisitingCity == null )
															{
																city.setId(null);
																city.setState(existingState);
																cities.add(city);
															}
														else
															{
																cities.add(exisitingCity);
															}
														cities = countryStateCityDao.createOrUpdateCities(cities, batchSize);
														existingState.setCities(cities);
														state = countryStateCityDao.createOrUpdateState(state);
														states.add(existingState);
													}
											}
											
									}
								updatedCountries.add(existingCountry);
							}
							
					}
				return countryStateCityDao.createOrUpdateCountries(updatedCountries, batchSize);
			}
			
		@Override
		public List<String> findStatesByCountryName(String countryName)
			{
				return countryStateCityDao.findStateByCountryName(countryName);
			}
			
		@Override
		public List<String> findCitiesByCountryNameAndStateName(String countryName, String stateName)
			{
				return countryStateCityDao.findCitiesByCountryNameAndStateName(countryName, stateName);
			}
			
		@Override
		public List<String> findAllCountries()
			{
				return countryStateCityDao.findAllCountries();
			}
			
	}
