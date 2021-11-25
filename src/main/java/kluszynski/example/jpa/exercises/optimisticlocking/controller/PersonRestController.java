package kluszynski.example.jpa.exercises.optimisticlocking.controller;

import kluszynski.example.jpa.exercises.optimisticlocking.dto.PersonDto;
import kluszynski.example.jpa.exercises.optimisticlocking.domain.Person;
import kluszynski.example.jpa.exercises.optimisticlocking.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PersonRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRestController.class);

    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/persons")
    public ResponseEntity<PersonDto>  createPerson(@RequestBody PersonDto personDto) {
        LOGGER.info("Adding person {}", personDto);

        Person person = PersonDto.getPerson(personDto);
        personRepository.save(person);

        return new ResponseEntity<>(personDto, HttpStatus.CREATED);
    }

    @GetMapping("/persons")
    public List<PersonDto> getAllPersons() {
        LOGGER.info("Getting all persons");

        return personRepository.getAll().stream()
                .map(PersonDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/persons/{id}")
    public PersonDto getPerson(@PathVariable Long id) {
        LOGGER.info("get person with id {}", id);

        Person person = personRepository.getById(id);
        return new PersonDto(person);
    }

    @PutMapping("/persons/{id}")
    public Person updatePerson(@RequestBody Person person, @PathVariable Long id) {
        LOGGER.info("update person with id {}", id);

        final Person oldPerson = personRepository.getById(id);
        oldPerson.setName(person.getName());
        oldPerson.setSurname(person.getSurname());
        oldPerson.setBirthDate(person.getBirthDate());

        // returns 200 because body is returned
        return personRepository.update(oldPerson);
    }

    @ExceptionHandler({EntityNotFoundException.class, EmptyResultDataAccessException.class})
    void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    void handleConcurrentRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
