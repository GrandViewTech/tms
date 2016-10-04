package com.loylty.application.service.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class LazyInitializationInterceptor
	{
		private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(LazyInitializationInterceptor.class);
		
		public static Object initializeAndReturnLazyObject(Object entity, Method method, String tenantId)
			{
				Object object = null;
				EntityManagerFactory entityManagerFactory = null;
				try
					{
						if ( SpringContext.isTenantIdConfigured(tenantId) )
							{
								Object bean = SpringContext.getBean(tenantId, "entityManagerFactory");
								if ( bean != null )
									{
										entityManagerFactory = (EntityManagerFactory) bean;
										EntityManager entityManager = entityManagerFactory.createEntityManager();
										TransactionSynchronizationManager.bindResource(entityManagerFactory, getEntityManagerHolder(entityManager));
										entity = entityManager.merge(entity);
										try
											{
												object = method.invoke(entity, new Object[0]);
											}
										catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception)
											{
												logger.error(exception.getLocalizedMessage(), exception);
											}
									}
							}
					}
				catch (Exception exception)
					{
						logger.error(exception.getLocalizedMessage(), exception);
					}
				finally
					{
						closeEntityManagerFactory(entityManagerFactory);
					}
				return object;
			}
			
		private static EntityManagerHolder getEntityManagerHolder(EntityManager entityManager)
			{
				return new EntityManagerHolder(entityManager);
			}
			
		private static void closeEntityManagerFactory(EntityManagerFactory entityManagerFactory)
			{
				if ( entityManagerFactory != null )
					{
						EntityManagerHolder entityManagerHolder = (EntityManagerHolder) TransactionSynchronizationManager.unbindResource(entityManagerFactory);
						EntityManager entityManager = entityManagerHolder.getEntityManager();
						EntityManagerFactoryUtils.closeEntityManager(entityManager);
					}
			}
	}
