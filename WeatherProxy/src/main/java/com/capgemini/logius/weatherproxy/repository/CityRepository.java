package com.capgemini.logius.weatherproxy.repository;

import com.capgemini.logius.weatherproxy.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {
    Optional<City> findCityByCityName(String cityName);

    @Transactional
    void deleteByCityName(String cityName);

}
