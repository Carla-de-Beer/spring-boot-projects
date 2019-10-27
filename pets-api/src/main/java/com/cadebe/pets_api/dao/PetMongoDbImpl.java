package com.cadebe.pets_api.dao;

import com.cadebe.pets_api.model.Pet;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("mongoDbDao")
public class PetMongoDbImpl implements PetDao {

    private final PetRepository REPOSITORY;

    @Autowired
    public PetMongoDbImpl(PetRepository repository) {
        this.REPOSITORY = repository;
    }

    @Override
    public Optional<List<Pet>> getAllPets() {
        return Optional.of(REPOSITORY.findAll());
    }

    @Override
    public Optional<Pet> getPetById(ObjectId id) {
        return (Optional<Pet>) REPOSITORY.findById(id);
    }

    @Override
    public void modifyPetById(ObjectId id, Pet pet) {
        pet.setId(id);
        REPOSITORY.save(pet);
    }

    @Override
    public Optional<Pet> insertNewPet(Pet pet) {
        pet.setId(ObjectId.get());
        REPOSITORY.save(pet);
        return Optional.of(pet);
    }

    @Override
    public void deletePet(ObjectId id) {
        Optional<Pet> pet = (Optional<Pet>) REPOSITORY.findById(id);
        pet.ifPresent(REPOSITORY::delete);
    }
}
