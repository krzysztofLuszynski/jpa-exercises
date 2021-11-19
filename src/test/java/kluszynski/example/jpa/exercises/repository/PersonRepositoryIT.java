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
public class PersonRepositoryIT {
    private static final Person JACK_WHITE = new Person("Jack", "White", LocalDate.parse("2010-01-01"));

    @Autowired
    private EntityManager entityManager;

    private PersonRepository personRepository;

    @BeforeEach
    void createRepository() {
        personRepository = new PersonRepository(entityManager);
    }


    @Test
    @Sql("clean_database.sql")
    void saveWithEmptyRepository() {
        Person person = createJackWhite();
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
    @Sql("clean_database.sql")
    void getByIdFromEmptyRepository() {
        Person person = personRepository.getById(1000L);

        assertThat(person).isNull();
    }

    @Test
    @Sql({"clean_database.sql", "jack_white.sql"})
    void getByIdFromNonEmptyRepositoryNonExistingId() {
        Person person = personRepository.getById(1L);

        assertThat(person).isNull();
    }

    @Test
    @Sql({"clean_database.sql", "jack_white.sql"})
    void getByIdFromNonEmptyRepositoryValidId() {
        Person person = personRepository.getById(1000L);

        assertThat(person)
                .hasFieldOrPropertyWithValue("name", JACK_WHITE.getName())
                .hasFieldOrPropertyWithValue("surname", JACK_WHITE.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", JACK_WHITE.getBirthDate());
    }

    @Test
    @Sql("clean_database.sql")
    void updateWithPersonNotPresentInDatabase() {
        Person person = createJackWhite();

        person = personRepository.update(person);

        Person personFromDatabase = personRepository.getById(person.getId());
        assertThat(personFromDatabase.getId()).isEqualTo(person.getId());
        assertThat(personFromDatabase)
                .hasFieldOrPropertyWithValue("name", JACK_WHITE.getName())
                .hasFieldOrPropertyWithValue("surname", JACK_WHITE.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", JACK_WHITE.getBirthDate());
    }

    @Test
    @Sql({"clean_database.sql", "jack_white.sql"})
    void updateWithPersonPresentInDatabase() {
        Person person = createJackWhite();
        person.setName("Jackie");
        person.setSurname("Whitee");
        person.setBirthDate(LocalDate.parse("2022-01-01"));
        person.setId(1000L);

        personRepository.update(person);
        assertThat(person.getId()).isNotNull();

        Person personFromDatabase = personRepository.getById(person.getId());
        assertThat(personFromDatabase.getId()).isEqualTo(person.getId());
        assertThat(personFromDatabase)
                .hasFieldOrPropertyWithValue("name", "Jackie")
                .hasFieldOrPropertyWithValue("surname", "Whitee")
                .hasFieldOrPropertyWithValue("birthDate", LocalDate.parse("2022-01-01"));
    }

    private Person createJackWhite() {
        return new Person(JACK_WHITE.getName(), JACK_WHITE.getSurname(), JACK_WHITE.getBirthDate());
    }

}
