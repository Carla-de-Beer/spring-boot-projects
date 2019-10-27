package com.cadebe.pets_api.dao;

import com.cadebe.pets_api.model.Pet;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface PetDao {

    Optional<List<Pet>> getAllPets();

    Optional<Pet> getPetById(ObjectId id);

    void modifyPetById(ObjectId id, Pet pet);

    Optional<Pet> insertNewPet(Pet pet);

    void deletePet(ObjectId id);
}
