package kluszynski.example.jpa.exercises.toomanyqueries.repository;

import kluszynski.example.jpa.exercises.toomanyqueries.domain.Hero;
import kluszynski.example.jpa.exercises.toomanyqueries.domain.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;

import static kluszynski.example.jpa.exercises.toomanyqueries.TestDataContants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@DataJpaTest
public class HeroRepositoryIT {
    @Autowired
    private EntityManager entityManager;

    private HeroRepository heroRepository;

    @BeforeEach
    void createRepository() {
        heroRepository = new HeroRepository(entityManager);
    }

    @Test
    @Sql(CLEAN_DATABASE_SQL_PATH)
    void getByIdFromEmptyRepository() {
        assertThatThrownBy(() -> heroRepository.getById(1000L))
                .isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    @Sql({CLEAN_DATABASE_SQL_PATH, INITIAL_DATA_SQL_PATH})
    void getByIdFromNonEmptyRepositoryNonExistingId() {
        assertThatThrownBy(() -> heroRepository.getById(1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @Sql({CLEAN_DATABASE_SQL_PATH, INITIAL_DATA_SQL_PATH})
    void getByIdFromNonEmptyRepositoryValidId() {
        Hero person = heroRepository.getById(1000L);

        assertThat(person)
                .hasFieldOrPropertyWithValue("id", JACK_WHITE.getId())
                .hasFieldOrPropertyWithValue("name", JACK_WHITE.getName())
                .hasFieldOrPropertyWithValue("surname", JACK_WHITE.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", JACK_WHITE.getBirthDate());

        log.debug("Loaded only heroes with lazy loading, items not loaded !");

        assertThat(person.getItems())
                .containsOnly(JACK_WHITE.getItems().toArray(new Item[0]));
    }

    @Test
    @Sql(CLEAN_DATABASE_SQL_PATH)
    void getAllEmptyDatabase() {
        List<Hero> heroes = heroRepository.getAll();

        assertThat(heroes).isEmpty();
    }

    @Test
    @Sql({CLEAN_DATABASE_SQL_PATH, INITIAL_DATA_SQL_PATH})
    void getAllFourHerosInDatabase() {
        List<Hero> heroes = heroRepository.getAll();

        assertThat(heroes).hasSize(4);
        assertThat(heroes).element(0)
                .hasFieldOrPropertyWithValue("id", JACK_WHITE.getId())
                .hasFieldOrPropertyWithValue("name", JACK_WHITE.getName())
                .hasFieldOrPropertyWithValue("surname", JACK_WHITE.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", JACK_WHITE.getBirthDate());
        log.debug("Loaded only heroes with lazy loading, items not loaded !");
        assertThat(heroes.get(0).getItems())
                .containsOnly(JACK_WHITE.getItems().toArray(new Item[0]));

        assertThat(heroes).element(1)
                .hasFieldOrPropertyWithValue("id", JOHNY_BRAVO.getId())
                .hasFieldOrPropertyWithValue("name", JOHNY_BRAVO.getName())
                .hasFieldOrPropertyWithValue("surname", JOHNY_BRAVO.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", JOHNY_BRAVO.getBirthDate());
        log.debug("Loaded only heroes with lazy loading, items not loaded !");
        assertThat(heroes.get(1).getItems())
                .containsOnly(JOHNY_BRAVO.getItems().toArray(new Item[0]));

        assertThat(heroes).element(2)
                .hasFieldOrPropertyWithValue("id", BARBARA_STRAISAND.getId())
                .hasFieldOrPropertyWithValue("name", BARBARA_STRAISAND.getName())
                .hasFieldOrPropertyWithValue("surname", BARBARA_STRAISAND.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", BARBARA_STRAISAND.getBirthDate());
        log.debug("Loaded only heroes with lazy loading, items not loaded !");
        assertThat(heroes.get(2).getItems())
                .containsOnly(BARBARA_STRAISAND.getItems().toArray(new Item[0]));

        assertThat(heroes).element(3)
                .hasFieldOrPropertyWithValue("id", TOM_GOODY.getId())
                .hasFieldOrPropertyWithValue("name", TOM_GOODY.getName())
                .hasFieldOrPropertyWithValue("surname", TOM_GOODY.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", TOM_GOODY.getBirthDate());
        log.debug("Loaded only heroes with lazy loading, items not loaded !");
        assertThat(heroes.get(3).getItems())
                .containsOnly(TOM_GOODY.getItems().toArray(new Item[0]));
    }

    @Test
    @Sql({CLEAN_DATABASE_SQL_PATH, INITIAL_DATA_SQL_PATH})
    void getAllFixedFourHeroesInDatabase() {
        List<Hero> heroes = heroRepository.getAllFixed();

        assertThat(heroes).hasSize(3);
        assertThat(heroes).element(0)
                .hasFieldOrPropertyWithValue("id", JACK_WHITE.getId())
                .hasFieldOrPropertyWithValue("name", JACK_WHITE.getName())
                .hasFieldOrPropertyWithValue("surname", JACK_WHITE.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", JACK_WHITE.getBirthDate());
        log.debug("Loaded only heroes with lazy loading, items not loaded !");
        assertThat(heroes.get(0).getItems())
                .containsOnly(JACK_WHITE.getItems().toArray(new Item[0]));

        assertThat(heroes).element(1)
                .hasFieldOrPropertyWithValue("id", JOHNY_BRAVO.getId())
                .hasFieldOrPropertyWithValue("name", JOHNY_BRAVO.getName())
                .hasFieldOrPropertyWithValue("surname", JOHNY_BRAVO.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", JOHNY_BRAVO.getBirthDate());
        log.debug("Loaded only heroes with lazy loading, items not loaded !");
        assertThat(heroes.get(1).getItems())
                .containsOnly(JOHNY_BRAVO.getItems().toArray(new Item[0]));

        assertThat(heroes).element(2)
                .hasFieldOrPropertyWithValue("id", TOM_GOODY.getId())
                .hasFieldOrPropertyWithValue("name", TOM_GOODY.getName())
                .hasFieldOrPropertyWithValue("surname", TOM_GOODY.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", TOM_GOODY.getBirthDate());
        log.debug("Loaded only heroes with lazy loading, items not loaded !");
        assertThat(heroes.get(2).getItems())
                .containsOnly(TOM_GOODY.getItems().toArray(new Item[0]));
    }
}
