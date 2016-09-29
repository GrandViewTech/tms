package com.loylty.application.service.master.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.loylty.application.entity.bo.master.User;
import com.loylty.application.service.master.dao.UserDao;

@Repository("userDao")
public class UserDaoImpl implements UserDao
	{
		@PersistenceContext
		private EntityManager entityManager;
		
		@Override
		public User create(User user)
			{
				entityManager.persist(user);
				return user;
			}
			
		@Override
		public User update(User user)
			{
				user = entityManager.merge(user);
				return user;
			}
			
		@Override
		public User findUserByEmailAddress(String emailAddress)
			{
				String hqlQuery = "SELECT user FROM User user where LOWER(user.emailAddress)=:emailAddress";
				TypedQuery<User> typedQuery = entityManager.createQuery(hqlQuery, User.class);
				typedQuery.setParameter("emailAddress", emailAddress.trim().toLowerCase());
				List<User> users = typedQuery.getResultList();
				User user = null;
				if ( users.size() > 0 )
					{
						user = users.get(0);
					}
				return user;
			}
	}
