package com.loylty.testcase;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.loylty.api.ApiList;
import com.loylty.application.entity.bo.constants.ProgramType;
import com.loylty.application.entity.bo.constants.TenantType;
import com.loylty.application.entity.bo.master.Program;
import com.loylty.application.entity.bo.master.User;

public class UserWebService
	{
		private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserWebService.class);
		
		@Test
		public void findUsersByUserIdsTestCase()
			{
				try
					{
						String url = ApiList.findUsersByUserIds();
						RestTemplate restTemplate = new RestTemplate();
						ParameterizedTypeReference<List<User>> parameterizedTypeReference = new ParameterizedTypeReference<List<User>>()
							{
							};
						List<String> userIds = new ArrayList<String>();
						userIds.add("4028e6fa578ed4a501578ed4b4be0000");
						userIds.add("4028e6fa579450ed015794513ac00000");
						userIds.add("4028e6fa579450ed015794513ac0000011");
						HttpEntity<List<String>> httpEntity = new HttpEntity<List<String>>(userIds);
						ResponseEntity<List<User>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, parameterizedTypeReference);
						List<User> users = responseEntity.getBody();
						Map<String, User> userId_user = new LinkedHashMap<String, User>();
						for (User user : users)
							{
								userId_user.put(user.getId(), user);
								
							}
						for (String userId : userIds)
							{
								org.junit.Assert.assertTrue("UserId : " + userId + " does not match", userId_user.containsKey(userId.trim()));
							}
					}
				catch (Exception exception)
					{
						org.junit.Assert.assertTrue(exception.getLocalizedMessage(), false);
						logger.error(exception.getLocalizedMessage(), exception);
					}
			}
			
		@Test
		public void getIndustriesTestCase()
			{
				try
					{
						String url = ApiList.getIndustries();
						RestTemplate restTemplate = new RestTemplate();
						ParameterizedTypeReference<List<String>> parameterizedTypeReference = new ParameterizedTypeReference<List<String>>()
							{
							};
						UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
						HttpHeaders headers = new HttpHeaders();
						headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
						HttpEntity<?> entity = new HttpEntity<>(headers);
						ResponseEntity<List<String>> responseEntity = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, parameterizedTypeReference);
						List<String> industries = responseEntity.getBody();
						Resource resource = new ClassPathResource("data" + File.separator + "Industries.txt");
						List<String> data = FileUtils.readLines(resource.getFile(), "utf-8");
						for (String industry : industries)
							{
								org.junit.Assert.assertTrue("Industry : " + industry + " does not match", data.contains(industry));
							}
					}
				catch (Exception exception)
					{
						org.junit.Assert.assertTrue(exception.getLocalizedMessage(), false);
						logger.error(exception.getLocalizedMessage(), exception);
					}
			}
			
		@Test
		public void getFeaturesTestCase()
			{
				try
					{
						String url = ApiList.getFeatures();
						RestTemplate restTemplate = new RestTemplate();
						ParameterizedTypeReference<List<String>> parameterizedTypeReference = new ParameterizedTypeReference<List<String>>()
							{
							};
						UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
						HttpHeaders headers = new HttpHeaders();
						headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
						HttpEntity<?> entity = new HttpEntity<>(headers);
						ResponseEntity<List<String>> responseEntity = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, parameterizedTypeReference);
						List<String> features = responseEntity.getBody();
						Resource resource = new ClassPathResource("data" + File.separator + "Features.txt");
						List<String> data = FileUtils.readLines(resource.getFile(), "utf-8");
						for (String feature : features)
							{
								org.junit.Assert.assertTrue("Feature : " + feature + " does not match", data.contains(feature));
							}
					}
				catch (Exception exception)
					{
						org.junit.Assert.assertTrue(exception.getLocalizedMessage(), false);
						logger.error(exception.getLocalizedMessage(), exception);
					}
			}
			
		@Test
		public void getTenantTypesTestCase()
			{
				try
					{
						String url = ApiList.getTenantTypes();
						RestTemplate restTemplate = new RestTemplate();
						ParameterizedTypeReference<List<String>> parameterizedTypeReference = new ParameterizedTypeReference<List<String>>()
							{
							};
						UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
						HttpHeaders headers = new HttpHeaders();
						headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
						HttpEntity<?> entity = new HttpEntity<>(headers);
						ResponseEntity<List<String>> responseEntity = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, parameterizedTypeReference);
						List<String> tenants = responseEntity.getBody();
						List<String> data = new ArrayList<String>();
						for (TenantType tenantType : TenantType.values())
							{
								data.add(tenantType.getTenantType());
							}
						for (String tenant : tenants)
							{
								org.junit.Assert.assertTrue("Tenant : " + tenant + " does not match", data.contains(tenant));
							}
					}
				catch (Exception exception)
					{
						org.junit.Assert.assertTrue(exception.getLocalizedMessage(), false);
						logger.error(exception.getLocalizedMessage(), exception);
					}
			}
			
		@Test
		public void getProgramTypesTestCase()
			{
				try
					{
						String url = ApiList.getProgramTypes();
						RestTemplate restTemplate = new RestTemplate();
						ParameterizedTypeReference<List<String>> parameterizedTypeReference = new ParameterizedTypeReference<List<String>>()
							{
							};
						UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
						HttpHeaders headers = new HttpHeaders();
						headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
						HttpEntity<?> entity = new HttpEntity<>(headers);
						ResponseEntity<List<String>> responseEntity = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, parameterizedTypeReference);
						List<String> programTypes = responseEntity.getBody();
						List<String> data = new ArrayList<String>();
						for (ProgramType type : ProgramType.values())
							{
								data.add(type.getProgramType());
							}
						for (String programType : programTypes)
							{
								org.junit.Assert.assertTrue("ProgramTyp : " + programType + " does not match", data.contains(programType));
							}
					}
				catch (Exception exception)
					{
						org.junit.Assert.assertTrue(exception.getLocalizedMessage(), false);
						logger.error(exception.getLocalizedMessage(), exception);
					}
			}
			
		@Test
		public void getProgramByProgramId()
			{
				try
					{
						String programId = "4028e6fa578eddc401578eddcf410000";
						String url = ApiList.getProgramByProgramId(programId);
						RestTemplate restTemplate = new RestTemplate();
						
						UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
						HttpHeaders headers = new HttpHeaders();
						headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
						HttpEntity<?> entity = new HttpEntity<>(headers);
						ResponseEntity<Program> responseEntity = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, Program.class);
						Program program = responseEntity.getBody();
						if ( program != null )
							{
								Assert.assertTrue("Program with programId : " + programId + " does not matches", program.getId().equalsIgnoreCase(programId));
							}
						else
							{
								Assert.assertTrue("Program with programId : " + programId + " does not matches", false);
							}
					}
				catch (Exception exception)
					{
						org.junit.Assert.assertTrue(exception.getLocalizedMessage(), false);
						logger.error(exception.getLocalizedMessage(), exception);
					}
					
			}
	}
