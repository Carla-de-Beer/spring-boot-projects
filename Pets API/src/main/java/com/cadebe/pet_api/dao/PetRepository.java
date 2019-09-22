package com.cadebe.pet_api.dao;

import com.cadebe.pet_api.model.Pet;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PetRepository extends MongoRepository<Pet, String> {
    Pet findById(ObjectId _id);
}