package com.cadebe.cities_api.dao;

import com.cadebe.cities_api.model.City;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CityDao {

    List<City> findAll();

    Optional<City> findById(UUID id);

    Optional<List<City>> findByName(String name);

    City save(City stock);

    City update(City stock);

    void deleteById(UUID id);
}