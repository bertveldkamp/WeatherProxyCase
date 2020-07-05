package com.capgemini.logius.weatherproxy.client.response;

import com.capgemini.logius.weatherproxy.model.City;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WeatherResponse {
    private long id;
    private String name;
    private List<Map<String, Object>> weather;
    private Map<String, Object> main;
    private Map<String, Object> sys;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map<String, Object>> getWeather() {
        return weather;
    }

    public void setWeather(List<Map<String, Object>> weather) {
        this.weather = weather;
    }

    public Map<String, Object> getMain() {
        return main;
    }

    public void setMain(Map<String, Object> main) {
        this.main = main;
    }

    public Map<String, Object> getSys() {
        return sys;
    }

    public void setSys(Map<String, Object> sys) {
        this.sys = sys;
    }

    public City convertToCity() {
        Objects.requireNonNull(weather);
        Objects.requireNonNull(main);
        Objects.requireNonNull(sys);
        City city = new City();
        city.setName(name);
        city.setId(id);
        if(weather.size() > 0) {
            city.setDescription(weather.get(0).containsKey("description") ? (String) weather.get(0).get("description") : "");
        }
        city.setTempMin(main.containsKey("temp_min") ? (double)main.get("temp_min") : 0.0);
        city.setTempMax(main.containsKey("temp_max") ? (double)main.get("temp_max") : 0.0);
        city.setSunrise(sys.containsKey("sunrise") ? (int)sys.get("sunrise") : 0);
        city.setSunset(sys.containsKey("sunset") ? (int)sys.get("sunset") : 0);
        return city;
    }
}
