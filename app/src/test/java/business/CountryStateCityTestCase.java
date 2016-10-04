package business;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.loylty.application.entity.bo.master.City;
import com.loylty.application.entity.bo.master.Country;
import com.loylty.application.entity.bo.master.State;
import com.loylty.application.service.app.master.business.CountryStateCityService;

import business.util.LoggerUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
	{ "classpath:spring/application-config.xml" })
public class CountryStateCityTestCase
	{
		
		final private static String		DATA		= "CountryStateCity" + File.separator + "data";
		final private static String		TESTCASE	= "CountryStateCity" + File.separator + "testCase";
		@Autowired
		private CountryStateCityService	countryStateCityService;
		
		@Test
		public void createCountryStateCityRelationShip() throws IOException
			{
				String fileName = "input" + File.separator + "GeoLite2-City-Locations-en.xlsx";
				Map<String, Country> countrycode_country = new LinkedHashMap<String, Country>();
				InputStream inputStream = new FileInputStream(new File(fileName));
				try (XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream))
					{
						XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
						Iterator<Row> rowIterator = xssfSheet.iterator();
						while (rowIterator.hasNext())
							{
								Row row = rowIterator.next();
								if ( row.getRowNum() > 0 )
									{
										String countryCode = getCellData(row.getCell(4));
										String countryName = getCellData(row.getCell(5));
										String stateCode = getCellData(row.getCell(6));
										String stateName = getCellData(row.getCell(7));
										String cityCode = getCellData(row.getCell(8));
										String cityName = getCellData(row.getCell(10));
										if ( countryCode != null && countryCode.trim().length() > 0 )
											{
												System.out.println("countryName : " + countryName + " | countryCode : " + countryCode + " | stateName : " + stateName + " | stateCode : " + stateCode + " | cityName : " + cityName + " | cityCode : " + cityCode);
												Country country = countrycode_country.get(countryName.trim().toLowerCase());
												if ( country == null )
													{
														country = new Country();
														country.setCode(countryCode);
														country.setName(countryName);
													}
												List<State> states = country.getStates();
												if ( stateName.trim().length() > 0 )
													{
														boolean stateFound = false;
														State state = null;
														for (State existingState : states)
															{
																String existingStateName = existingState.getName();
																if ( stateName.trim().equalsIgnoreCase(existingStateName.trim()) )
																	{
																		state = existingState;
																		stateFound = true;
																		break;
																	}
															}
														if ( stateFound == false )
															{
																state = new State();
																state.setCode(stateCode);
																state.setName(stateName);
																state.setCountry(country);
																states.add(state);
															}
														if ( cityName.trim().length() > 0 )
															{
																City city = null;
																List<City> cities = state.getCities();
																boolean cityFound = false;
																for (City existingCity : cities)
																	{
																		String existingCityName = existingCity.getName();
																		if ( existingCityName.trim().equalsIgnoreCase(cityName.trim()) )
																			{
																				city = existingCity;
																				cityFound = true;
																				break;
																			}
																	}
																if ( cityFound == false )
																	{
																		city = new City();
																		city.setCode(cityCode);
																		city.setName(cityName);
																		city.setState(state);
																		cities.add(city);
																		state.setCities(cities);
																	}
																	
															}
														country.setStates(states);
														countrycode_country.put(countryName.trim().toLowerCase(), country);
													}
											}
									}
							}
							
					}
				catch (Exception exception)
					{
						exception.printStackTrace();
					}
				System.out.println("Reading excel completed");
				List<Country> countries = new ArrayList<Country>();
				for (Country country : countrycode_country.values())
					{
						countries.add(country);
						LoggerUtil.log(DATA, country.getCode(), country);
					}
				System.out.println("Writing Json completed");
				countryStateCityService.createOrUpdate(countries, 50);
				System.out.println("End");
			}
			
		@SuppressWarnings("deprecation")
		private String getCellData(Cell cell)
			{
				try
					{
						
						if ( cell != null )
							{
								switch (cell.getCellType())
									{
										case Cell.CELL_TYPE_NUMERIC:
											{
												return "" + cell.getNumericCellValue();
											}
										case Cell.CELL_TYPE_STRING:
											{
												return cell.getStringCellValue();
											}
											
									}
									
							}
							
					}
				catch (Exception exception)
					{
						System.out.println(cell.getColumnIndex() + " " + exception.getLocalizedMessage());
					}
				return "";
			}
			
		@Test
		public void findStatesByCountryName()
			{
				String countryName = "India";
				List<String> states = countryStateCityService.findStatesByCountryName(countryName);
				if ( states != null )
					{
						LoggerUtil.log(TESTCASE, "findStatesByCountryName", states);
					}
			}
			
		@Test
		public void findCitiesByCountryNameAndStateName()
			{
				String countryName = "India";
				String stateName = "Maharashtra";
				List<String> cities = countryStateCityService.findCitiesByCountryNameAndStateName(countryName, stateName);
				if ( cities != null )
					{
						LoggerUtil.log(TESTCASE, "findCitiesByCountryNameAndStateName", cities);
					}
			}
			
		@Test
		public void findAllCountries()
			{
				List<String> countries = countryStateCityService.findAllCountries();
				if ( countries != null )
					{
						LoggerUtil.log(DATA, "findAllCountries", countries);
					}
			}
	}
