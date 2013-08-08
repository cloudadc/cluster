package org.infinispan.demo.carmart.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Represents a car in the car mart. Car objects are stored in the cache.
 * 
 * 
 */

@Entity
@Table(name="carmart")
public class Car implements Serializable {

    private static final long serialVersionUID = 188164481825309731L;

    public enum CarType {
        SEDAN, HATCHBACK, COMBI, CABRIO, ROADSTER
    }

    public enum Country {
        CHN, USA, GERMANY
    }

    public Car() {
    }

    public Car(String brand, double displacement, CarType type, String color, String numberPlate, Country country) {
        this.brand = brand;
        this.displacement = displacement;
        this.type = type;
        this.color = color;
        this.numberPlate = numberPlate;
        this.country = country;
    }

    private String brand;
    private double displacement;
    private CarType type;
    private String color;
    private String numberPlate;
    private Country country;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getDisplacement() {
        return displacement;
    }

    public void setDisplacement(double displacement) {
        this.displacement = displacement;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }
}