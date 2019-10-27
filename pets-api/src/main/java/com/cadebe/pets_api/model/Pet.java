package com.cadebe.pets_api.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Pet {

    @Id
    ObjectId id;

    String name;
    String species;
    String breed;

    public String getId() {
        return this.id.toHexString();
    }

    public ObjectId getObjectId() {
        return this.id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
