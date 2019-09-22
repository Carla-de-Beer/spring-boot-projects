package com.cadebe.pet_api.controller;

import com.cadebe.pet_api.model.Pet;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.cadebe.pet_api.service.PetService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pets")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @RequestMapping(value = "/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Pet> getAllPets() {
        return this.petService.getAllPets();
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Pet getPetById(@PathVariable("id") ObjectId id) {
        return this.petService.getPetById(id);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void modifyPetById(@PathVariable("id") ObjectId id, @Valid @RequestBody Pet pet) {
        this.petService.modifyPetById(id, pet);
    }

    @RequestMapping(value = "/",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Pet insertNewPet(@RequestBody Pet pet) {
        return this.petService.insertNewPet(pet);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE)
    public void deletePet(@PathVariable("id") ObjectId id) throws IllegalAccessException {
        this.petService.deletePet(id);
    }
}
