package com.loylty.application.service.app.master.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.loylty.application.entity.bo.master.Activity;
import com.loylty.application.service.app.master.dao.UserActivityDao;

@Repository("userActivityDao")
public class UserActivityDaoImpl implements UserActivityDao
	{
		@PersistenceContext
		private EntityManager entityManager;
		
		@Override
		public Activity findActivityByActivityName(String activityName)
			{
				String hqlQuery = "SELECT activity FROM Activity activity WHERE LOWER(activity.name)=:activityName";
				TypedQuery<Activity> typedQuery = entityManager.createQuery(hqlQuery, Activity.class);
				typedQuery.setParameter("activityName", activityName.trim().toLowerCase());
				List<Activity> activities = typedQuery.getResultList();
				Activity activity = null;
				if ( activities.size() > 0 )
					{
						activity = activities.get(0);
					}
				return activity;
			}
			
		@Override
		public Activity create(Activity activity)
			{
				entityManager.persist(activity);
				return activity;
			}
			
		@Override
		public Activity update(Activity activity)
			{
				return entityManager.merge(activity);
			}
			
	}
