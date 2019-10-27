package com.cadebe.cities_api.repository;

import com.cadebe.cities_api.model.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CityJPA extends CrudRepository<City, UUID> {

    Optional<List<City>> findByName(@Param("name") String name);

    Optional<List<City>> findByCountryCode(@Param("countryCode") String countryCode);

    @Query(value = "SELECT * FROM cities WHERE population > ?1", nativeQuery = true)
    Optional<List<City>> findAllCitiesWithPopulationGreaterThanX(long size);
}