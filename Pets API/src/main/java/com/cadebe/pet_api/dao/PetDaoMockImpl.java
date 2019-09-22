package com.cadebe.pet_api.dao;

import com.cadebe.pet_api.model.Pet;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("mockDao")
public class PetDaoMockImpl implements PetDao {

    private final Map<ObjectId, Pet> database;

    public PetDaoMockImpl() {
        this.database = new HashMap<>();
        ObjectId id = ObjectId.get();
        this.database.put(id, new Pet(id, "Rex", "dog", "pitbull"));
    }

    @Override
    public List<Pet> getAllPets() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Pet getPetById(ObjectId id) {
        return database.get(id);
    }

    @Override
    public void modifyPetById(ObjectId id, Pet pet) {
    }

    @Override
    public Pet insertNewPet(Pet pet) {
        database.put(pet.getObjectId(), pet);
        return pet;
    }

    @Override
    public void deletePet(ObjectId id) {
        database.remove(id);
    }
}
