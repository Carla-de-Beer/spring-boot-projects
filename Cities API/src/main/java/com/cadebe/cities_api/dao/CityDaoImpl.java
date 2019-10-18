package com.cadebe.cities_api.dao;

import com.cadebe.cities_api.exception.CityNotFoundException;
import com.cadebe.cities_api.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("JPA_Dao")
public class CityDaoImpl implements CityDao {

    @Autowired
    private CityJPA cityDao;

    @Override
    public List<City> findAll() {
        return (List<City>) this.cityDao.findAll();
    }

    @Override
    public Optional<City> findById(UUID id) {
        return cityDao.findById(id);
    }

    @Override
    public Optional<List<City>> findByName(String name) {
        return cityDao.findByName(name);
    }

    @Override
    public City save(City city) {
        // Don't add the same city with the same name
        if (doesNameExist(city.getName())) {
            throw new CityNotFoundException("City already exists.");
        }
        return cityDao.save(city);
    }

    @Override
    public City update(City city) {
        UUID id = city.getId();
        // Don't add the same city with the same name
        if (doesIdExist(city.getId())) {
            throw new CityNotFoundException("City does not exist.");
        }
        return cityDao.save(city);
    }

    @Override
    public void deleteById(UUID id) {
        cityDao.deleteById(id);
    }

    private Boolean doesNameExist(String name) {
        List<City> list = findAll();
        for (City c : list) {
            if (c.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private Boolean doesIdExist(UUID id) {
        List<City> list = findAll();
        for (City c : list) {
            if (c.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
