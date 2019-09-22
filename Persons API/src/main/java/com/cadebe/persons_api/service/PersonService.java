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

    private PersonDao personDao;

    @Autowired
    public PersonService(@Qualifier("mockDao") PersonDao personDao) {
        this.personDao = personDao;
    }

    public List<Person> findAll() {
        return personDao.findAll();
    }

    public Person save(Person person) {
        return personDao.save(person);
    }

    public Person updateById(UUID id, Person person) {
        return personDao.updateById(id, person);
    }

    public Optional findById(UUID id) {
        return personDao.findById(id);
    }

    public List<Person> findAllByColorPreference(int colorCode) {
        return personDao.findAllByColorPreference(colorCode);
    }

    public void deleteById(UUID id) {
        personDao.deleteById(id);
    }
}
