package com.cadebe.cities_api.service;

import com.cadebe.cities_api.dao.CityDao;
import com.cadebe.cities_api.exception.CityNotFoundException;
import com.cadebe.cities_api.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CityService {

    private CityDao cityDao;

    @Autowired
    public CityService(@Qualifier("JPA_Dao") CityDao cityDao) {
        this.cityDao = cityDao;
    }

    public List<City> findAll() {
        return this.cityDao.findAll();
    }

    public Optional<City> findById(UUID id) {
        return cityDao.findById(id);
    }

    public Optional<List<City>> findByName(String name) {
        return cityDao.findByName(name);
    }

    public City save(City city) {
        return cityDao.save(city);
    }

    public City update(City city) {
        return cityDao.update(city);
    }

    public void deleteById(UUID id) {
        cityDao.deleteById(id);
    }
}