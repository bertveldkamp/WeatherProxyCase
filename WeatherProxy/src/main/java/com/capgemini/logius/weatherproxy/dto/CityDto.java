package com.capgemini.logius.weatherproxy.dto;

import com.capgemini.logius.weatherproxy.model.City;

import java.util.Objects;

public class CityDto {
    private String name;
    private long id;
    private String description;
    private double tempMin;
    private double tempMax;
    private int sunrise;
    private int sunset;

    public CityDto() {
    }

    public CityDto(City city) {
        Objects.requireNonNull(city);
        this.id = city.getId();
        this.name = city.getName();
        this.description = city.getDescription();
        this.tempMin = city.getTempMin();
        this.tempMax = city.getTempMax();
        this.sunrise = city.getSunrise();
        this.sunset = city.getSunset();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }
}
