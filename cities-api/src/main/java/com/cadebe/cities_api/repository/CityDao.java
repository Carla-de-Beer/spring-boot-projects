package com.cadebe.cities_api.repository;

import com.cadebe.cities_api.model.City;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CityDao {

    List<City> findAll();

    Optional<City> findById(UUID id);

    Optional<List<City>> findByName(String name);

    Optional<List<City>> findByCountryCode(String countryCode);

    Optional<List<City>> findAllCitiesWithPopulationGreaterThanX(long size);

    City save(City city);

    City update(City city);

    void deleteById(UUID id);
}