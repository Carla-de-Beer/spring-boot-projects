package com.cadebe.pets_api.dao;

import com.cadebe.pets_api.model.Pet;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PetRepository extends MongoRepository<Pet, String> {

    Optional<Pet> findById(ObjectId _id);
}