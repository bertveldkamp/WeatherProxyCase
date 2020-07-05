package com.capgemini.logius.weatherproxy.controller;

import com.capgemini.logius.weatherproxy.dto.CityDto;
import com.capgemini.logius.weatherproxy.model.City;
import com.capgemini.logius.weatherproxy.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/weatherproxy")
public class CityController {
    private CityService cityService;

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/cities")
    public ResponseEntity<List<CityDto>> getAllCities() {
        return cityService.getCities();
    }

    @GetMapping("/cities/{city}")
    public ResponseEntity<CityDto> getCity(@PathVariable("city") final String cityName) {
        if(cityName == null || cityName.length() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return cityService.getCity(cityName);
    }

    @PostMapping("/cities/{city}")
    public ResponseEntity<Void> createCity(@PathVariable("city") final String cityName) {
        return cityService.createCity(cityName);
    }

    @DeleteMapping("/cities/{city}")
    public ResponseEntity<Void> deleteCity(@PathVariable("city") final String cityName) {
        if(cityName == null || cityName.length() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return cityService.deleteCity(cityName);
    }


}
