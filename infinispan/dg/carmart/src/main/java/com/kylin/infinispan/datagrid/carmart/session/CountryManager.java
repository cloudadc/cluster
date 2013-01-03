package com.kylin.infinispan.datagrid.carmart.session;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.kylin.infinispan.datagrid.carmart.model.Car.Country;


/**
 * Produces an array of supported countries
 */
public class CountryManager {
    @Produces
    @Named
    public Country[] getCountries() {
        return Country.values();
    }
}
