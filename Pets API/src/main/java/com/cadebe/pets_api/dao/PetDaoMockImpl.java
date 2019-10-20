package com.cadebe.pets_api.dao;

import com.cadebe.pets_api.model.Pet;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("mockDao")
public class PetDaoMockImpl implements PetDao {

    private final Map<ObjectId, Pet> database;

    public PetDaoMockImpl() {
        this.database = new HashMap<>();
        ObjectId id = ObjectId.get();

        this.database.put(id, Pet.builder()
                .name("Rex")
                .species("dog")
                .breed("pitbull").build());
    }

    @Override
    public Optional<List<Pet>> getAllPets() {
        return Optional.of(new ArrayList<>(database.values()));
    }

    @Override
    public Optional<Pet> getPetById(ObjectId id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public void modifyPetById(ObjectId id, Pet pet) {
    }

    @Override
    public Optional<Pet> insertNewPet(Pet pet) {
        database.put(pet.getObjectId(), pet);
        return Optional.of(pet);
    }

    @Override
    public void deletePet(ObjectId id) {
        database.remove(id);
    }
}
