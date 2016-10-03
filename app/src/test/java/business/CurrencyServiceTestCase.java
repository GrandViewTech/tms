package business;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loylty.application.entity.bo.master.Currency;
import com.loylty.application.service.business.master.business.CurrencyService;

import business.util.LoggerUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
	{ "classpath:spring/application-config.xml" })
public class CurrencyServiceTestCase
	{
		final private static String	FILE_NAME	= "Currency";
		final private static String	DATA		= FILE_NAME + File.separator + "data";
		final private static String	TESTCASE	= FILE_NAME + File.separator + "testCase";
		@Autowired
		private CurrencyService		currencyService;
		
		@Test
		public void readCurrencyFromFileAndUpload() throws JsonParseException, JsonMappingException, IOException
			{
				ObjectMapper objectMapper = new ObjectMapper();
				File file = new File("input" + File.separator + "currency.json");
				InputStream inputStream = new FileInputStream(file);
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String data;
				StringWriter stringWriter = new StringWriter();
				while ((data = bufferedReader.readLine()) != null)
					{
						stringWriter.append(data);
					}
					
				TypeReference<HashMap<String, String>> typeReference = new com.fasterxml.jackson.core.type.TypeReference<HashMap<String, String>>()
					{
					};
				Map<String, String> currencies = objectMapper.readValue((new ByteArrayInputStream(stringWriter.toString().getBytes("UTF-8"))), typeReference);
				for (Map.Entry<String, String> cr : currencies.entrySet())
					{
						Currency currency = new Currency();
						currency.setName(cr.getValue());
						currency.setCurrencyCode(cr.getKey());
						System.out.println(cr.getKey() + " | " + cr.getValue());
						LoggerUtil.log("currency" + File.separator + "readCurrencyFromFileAndUpload" + File.separator + "before", currency.getCurrencyCode(), currency);
						currencyService.createOrUpdate(currency);
					}
					
				for (Currency currency : currencyService.findAll())
					{
						LoggerUtil.log("currency" + File.separator + "readCurrencyFromFileAndUpload" + File.separator + "after", currency.getCurrencyCode(), currency);
					}
				bufferedReader.close();
			}
			
		@Test
		public void addConversionRatioFromFile() throws IOException
			{
				ObjectMapper objectMapper = new ObjectMapper();
				File file = new File("input" + File.separator + "conversion.json");
				InputStream inputStream = new FileInputStream(file);
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String data;
				StringWriter stringWriter = new StringWriter();
				while ((data = bufferedReader.readLine()) != null)
					{
						stringWriter.append(data);
					}
					
				TypeReference<HashMap<String, Double>> typeReference = new com.fasterxml.jackson.core.type.TypeReference<HashMap<String, Double>>()
					{
					};
				Map<String, Double> currencies = objectMapper.readValue((new ByteArrayInputStream(stringWriter.toString().getBytes("UTF-8"))), typeReference);
				Currency base = currencyService.findCurrencyByCurrencyCode("USD");
				for (Map.Entry<String, Double> cr : currencies.entrySet())
					{
						System.out.println(cr.getKey() + " | " + cr.getValue());
						Currency currency = currencyService.findCurrencyByCurrencyCode(cr.getKey());
						if ( currency != null )
							{
								currency.setConversionRatio(cr.getValue());
								currency.setBase(base.getCurrencyCode());
								currencyService.createOrUpdate(currency);
								LoggerUtil.log("currency" + File.separator + "addConversionRatioFromFile" + File.separator + "before", currency.getCurrencyCode(), currency);
								currency = currencyService.findCurrencyByCurrencyCode(cr.getKey());
								LoggerUtil.log("currency" + File.separator + "addConversionRatioFromFile" + File.separator + "after", currency.getCurrencyCode(), currency);
								
							}
						else
							{
								
								currency = new Currency();
								currency.setCurrencyCode(cr.getKey());
								currency.setConversionRatio(cr.getValue());
								currency.setBase(base.getCurrencyCode());
								LoggerUtil.log("currency" + File.separator + "addConversionRatioFromFile" + File.separator + "before", currency.getCurrencyCode(), currency);
								currencyService.createOrUpdate(currency);
								currency = currencyService.findCurrencyByCurrencyCode(cr.getKey());
								LoggerUtil.log("currency" + File.separator + "addConversionRatioFromFile" + File.separator + "after", currency.getCurrencyCode(), currency);
								
							}
					}
				bufferedReader.close();
			}
			
		@Test
		public void testMultilingualSymbol()
			{
				String symbol = " الاغاني الهندية غايه فى";
				for (Currency currency : currencyService.findAll())
					{
						currency.setSymbol(symbol);
						currencyService.createOrUpdate(currency);
					}
			}
			
		@Test
		public void findAllCurrency()
			{
				for (Currency currency : currencyService.findAll())
					{
						LoggerUtil.log(DATA, currency.getCurrencyCode(), currency);
					}
			}
	}
