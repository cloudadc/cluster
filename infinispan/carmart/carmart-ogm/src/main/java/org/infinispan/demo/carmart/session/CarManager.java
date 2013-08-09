package org.infinispan.demo.carmart.session;

import org.infinispan.demo.carmart.model.Car;
import org.infinispan.demo.carmart.session.EntityManagerFactoryProvider;

import javax.enterprise.inject.Model;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Logger;

/**
 * Adds, retrieves, removes new cars from the cache. Also returns a list of cars stored in the cache.
 * 
 * 
 */
@Model
public class CarManager {

    private Logger log = Logger.getLogger(this.getClass().getName());

    public static final String CACHE_NAME = "carcache";

    public static final String CAR_NUMBERS_KEY = "carnumbers";

    @PersistenceUnit(unitName = "org.infinispan.demo.carmart")
    private EntityManagerFactoryProvider provider;

    private String carId;
    private Car car = new Car();

    public CarManager() {
    }

    public String addNewCar() {
        try {
        	EntityManager em = provider.getEntityManagerFactory().createEntityManager();
        	em.persist(car);
        	em.close();
            log.info("add a new car");
        } catch (Exception e) {
        	log.warning("An exception occured while adding a new car");
        	e.printStackTrace();
        }
        return "home";
    }

    public String addNewCarWithRollback() {
        return "home";
    }

    public String showCarDetails(String numberPlate) {
        try {
        	EntityManager em = provider.getEntityManagerFactory().createEntityManager();
        	this.car = em.find(Car.class, numberPlate);
        	em.close();
        	log.info("show car " + numberPlate + " details");
        } catch (Exception e) {
        	log.warning("An exception occured while extracting " + numberPlate + " from infinispan");
        	e.printStackTrace();
        }
        return "showdetails";
    }

    public List<String> getCarList() {
        List<String> result = null;
        try {
        	EntityManager em = provider.getEntityManagerFactory().createEntityManager();
            Query query = em.createQuery("SELECT * FROM Car");  
            result = query.getResultList();
            em.close();
            log.info("extract cae list");
        } catch (Exception e) {
            log.warning("An exception occured while get car list");
            e.printStackTrace();
        }
        return result;
    }

    public String removeCar(String numberPlate) {
        try {
        	EntityManager em = provider.getEntityManagerFactory().createEntityManager();
        	Car car = em.find(Car.class, numberPlate);
        	em.remove(car);
        	em.close();
            log.info("remote car " + numberPlate);
        } catch (Exception e) {
        	log.warning("error whiling remote car " + numberPlate);
        	e.printStackTrace();
        }
        return null;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    public static String encode(String key) {
        try {
            return URLEncoder.encode(key, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decode(String key) {
        try {
            return URLDecoder.decode(key, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
