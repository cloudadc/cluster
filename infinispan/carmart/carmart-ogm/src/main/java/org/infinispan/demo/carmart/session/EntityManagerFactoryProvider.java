package org.infinispan.demo.carmart.session;

import javax.persistence.EntityManagerFactory;

/**
 * 
 * Subclasses should create an instance of a JPA EntityManagerFactory
 * 
 */
public interface EntityManagerFactoryProvider {
    
    public EntityManagerFactory getEntityManagerFactory();

}
