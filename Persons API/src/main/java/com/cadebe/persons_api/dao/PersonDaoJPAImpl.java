package com.cadebe.persons_api.dao;

import com.cadebe.persons_api.model.Person;
import com.cadebe.persons_api.util.ColorMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("JPA_Dao")
public class PersonDaoJPAImpl implements PersonDao {

    @Autowired
    private PersonJPA personDao;

    @Override
    public Person save(Person person) {
        // Exclude duplicate new entries
        for (Person p : findAll()) {
            if (p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName())
                    && p.getCity().equals(person.getCity()) && p.getZipCode().equals(person.getZipCode())) {
                return null;
            }
        }

        PersonDaoJPAImpl.addColorCode(person);
        return personDao.save(person);
    }

    @Override
    public List<Person> findAll() {
        List<Person> list = (List<Person>) this.personDao.findAll();
        for (Person person : list) {
            PersonDaoJPAImpl.addColorString(person);
        }
        return list;
    }

    @Override
    public Optional<Person> findById(UUID id) {
        Optional<Person> person = personDao.findById(id);
        person.ifPresent(value -> PersonDaoJPAImpl.addColorString((Person) value));
        return person;
    }

    @Override
    public List<Person> findAllByColorPreference(int color) {
        List<Person> list = (List<Person>) this.personDao.findAll();
        List<Person> resultList = new ArrayList<>();
        for (Person person : list) {
            if (person.getColorCode() == color) {
                PersonDaoJPAImpl.addColorString(person);
                resultList.add(person);
            }
        }
        return resultList;
    }

    @Override
    public Person updateById(UUID id, Person updatedPerson) {
        Optional<Person> person = findById(id);
        person.map((x) -> {
            updatedPerson.setId(id);
            updatedPerson.setColorCode(ColorMap.getOrdinalFromString(updatedPerson.getColorName()));
            personDao.save(updatedPerson);
            return updatedPerson;
        });
        return null;
    }

    @Override
    public void deleteById(UUID id) {
        personDao.deleteById(id);
    }

    private static void addColorString(Person person) {
        person.setColorName(ColorMap.getStringFromOrdinal(person.getColorCode()));
    }

    private static void addColorCode(Person person) {
        person.setColorCode(ColorMap.getOrdinalFromString(person.getColorName()));
    }
}