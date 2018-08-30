package com.test.springcloud2webflux.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test.springcloud2webflux.entities.Person;
import com.test.springcloud2webflux.services.PersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/person")
public class PersonController {

    @Autowired
	PersonService personService;

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @GetMapping("/index")
    public Flux<Person> getAll(String lastName) {
        return Flux.fromIterable((personService.findByLastname(lastName).stream())
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public Mono<Person> getPerson(@PathVariable String id) {
        return Mono.justOrEmpty(personService.getPersonByID(id));
    }

    @PostMapping("/post")
    public Mono<ResponseEntity<String>> postPerson(@RequestBody Person person) {
        personService.addPerson(person);
        logger.info("########### POST:" + person);
        return Mono.just(new ResponseEntity<>("Post Successfully!", HttpStatus.CREATED));
    }

    @PutMapping("/put/{id}")
    public Mono<ResponseEntity<Person>> putPerson(@PathVariable String id, @RequestBody Person person) {
        person.setId(id);
        personService.updatePerson(person);
        System.out.println("########### PUT:" + person);
        return Mono.just(new ResponseEntity<>(person, HttpStatus.CREATED));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> deleteMethod(@PathVariable String id) {
        Person person = personService.getPersonByID(id);
        personService.deletePerson(person);
        return Mono.just(new ResponseEntity<>("Delete Successfully!", HttpStatus.ACCEPTED));
    }
}