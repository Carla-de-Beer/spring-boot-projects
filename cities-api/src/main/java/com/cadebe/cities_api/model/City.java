package com.cadebe.cities_api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "cities")
@ApiModel(description = "City data")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class City {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @ApiModelProperty(notes = "UUID associated with the city")
    UUID id;

    @ApiModelProperty(notes = "City name")
    String name;

    @ApiModelProperty(notes = "Country")
    String countryCode;

    @ApiModelProperty(notes = "Population size")
    int population;

    @ApiModelProperty(notes = "Longitude")
    double longitude;

    @ApiModelProperty(notes = "Latitude")
    double latitude;
}