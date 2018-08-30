package com.test.springcloud2webflux.repositories;

import org.springframework.data.repository.CrudRepository;

import com.test.springcloud2webflux.entities.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, String> {

    List<Person> findByLastname(String lastname);

    List<Person> findByFirstnameAndLastname(String firstname, String lastname);

    List<Person> findByFirstnameOrLastname(String firstname, String lastname);

    List<Person> findByAddress_City(String city);
}