package com.cadebe.pet_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Pet {

    @Id
    private ObjectId id;

    private String name;
    private String species;
    private String breed;

    public Pet(@JsonProperty("id") ObjectId id,
               @JsonProperty("name") String name,
               @JsonProperty("species") String species,
               @JsonProperty("breed") String breed) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.breed = breed;
    }

    public String getId() {
        return this.id.toHexString();
    }

    public ObjectId getObjectId() {
        return this.id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
