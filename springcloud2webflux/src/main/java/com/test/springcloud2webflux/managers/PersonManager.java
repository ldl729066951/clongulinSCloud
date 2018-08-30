package com.test.springcloud2webflux.managers;

import java.util.List;

import com.test.springcloud2webflux.entities.Person;
import com.test.springcloud2webflux.repositories.PersonRepository;
import com.test.springcloud2webflux.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonManager implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public Boolean addPerson(Person person) {
        personRepository.save(person);

        return Boolean.TRUE;
    }

    @Override
    public Boolean deletePerson(Person person) {
        personRepository.delete(person);

        return Boolean.TRUE;
    }

    public Person getPersonByID(String id) {

        return personRepository.findById(id).orElse(null);
    }

    @Override
    public Person updatePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public List<Person> findByLastname(String lastname) {
        return personRepository.findByLastname(lastname);
    }

    @Override
    public Iterable<Person> getAllPersons() {
        return personRepository.findAll();
    }
}
