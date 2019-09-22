package com.cadebe.persons_api.dao;

import com.cadebe.persons_api.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonJPA extends CrudRepository<Person, UUID> {
}