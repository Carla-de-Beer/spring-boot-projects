package com.cadebe.persons_api.controller;

import com.cadebe.persons_api.util.ColorMap;
import com.cadebe.persons_api.model.Person;
import com.cadebe.persons_api.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // CREATE
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createNewPerson(@RequestBody Person newPerson) {
        newPerson.setId(UUID.randomUUID());
        return ResponseEntity.ok(personService.save(newPerson));
    }

    // READ
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findAll() {
        return personService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, path = "{id}")
    public Person findById(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id.toLowerCase());
        Optional person = personService.findById(uuid);
        if (!person.isPresent()) {
            handleError(uuid);
            return null;
        }
        return (Person) person.get();
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, path = "/color/{colorCode}")
    public List<Person> getAllPersonsByColor(@PathVariable("colorCode") String inputColor) {
        int colorCode = ColorMap.getOrdinalFromString(inputColor);
        return this.personService.findAllByColorPreference(colorCode);
    }

    // UPDATE
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, path = "{id}")
    public Person update(@PathVariable("id") String id, @RequestBody Person person) {
        UUID uuid = UUID.fromString(id);
        if (!personService.findById(uuid).isPresent()) {
            handleError(uuid);
            return null;
        }
        return personService.updateById(uuid, person);
    }

    // DELETE
    @RequestMapping(method = RequestMethod.DELETE, path = "{id}")
    public Boolean deleteById(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        if (!personService.findById(uuid).isPresent()) {
            handleError(uuid);
            return false;
        }
        personService.deleteById(uuid);
        return true;
    }

    private void handleError(UUID uuid) {
        log.error("Received id {} is not present and person object could not be updated", uuid);
    }
}