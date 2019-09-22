package com.cadebe.pet_api.service;

import com.cadebe.pet_api.dao.PetDao;
import com.cadebe.pet_api.model.Pet;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetDao petDao;

    @Autowired
    public PetService(@Qualifier("mongoDbDao") PetDao petDao) {
        this.petDao = petDao;
    }

    public List<Pet> getAllPets() {
        return petDao.getAllPets();
    }

    public Pet getPetById(ObjectId id) {
        return petDao.getPetById(id);
    }

    public void modifyPetById(ObjectId id, Pet pet) {
        petDao.modifyPetById(id, pet);
    }

    public Pet insertNewPet(Pet pet) {
        return petDao.insertNewPet(pet);
    }

    public void deletePet(ObjectId id) throws IllegalAccessException {
        Pet pet = petDao.getPetById(id);
        if (pet == null) {
            throw new IllegalAccessException();
        }
        petDao.deletePet(id);
    }
}
