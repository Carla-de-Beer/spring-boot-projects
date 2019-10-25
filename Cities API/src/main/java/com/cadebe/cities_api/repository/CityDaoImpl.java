package com.cadebe.cities_api.repository;

import com.cadebe.cities_api.exception.CityNotFoundException;
import com.cadebe.cities_api.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("JPA_Dao")
public class CityDaoImpl implements CityDao {

    private final CityJPA CITY_DAO;
    private final MessageSource MESSAGE_SERVICE;

    @Autowired
    public CityDaoImpl(CityJPA cityDao, MessageSource messageSource) {
        this.CITY_DAO = cityDao;
        this.MESSAGE_SERVICE = messageSource;
    }

    @Override
    public List<City> findAll() {
        return (List<City>) this.CITY_DAO.findAll();
    }

    @Override
    public Optional<City> findById(UUID id) {
        return CITY_DAO.findById(id);
    }

    @Override
    public Optional<List<City>> findByName(String name) {
        return CITY_DAO.findByName(name);
    }

    @Override
    public Optional<List<City>> findByCountryCode(String countryCode) {
        return CITY_DAO.findByCountryCode(countryCode);
    }

    @Override
    public Optional<List<City>> findAllCitiesWithPopulationGreaterThanX(long size) {
        return CITY_DAO.findAllCitiesWithPopulationGreaterThanX(size);
    }

    @Override
    public City save(City city) {

        return CITY_DAO.save(city);
    }

    @Override
    public City update(City city) {
        if (doesIdExist(city.getId())) {
            String message = MESSAGE_SERVICE.getMessage("city.does.not.exist", null, LocaleContextHolder.getLocale());
            throw new CityNotFoundException(message + ".");
        }
        return CITY_DAO.save(city);
    }

    @Override
    public void deleteById(UUID id) {
        CITY_DAO.deleteById(id);
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
