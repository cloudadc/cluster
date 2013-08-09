package org.infinispan.demo.carmart.session;

import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;


import org.infinispan.demo.carmart.session.EntityManagerFactoryProvider;


/**
 * {@link EntityManagerFactoryProvider}'s implementation creating a
 * EntityManagerFactory 
 * 
 * 
 */
@ApplicationScoped
public class JBossASEntityManagerFactoryProvider implements EntityManagerFactoryProvider {

	private Logger log = Logger.getLogger(this.getClass().getName());

	@Inject
	private EntityManagerFactory factory;

	@PreDestroy
	public void cleanUp() {
		if (null != factory) {
			factory.close();
			factory = null;
			log.info("destroy the EntityManagerFactory");
		}
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return factory;
	}
}
