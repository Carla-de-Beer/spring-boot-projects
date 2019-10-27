package com.cadebe.persons_api.service;

import com.cadebe.persons_api.dao.PersonDao;
import com.cadebe.persons_api.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    private final PersonDao PERSON_DAO;

    @Autowired
    public PersonService(@Qualifier("JPA_Dao") PersonDao personDao) {
        this.PERSON_DAO = personDao;
    }

    public List<Person> findAll() {
        return PERSON_DAO.findAll();
    }

    public Person save(Person person) {
        return PERSON_DAO.save(person);
    }

    public Person updateById(UUID id, Person person) {
        return PERSON_DAO.updateById(id, person);
    }

    public Optional findById(UUID id) {
        return PERSON_DAO.findById(id);
    }

    public List<Person> findAllByColorPreference(int colorCode) {
        return PERSON_DAO.findAllByColorPreference(colorCode);
    }

    public void deleteById(UUID id) {
        PERSON_DAO.deleteById(id);
    }
}
