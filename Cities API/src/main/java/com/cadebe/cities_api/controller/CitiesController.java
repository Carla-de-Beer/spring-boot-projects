package com.cadebe.cities_api.controller;

import com.cadebe.cities_api.exception.CityNotFoundException;
import com.cadebe.cities_api.model.City;
import com.cadebe.cities_api.service.CityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/cities/")
public class CitiesController {

    private final CityService CITY_SERVICE;
    private final MessageSource MESSAGE_SOURCE;

    @Autowired
    public CitiesController(CityService cityService, MessageSource messageSource) {
        this.CITY_SERVICE = cityService;
        this.MESSAGE_SOURCE = messageSource;
    }

    // CREATE
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(value = CityNotFoundException.class)
    public ResponseEntity<Object> createNewCity(@RequestBody City newCity) {
        return new ResponseEntity<>(CITY_SERVICE.save(newCity), HttpStatus.CREATED);
    }

    // READ
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(value = CityNotFoundException.class)
    @ApiOperation(value = "Finds all cities", notes = "Returns all cities listed in the DB", response = City.class)
    public ResponseEntity<Object> findAll() {
        try {
            return new ResponseEntity<>(CITY_SERVICE.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, path = "{id}")
    @ExceptionHandler(value = CityNotFoundException.class)
    @ApiOperation(value = "Finds the city for a given ID", notes = "Returns the requested city listed in the DB", response = City.class)
    public ResponseEntity<Object> findById(@ApiParam(value = "ID value for city to be retrieved", required = true)
                                           @PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        Optional<City> city = CITY_SERVICE.findById(uuid);
        if (city.isEmpty()) {
            handelError(uuid);
            return new ResponseEntity<>(generateErrorMessage("city.not.found") + ".", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(city.get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, path = "name/{name}")
    @ExceptionHandler(value = CityNotFoundException.class)
    @ApiOperation(value = "Finds the list of cities for a given name", notes = "Returns a list of cities for a given name", response = City.class)
    public ResponseEntity<Object> findByName(@ApiParam(value = "Name value for city to be retrieved", required = true)
                                             @PathVariable("name") String name) {
        Optional<List<City>> city = CITY_SERVICE.findByName(name);
        handelError(name);
        return city.<ResponseEntity<Object>>map(cities -> new ResponseEntity<>(cities, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(generateErrorMessage("city.not.found") + ".", HttpStatus.NOT_FOUND));
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, path = "country/{countryCode}")
    @ExceptionHandler(value = CityNotFoundException.class)
    @ApiOperation(value = "Finds the list of cities for a given country code", notes = "Returns a list of cities for a given country code", response = City.class)
    public ResponseEntity<Object> findByCountryCode(@ApiParam(value = "Country name value for cities to be retrieved", required = true)
                                                    @PathVariable("countryCode") String countryCode) {
        Optional<List<City>> city = CITY_SERVICE.findByCountryCode(countryCode);
        handelError(countryCode);
        return city.<ResponseEntity<Object>>map(cities -> new ResponseEntity<>(cities, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(generateErrorMessage("city.not.found") + ".", HttpStatus.NOT_FOUND));
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, path = "populationSize/{size}")
    @ExceptionHandler(value = CityNotFoundException.class)
    @ApiOperation(value = "Finds the list of cities greater than a given parameter", notes = "Returns a list of cities greater than a given parameter", response = City.class)
    public ResponseEntity<Object> findAllCitiesWithPopulationGreaterThanX(@ApiParam(value = "Population size value for cities to be retrieved", required = true)
                                                                          @PathVariable("size") long size) {
        Optional<List<City>> city = CITY_SERVICE.findAllCitiesWithPopulationGreaterThanX(size);
        handelError(Long.toString(size));
        return city.<ResponseEntity<Object>>map(cities -> new ResponseEntity<>(cities, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(generateErrorMessage("city.not.found") + ".", HttpStatus.NOT_FOUND));
    }

    // UPDATE
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, path = "{id}")
    @ExceptionHandler(value = CityNotFoundException.class)
    public ResponseEntity<Object> update(@PathVariable("id") String id, @RequestBody City city) {
        UUID uuid = UUID.fromString(id);
        if (CITY_SERVICE.findById(uuid).isEmpty()) {
            handelError(uuid);
            return new ResponseEntity<>(generateErrorMessage("city.not.found") + ".", HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(CITY_SERVICE.update(city), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(generateErrorMessage("city.not.updated") + ": " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // DELETE
    @RequestMapping(method = RequestMethod.DELETE, path = "{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        if (CITY_SERVICE.findById(uuid).isEmpty()) {
            handelError(uuid);
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        CITY_SERVICE.deleteById(uuid);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    private String generateErrorMessage(String i18n) {
        return MESSAGE_SOURCE.getMessage(i18n, null, LocaleContextHolder.getLocale());
    }

    private <T> void handelError(T uuid) {
        log.error("Received id {} is not present and the database could not be updated", uuid);
    }
}