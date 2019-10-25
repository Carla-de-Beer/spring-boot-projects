package com.cadebe.cities_api.service;

import com.cadebe.cities_api.repository.CityDao;
import com.cadebe.cities_api.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CityService {

    private final CityDao CITY_DAO;

    @Autowired
    public CityService(@Qualifier("JPA_Dao") CityDao cityDao) {
        this.CITY_DAO = cityDao;
    }

    public List<City> findAll() {
        return this.CITY_DAO.findAll();
    }

    public Optional<City> findById(UUID id) {
        return this.CITY_DAO.findById(id);
    }

    public Optional<List<City>> findByName(String name) {
        return this.CITY_DAO.findByName(name);
    }

    public Optional<List<City>> findByCountryCode(String countryCode) {
        return this.CITY_DAO.findByCountryCode(countryCode);
    }

    public Optional<List<City>> findAllCitiesWithPopulationGreaterThanX(long size) {
        return this.CITY_DAO.findAllCitiesWithPopulationGreaterThanX(size);
    }

    public City save(City city) {
        return this.CITY_DAO.save(city);
    }

    public City update(City city) {
        return this.CITY_DAO.update(city);
    }

    public void deleteById(UUID id) {
        this.CITY_DAO.deleteById(id);
    }
}