package com.cadebe.cities_api.controller;

import com.cadebe.cities_api.exception.CityNotFoundException;
import com.cadebe.cities_api.model.City;
import com.cadebe.cities_api.service.CityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final CityService cityService;

    @Autowired
    public CitiesController(CityService cityService) {
        this.cityService = cityService;
    }

    // CREATE
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(value = CityNotFoundException.class)
    public ResponseEntity<Object> createNewCity(@RequestBody City newCity) {
        try {
            return new ResponseEntity<>(cityService.save(newCity), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("City could not be added: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // READ
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(value = CityNotFoundException.class)
    @ApiOperation(value = "Finds all cities", notes = "Returns all cities listed in the DB", response = City.class)
    public ResponseEntity<Object> findAll() {
        try {
            return new ResponseEntity<>(cityService.findAll(), HttpStatus.OK);
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
        Optional<City> city = cityService.findById(uuid);
        if (!city.isPresent()) {
            ErrorHandler<UUID> eh = new ErrorHandler<>();
            eh.handelError(uuid);
            return new ResponseEntity<>("City not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(city.get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, path = "name/{name}")
    @ExceptionHandler(value = CityNotFoundException.class)
    @ApiOperation(value = "Finds the list of cities for a given name", notes = "Returns a list of cities for a given name", response = City.class)
    public ResponseEntity<Object> findByName(@ApiParam(value = "Name value for city to be retrieved", required = true)
                                             @PathVariable("name") String name) {
        Optional<List<City>> city = cityService.findByName(name);
        ErrorHandler<String> eh = new ErrorHandler<>();
        eh.handelError(name);
        return city.<ResponseEntity<Object>>map(cities -> new ResponseEntity<>(cities, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>("City not found", HttpStatus.NOT_FOUND));
    }

    // UPDATE
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, path = "{id}")
    @ExceptionHandler(value = CityNotFoundException.class)
    public ResponseEntity<Object> update(@PathVariable("id") String id, @RequestBody City city) {
        UUID uuid = UUID.fromString(id);
        if (!cityService.findById(uuid).isPresent()) {
            ErrorHandler<UUID> eh = new ErrorHandler<>();
            eh.handelError(uuid);
            return new ResponseEntity<>("City not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cityService.update(city), HttpStatus.NO_CONTENT);
    }

    // DELETE
    @RequestMapping(method = RequestMethod.DELETE, path = "{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        if (!cityService.findById(uuid).isPresent()) {
            ErrorHandler<UUID> eh = new ErrorHandler<>();
            eh.handelError(uuid);
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        cityService.deleteById(uuid);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    private static class ErrorHandler<T> {
        private void handelError(T uuid) {
            log.error("Received id {} is not present and city object could not be updated", uuid);
        }
    }
}