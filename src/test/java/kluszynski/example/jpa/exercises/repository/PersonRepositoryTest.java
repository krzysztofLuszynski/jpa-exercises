package kluszynski.example.jpa.exercises.repository;

import kluszynski.example.jpa.exercises.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersonRepositoryTest {
    @Autowired
    private EntityManager entityManager;

    private PersonRepository personRepository;

    @BeforeEach
    void createRepository() {
        personRepository = new PersonRepository(entityManager);
    }

    @Test
    @Sql("clean_database.sql")
    void getPersonFromEmptyRepository() {
        Person person = personRepository.getById(1000L);

        assertThat(person).isNull();
    }

    @Test
    @Sql({"clean_database.sql", "jack_white.sql"})
    void getPersonFromNonEmptyRepositoryNonExistingId() {
        Person person = personRepository.getById(1L);

        assertThat(person).isNull();
    }

    @Test
    @Sql({"clean_database.sql", "jack_white.sql"})
    void getPersonFromNonEmptyRepositoryValidId() {
        Person person = personRepository.getById(1000L);

        assertThat(person)
                .hasFieldOrPropertyWithValue("name", "Jack")
                .hasFieldOrPropertyWithValue("surname", "White")
                .hasFieldOrPropertyWithValue("birthDate", LocalDate.parse("2010-01-01"));
    }
}
