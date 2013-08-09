package org.infinispan.demo.carmart.jsf;

import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;

import org.infinispan.demo.carmart.model.Car;
import org.infinispan.demo.carmart.model.Car.CarType;
import org.infinispan.demo.carmart.model.Car.Country;
import org.infinispan.demo.carmart.session.EntityManagerFactoryProvider;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

/**
 * Populates a cache with initial data. We need to obtain BeanManager from JNDI and create an instance of CacheContainerProvider
 * manually since injection into Listeners is not supported by CDI specification.
 * 
 * 
 */
public class PopulateCache implements SystemEventListener {

    private Logger log = Logger.getLogger(this.getClass().getName());

    private EntityManagerFactoryProvider provider;

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        provider = getContextualInstance(getBeanManagerFromJNDI(), EntityManagerFactoryProvider.class);
        startup();
    }

    public void startup() {

		try {
			EntityManager em = provider.getEntityManagerFactory().createEntityManager();
            Car c = new Car("Ford Focus", 1.6, CarType.COMBI, "white", "FML 23-25", Country.CHN);
            em.persist(c);
            c = new Car("BMW X3", 2.0, CarType.SEDAN, "gray", "1P3 2632", Country.CHN);
            em.persist(c);
            c = new Car("Ford Mondeo", 2.2, CarType.COMBI, "blue", "1B2 1111", Country.USA);
            em.persist(c);
            c = new Car("Mazda MX-5", 1.8, CarType.CABRIO, "red", "6T4 2526", Country.USA);
            em.persist(c);
            c = new Car("VW Golf", 1.6, CarType.HATCHBACK, "yellow", "2B2 4946", Country.GERMANY);
            em.persist(c);
            em.close();
            log.info("Successfully imported data!");
        } catch (Exception e) {
            log.warning("An exception occured while populating the database " + e.getMessage());
            e.printStackTrace();
        }
		
    }

    private BeanManager getBeanManagerFromJNDI() {
        // BeanManager is only available when CDI is available. This is achieved by the presence of beans.xml file
        InitialContext context;
        Object result;
        try {
            context = new InitialContext();
            result = context.lookup("java:comp/BeanManager"); // lookup in JBossAS
        } catch (NamingException e) {
            throw new RuntimeException("BeanManager could not be found in JNDI", e);
        }
        return (BeanManager) result;
    }

    @SuppressWarnings("unchecked")
    public <T> T getContextualInstance(final BeanManager manager, final Class<T> type) {
        T result = null;
        Bean<T> bean = (Bean<T>) manager.resolve(manager.getBeans(type));
        if (bean != null) {
            CreationalContext<T> context = manager.createCreationalContext(bean);
            if (context != null) {
                result = (T) manager.getReference(bean, type, context);
            }
        }
        return result;
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return source instanceof Application;
    }
}
