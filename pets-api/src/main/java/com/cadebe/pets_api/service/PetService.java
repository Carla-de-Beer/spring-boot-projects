package com.cadebe.pets_api.service;

import com.cadebe.pets_api.dao.PetDao;
import com.cadebe.pets_api.model.Pet;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetDao PET_DAO;

    @Autowired
    public PetService(@Qualifier("mongoDbDao") PetDao petDao) {
        this.PET_DAO = petDao;
    }

    public Optional<List<Pet>> getAllPets() {
        return PET_DAO.getAllPets();
    }

    public Optional<Pet> getPetById(ObjectId id) {
        return PET_DAO.getPetById(id);
    }

    public void modifyPetById(ObjectId id, Pet pet) {
        PET_DAO.modifyPetById(id, pet);
    }

    public Optional<Pet> insertNewPet(Pet pet) {
        return PET_DAO.insertNewPet(pet);
    }

    public void deletePet(ObjectId id) {
        PET_DAO.deletePet(id);
    }
}
