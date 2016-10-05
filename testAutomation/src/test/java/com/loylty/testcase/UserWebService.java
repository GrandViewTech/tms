package com.loylty.testcase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.loylty.api.ApiList;
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
	}
