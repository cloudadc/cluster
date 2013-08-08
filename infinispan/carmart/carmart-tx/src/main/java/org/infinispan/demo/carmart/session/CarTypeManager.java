package org.infinispan.demo.carmart.session;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.infinispan.demo.carmart.model.Car.CarType;


/**
 * Produces an array of supported car types
 * 
 */
public class CarTypeManager {
    @Produces
    @Named
    public CarType[] getCarTypes() {
        return CarType.values();
    }
}
