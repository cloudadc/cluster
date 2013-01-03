package com.kylin.infinispan.datagrid.carmart.session;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.kylin.infinispan.datagrid.carmart.model.Car.CarType;


/**
 * Produces an array of supported car types
 */
public class CarTypeManager {
    @Produces
    @Named
    public CarType[] getCarTypes() {
        return CarType.values();
    }
}
