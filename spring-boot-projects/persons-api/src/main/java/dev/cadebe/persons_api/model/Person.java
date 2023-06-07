package dev.cadebe.persons_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "person")
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @Setter
    UUID id;

    String firstName;
    String lastName;
    String zipCode;
    String city;

    @Setter
    @JsonIgnore
    int colorCode;

    @Transient
    @Setter
    String colorName;

    public Person(String firstName, String lastName, String zipCode, String city, int colorCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
        this.city = city;
        this.colorCode = colorCode;
    }
}