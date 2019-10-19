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

    private final CityJPA cityDao;
    private final MessageSource messageSource;

    @Autowired
    public CityDaoImpl(CityJPA cityDao, MessageSource messageSource) {
        this.cityDao = cityDao;
        this.messageSource = messageSource;
    }

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
        return cityDao.save(city);
    }

    @Override
    public City update(City city) {
        if (doesIdExist(city.getId())) {
            String message = messageSource.getMessage("city.does.not.exist", null, LocaleContextHolder.getLocale());
            throw new CityNotFoundException(message + ".");
        }
        return cityDao.save(city);
    }

    @Override
    public void deleteById(UUID id) {
        cityDao.deleteById(id);
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
