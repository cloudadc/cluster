package org.jboss.as.quickstarts.datagrid.carmart.session;

import org.infinispan.client.hotrod.RemoteCache;
import org.jboss.as.quickstarts.datagrid.carmart.model.Car;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

/**
 * Adds, retrieves, removes new cars from the cache. Also returns a list of cars stored in the cache.
 * 
 * 
 */
@Model
public class CarManager {
    public static final String CACHE_NAME = "carcache";
    public static final String CAR_NUMBERS_KEY = "carnumbers";

    @Inject
    private CacheContainerProvider provider;

    private RemoteCache<String, Object> carCache;

    private String carId;
    private Car car = new Car();

    public CarManager() {
    }

    public String addNewCar() {
        carCache = provider.getCacheContainer().getCache(CACHE_NAME);
        carCache.put(CarManager.encode(car.getNumberPlate()), car);
        List<String> carNumbers = getNumberPlateList();
        if (carNumbers == null)
            carNumbers = new LinkedList<String>();
        carNumbers.add(car.getNumberPlate());
        carCache.replace(CAR_NUMBERS_KEY, carNumbers);
        return "home";
    }

    @SuppressWarnings("unchecked")
    private List<String> getNumberPlateList() {
        return (List<String>) carCache.get(CAR_NUMBERS_KEY);
    }

    public String showCarDetails(String numberPlate) {
        carCache = provider.getCacheContainer().getCache(CACHE_NAME);
        this.car = (Car) carCache.get(encode(numberPlate));
        return "showdetails";
    }

    public List<String> getCarList() {
        // retrieve a cache
        carCache = provider.getCacheContainer().getCache(CACHE_NAME);
        // retrieve a list of number plates from the cache
        return getNumberPlateList();
    }

    public String removeCar(String numberPlate) {
        carCache = provider.getCacheContainer().getCache(CACHE_NAME);
        carCache.remove(encode(numberPlate));
        List<String> carNumbers = getNumberPlateList();
        carNumbers.remove(numberPlate);
        carCache.replace(CAR_NUMBERS_KEY, carNumbers);
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
