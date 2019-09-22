package com.cadebe.pet_api.dao;

import com.cadebe.pet_api.model.Pet;
import org.bson.types.ObjectId;

import java.util.List;

public interface PetDao {

    List<Pet> getAllPets();

    Pet getPetById(ObjectId id);

    void modifyPetById(ObjectId id, Pet pet);

    Pet insertNewPet(Pet pet);

    void deletePet(ObjectId id);
}
