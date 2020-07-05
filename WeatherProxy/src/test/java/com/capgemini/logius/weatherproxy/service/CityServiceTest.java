package com.capgemini.logius.weatherproxy.service;

import com.capgemini.logius.weatherproxy.client.WeatherClient;
import com.capgemini.logius.weatherproxy.model.City;
import com.capgemini.logius.weatherproxy.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@SpringBootTest
class CityServiceTest {
    @Mock
    private CityRepository cityRepository;

    @Mock
    private WeatherClient weatherClient;

    @InjectMocks
    private CityService cityService;


    @Test
    void createCityWithKnownCity() {
        String cityName = "Utrecht";
        City responseCity = new City();
        responseCity.setCityName(cityName);
        responseCity.setName(cityName);
        responseCity.setId(1);
        responseCity.setDescription(cityName);
        responseCity.setSunrise(1);
        responseCity.setSunset(1);
        responseCity.setTempMax(1.0);
        responseCity.setTempMin(1.0);

        Mockito.when(weatherClient.getWeatherInCity(cityName)).thenReturn(responseCity);
        Mockito.when(cityRepository.findCityByCityName(cityName)).thenReturn(Optional.empty());
        Mockito.when(cityRepository.save(responseCity)).thenReturn(responseCity);

        ResponseEntity<Void> responseEntity = cityService.createCity(cityName);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Mockito.verify(cityRepository, times(1)).save(responseCity);
        Mockito.verify(cityRepository, times(1)).findCityByCityName(cityName.toUpperCase());
    }

    @Test
    void createCityWithUnknownCity() {
        Mockito.when(weatherClient.getWeatherInCity("ABC")).thenReturn(null);

        ResponseEntity<Void> responseEntity = cityService.createCity("ABC");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Mockito.verify(cityRepository, times(0)).save(Mockito.any());
        Mockito.verify(cityRepository, times(0)).findCityByCityName(Mockito.any());
    }
}