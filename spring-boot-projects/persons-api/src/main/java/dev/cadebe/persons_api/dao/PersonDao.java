package dev.cadebe.persons_api.dao;

import dev.cadebe.persons_api.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDao {

    List<Person> findAll();

    List<Person> findAllByColorPreference(int colorCode);

    Optional<Person> findById(UUID id);

    Person save(Person person);

    Person updateById(UUID id, Person updatedPerson);

    void deleteById(UUID id);
}