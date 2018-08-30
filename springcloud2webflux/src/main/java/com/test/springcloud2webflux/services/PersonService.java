package com.test.springcloud2webflux.services;

import java.util.List;

import com.test.springcloud2webflux.entities.Person;

public interface PersonService {
    public Boolean addPerson(Person person);

    public Boolean deletePerson(Person person);

    public Person updatePerson(Person person);

    public Person getPersonByID(String id);

    public List<Person> findByLastname(String lastname);

    public Iterable<Person> getAllPersons();
}