package org.infinispan.demo.carmart.session;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.infinispan.demo.carmart.model.Car.Country;


/**
 * Produces an array of supported countries
 * 
 */
public class CountryManager {
    @Produces
    @Named
    public Country[] getCountries() {
        return Country.values();
    }
}
