package kluszynski.example.jpa.exercises.repository;

import kluszynski.example.jpa.exercises.TestDataContants;
import kluszynski.example.jpa.exercises.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

import static kluszynski.example.jpa.exercises.TestDataContants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class PersonRepositoryIT {
    @Autowired
    private EntityManager entityManager;

    private PersonRepository personRepository;

    @BeforeEach
    void createRepository() {
        personRepository = new PersonRepository(entityManager);
    }


    @Test
    @Sql(CLEAN_DATABASE_SQL_PATH)
    void saveWithEmptyRepository() {
        Person person = TestDataContants.createJackWhite();
        personRepository.save(person);
        assertThat(person.getId()).isNotNull();

        Person personFromDatabase = personRepository.getById(person.getId());
        assertThat(personFromDatabase.getId()).isEqualTo(person.getId());
        assertThat(personFromDatabase)
                .hasFieldOrPropertyWithValue("name", JACK_WHITE.getName())
                .hasFieldOrPropertyWithValue("surname", JACK_WHITE.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", JACK_WHITE.getBirthDate());
    }

    @Test
    @Sql(CLEAN_DATABASE_SQL_PATH)
    void getByIdFromEmptyRepository() {
        assertThatThrownBy(() ->personRepository.getById(1000L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @Sql({CLEAN_DATABASE_SQL_PATH, JACK_WHITE_SQL_PATH})
    void getByIdFromNonEmptyRepositoryNonExistingId() {
        assertThatThrownBy(() ->personRepository.getById(1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @Sql(CLEAN_DATABASE_SQL_PATH)
    void getAllEmptyDatabase() {
        List<Person> persons = personRepository.getAll();

        assertThat(persons).isEmpty();
    }

    @Test
    @Sql({CLEAN_DATABASE_SQL_PATH, JACK_WHITE_SQL_PATH})
    void getByIdFromNonEmptyRepositoryValidId() {
        Person person = personRepository.getById(1000L);

        assertThat(person)
                .hasFieldOrPropertyWithValue("id", JACK_WHITE.getId())
                .hasFieldOrPropertyWithValue("name", JACK_WHITE.getName())
                .hasFieldOrPropertyWithValue("surname", JACK_WHITE.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", JACK_WHITE.getBirthDate());
    }

    @Test
    @Sql({CLEAN_DATABASE_SQL_PATH, JACK_WHITE_SQL_PATH})
    void getAllOnePersonInDatabase() {
        List<Person> persons = personRepository.getAll();

        assertThat(persons).hasSize(1);
        assertThat(persons).element(0)
                .hasFieldOrPropertyWithValue("id", JACK_WHITE.getId())
                .hasFieldOrPropertyWithValue("name", JACK_WHITE.getName())
                .hasFieldOrPropertyWithValue("surname", JACK_WHITE.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", JACK_WHITE.getBirthDate());
    }

    @Test
    @Sql({CLEAN_DATABASE_SQL_PATH, JACK_WHITE_SQL_PATH, JOHNY_BRAVO_SQL_PATH})
    void getAllTwoPersonsInDatabase() {
        List<Person> persons = personRepository.getAll();

        assertThat(persons).hasSize(2);
        assertThat(persons).element(0)
                .hasFieldOrPropertyWithValue("id", JACK_WHITE.getId())
                .hasFieldOrPropertyWithValue("name", JACK_WHITE.getName())
                .hasFieldOrPropertyWithValue("surname", JACK_WHITE.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", JACK_WHITE.getBirthDate());
        assertThat(persons).element(1)
                .hasFieldOrPropertyWithValue("id", JOHNY_BRAVO.getId())
                .hasFieldOrPropertyWithValue("name", JOHNY_BRAVO.getName())
                .hasFieldOrPropertyWithValue("surname", JOHNY_BRAVO.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", JOHNY_BRAVO.getBirthDate());
    }

    @Test
    @Sql(CLEAN_DATABASE_SQL_PATH)
    void updateWithPersonNotPresentInDatabase() {
        Person person = TestDataContants.createJackWhite();

        person = personRepository.update(person);

        Person personFromDatabase = personRepository.getById(person.getId());
        assertThat(personFromDatabase.getId()).isEqualTo(person.getId());
        assertThat(personFromDatabase)
                .hasFieldOrPropertyWithValue("name", JACK_WHITE.getName())
                .hasFieldOrPropertyWithValue("surname", JACK_WHITE.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", JACK_WHITE.getBirthDate());
    }

    @Test
    @Sql({CLEAN_DATABASE_SQL_PATH, JACK_WHITE_SQL_PATH})
    void updateWithPersonPresentInDatabase() {
        Person person = TestDataContants.createJackWhite();
        person.setName("Jackie");
        person.setSurname("Whitee");
        person.setBirthDate(LocalDate.parse("2022-01-01"));
        person.setId(1000L);

        personRepository.update(person);
        assertThat(person.getId()).isNotNull();

        Person personFromDatabase = personRepository.getById(person.getId());
        assertThat(personFromDatabase.getId()).isEqualTo(person.getId());
        assertThat(personFromDatabase)
                .hasFieldOrPropertyWithValue("id", JACK_WHITE.getId())
                .hasFieldOrPropertyWithValue("name", "Jackie")
                .hasFieldOrPropertyWithValue("surname", "Whitee")
                .hasFieldOrPropertyWithValue("birthDate", LocalDate.parse("2022-01-01"));
    }
}
