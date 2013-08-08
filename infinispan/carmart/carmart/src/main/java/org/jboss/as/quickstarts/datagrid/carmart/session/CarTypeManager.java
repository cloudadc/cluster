package org.jboss.as.quickstarts.datagrid.carmart.session;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.jboss.as.quickstarts.datagrid.carmart.model.Car.CarType;

/**
 * Produces an array of supported car types
 * 
 * 
 */
public class CarTypeManager {
    @Produces
    @Named
    public CarType[] getCarTypes() {
        return CarType.values();
    }
}
