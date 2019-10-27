package com.cadebe.persons_api.controller;

import com.cadebe.persons_api.model.Person;
import com.cadebe.persons_api.service.PersonService;
import com.cadebe.persons_api.util.ColorMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

    private final PersonService PERSON_SERVICE;
    private final MessageSource MESSAGE_SOURCE;

    @Autowired
    public PersonController(PersonService personService, MessageSource messageSource) {
        this.PERSON_SERVICE = personService;
        this.MESSAGE_SOURCE = messageSource;
    }

    // CREATE
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createNewPerson(@RequestBody Person newPerson) {
        newPerson.setId(UUID.randomUUID());
        return new ResponseEntity<>(PERSON_SERVICE.save(newPerson), HttpStatus.CREATED);
    }

    // READ
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(PERSON_SERVICE.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, path = "{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id.toLowerCase());
        Optional person = PERSON_SERVICE.findById(uuid);
        if (person.isEmpty()) {
            ErrorHandler<UUID> eh = new ErrorHandler<>();
            eh.handelError(uuid);
            return new ResponseEntity<>(generateErrorMessage("person.not.found") + ".", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>((Person) person.get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, path = "/color/{colorCode}")
    public ResponseEntity<Object> getAllPersonsByColor(@PathVariable("colorCode") String inputColor) {
        int colorCode = ColorMap.getOrdinalFromString(inputColor);
        return new ResponseEntity<>(this.PERSON_SERVICE.findAllByColorPreference(colorCode), HttpStatus.OK);
    }

    // UPDATE
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, path = "{id}")
    public ResponseEntity<Object> update(@PathVariable("id") String id, @RequestBody Person person) {
        UUID uuid = UUID.fromString(id);
        if (PERSON_SERVICE.findById(uuid).isEmpty()) {
            ErrorHandler<UUID> eh = new ErrorHandler<>();
            eh.handelError(uuid);
            return new ResponseEntity<>(generateErrorMessage("person.not.found") + ".", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(PERSON_SERVICE.updateById(uuid, person), HttpStatus.NO_CONTENT);
    }

    // DELETE
    @RequestMapping(method = RequestMethod.DELETE, path = "{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        if (PERSON_SERVICE.findById(uuid).isEmpty()) {
            ErrorHandler<UUID> eh = new ErrorHandler<>();
            eh.handelError(uuid);
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
        PERSON_SERVICE.deleteById(uuid);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    private String generateErrorMessage(String i18n) {
        return MESSAGE_SOURCE.getMessage(i18n, null, LocaleContextHolder.getLocale());
    }

    private static class ErrorHandler<T> {
        private void handelError(T uuid) {
            log.error("Received id {} is not present and the database could not be updated", uuid);
        }
    }
}