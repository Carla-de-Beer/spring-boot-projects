package com.cadebe.pet_api.dao;

import com.cadebe.pet_api.model.Pet;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mongoDbDao")
public class PetMongoDbImpl implements PetDao {

    @Autowired
    private PetRepository repository;

    @Override
    public List<Pet> getAllPets() {
        return repository.findAll();
    }

    @Override
    public Pet getPetById(ObjectId id) {
        return repository.findById(id);
    }

    @Override
    public void modifyPetById(ObjectId id, Pet pet) {
        pet.setId(id);
        repository.save(pet);
    }

    @Override
    public Pet insertNewPet(Pet pet) {
        pet.setId(ObjectId.get());
        repository.save(pet);
        return pet;
    }

    @Override
    public void deletePet(ObjectId id) {
        Pet pet = repository.findById(id);
        repository.delete(pet);
    }
}
