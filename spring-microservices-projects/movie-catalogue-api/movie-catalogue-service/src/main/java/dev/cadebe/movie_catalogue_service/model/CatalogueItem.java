package dev.cadebe.movie_catalogue_service.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CatalogueItem {

    String name;
    String description;
    int rating;

}
