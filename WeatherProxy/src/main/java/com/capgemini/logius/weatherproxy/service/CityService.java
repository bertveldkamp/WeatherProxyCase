package com.capgemini.logius.weatherproxy.service;

import com.capgemini.logius.weatherproxy.client.WeatherClient;
import com.capgemini.logius.weatherproxy.client.response.WeatherResponse;
import com.capgemini.logius.weatherproxy.dto.CityDto;
import com.capgemini.logius.weatherproxy.model.City;
import com.capgemini.logius.weatherproxy.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private CityRepository cityRepository;
    private WeatherClient weatherClient;

    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Autowired
    public void setWeatherClient(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public ResponseEntity<List<CityDto>> getCities() {
        Iterable<City> citiesIterable = cityRepository.findAll();
        List<CityDto> citiesList = new ArrayList<>();
        citiesIterable.forEach(c -> citiesList.add(new CityDto(c)));
        return ResponseEntity.status(HttpStatus.OK).body(citiesList);
    }

    public ResponseEntity<CityDto> getCity(final String cityName) {
        Optional<City> optionalCity = cityRepository.findCityByCityName(cityName);
        return optionalCity.map(city -> ResponseEntity.status(HttpStatus.OK).body(new CityDto(city)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Void> createCity(final String cityName) {
            City result = weatherClient.getWeatherInCity(cityName);
            if(result == null) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            result.setCityName(cityName.toUpperCase());
            Optional<City> cityOptional = cityRepository.findCityByCityName(cityName.toUpperCase());
            cityOptional.ifPresent(value -> result.setCityId(value.getCityId()));
            cityRepository.save(result);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    public ResponseEntity<Void> deleteCity(final String cityName) {
        cityRepository.deleteByCityName(cityName.toUpperCase());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }




}
