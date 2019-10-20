package com.cadebe.pets_api.controller;

import com.cadebe.pets_api.model.Pet;
import com.cadebe.pets_api.service.PetService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/pets/")
public class PetController {

    private final PetService PET_SERVICE;
    private final MessageSource MESSAGE_SOURCE;

    @Autowired
    public PetController(PetService petService, MessageSource messageSource) {
        this.PET_SERVICE = petService;
        this.MESSAGE_SOURCE = messageSource;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllPets() {
        return new ResponseEntity<>(this.PET_SERVICE.getAllPets(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getPetById(@PathVariable("id") String id) {
        ObjectId objectId = new ObjectId(id);
        Optional<Pet> pet = PET_SERVICE.getPetById(objectId);
        if (pet.isEmpty()) {
            return checkExistence(id);
        }
        return new ResponseEntity<>(this.PET_SERVICE.getPetById(objectId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> modifyPetById(@PathVariable("id") String id, @Valid @RequestBody Pet pet) {
        ObjectId objectId = new ObjectId(id);
        if (PET_SERVICE.getPetById(objectId).isEmpty()) {
            return checkExistence(id);
        }
        this.PET_SERVICE.modifyPetById(new ObjectId(id), pet);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertNewPet(@RequestBody Pet pet) {
        return new ResponseEntity<>(this.PET_SERVICE.insertNewPet(pet), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deletePet(@PathVariable("id") String id) {
        ObjectId objectId = new ObjectId(id);
        if (PET_SERVICE.getPetById(objectId).isEmpty()) {
            ErrorHandler<String> eh = new ErrorHandler<>();
            eh.handelError(id);
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        this.PET_SERVICE.deletePet(new ObjectId(id));
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    private ResponseEntity<Object> checkExistence(String id) {
        ErrorHandler<String> eh = new ErrorHandler<>();
        eh.handelError(id);
        return new ResponseEntity<>(generateErrorMessage("pet.not.found") + ".", HttpStatus.NOT_FOUND);
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
