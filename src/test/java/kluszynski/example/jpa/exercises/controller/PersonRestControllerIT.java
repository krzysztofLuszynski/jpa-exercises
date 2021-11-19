package kluszynski.example.jpa.exercises.controller;

import kluszynski.example.jpa.exercises.TestDataContants;
import kluszynski.example.jpa.exercises.domain.Person;
import kluszynski.example.jpa.exercises.dto.PersonDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import static kluszynski.example.jpa.exercises.TestDataContants.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonRestControllerIT {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Sql(CLEAN_DATABASE_SQL_PATH)
    void createPerson() throws MalformedURLException {
        final PersonDto person = new PersonDto(TestDataContants.createJackWhite());

        final ResponseEntity<PersonDto> postResponse = restTemplate.postForEntity(
                getServiceUrl("persons"), person, PersonDto.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody())
                .hasFieldOrPropertyWithValue("name", JACK_WHITE.getName())
                .hasFieldOrPropertyWithValue("surname", JACK_WHITE.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", JACK_WHITE.getBirthDate());
    }

    @Test
    @Sql(CLEAN_DATABASE_SQL_PATH)
    void getAllPersonsEmptyDatabase() throws Exception {
        final ResponseEntity<PersonDto[]> getAllResponse = restTemplate.getForEntity(
                getServiceUrl("persons"), PersonDto[].class);

        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getAllResponse.getBody()).isEmpty();
    }

    @Test
    @Sql({CLEAN_DATABASE_SQL_PATH, JACK_WHITE_SQL_PATH, JOHNY_BRAVO_SQL_PATH})
    void getAllPersonsTwoPersons() throws Exception {
        final ResponseEntity<PersonDto[]> getAllResponse = restTemplate.getForEntity(
                getServiceUrl("persons"), PersonDto[].class);

        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getAllResponse.getBody()).hasSize(2);

        assertThat(Objects.requireNonNull(getAllResponse.getBody())[0])
                .hasFieldOrPropertyWithValue("name", JACK_WHITE.getName())
                .hasFieldOrPropertyWithValue("surname", JACK_WHITE.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", JACK_WHITE.getBirthDate());

        assertThat(Objects.requireNonNull(getAllResponse.getBody())[1])
                .hasFieldOrPropertyWithValue("name", JOHNY_BRAVO.getName())
                .hasFieldOrPropertyWithValue("surname", JOHNY_BRAVO.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", JOHNY_BRAVO.getBirthDate());
    }

    @Test
    @Sql(CLEAN_DATABASE_SQL_PATH)
    void getPersonByIdNonExisting() throws MalformedURLException {
        final ResponseEntity<PersonDto> response = restTemplate.getForEntity(
                getServiceUrl("persons/1002"), PersonDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody())
                .hasFieldOrPropertyWithValue("name", null)
                .hasFieldOrPropertyWithValue("surname", null)
                .hasFieldOrPropertyWithValue("birthDate", null);
    }

    @Test
    @Sql({CLEAN_DATABASE_SQL_PATH, JACK_WHITE_SQL_PATH})
    void getPersonById() throws MalformedURLException {
        final ResponseEntity<PersonDto> response = restTemplate.getForEntity(
                getServiceUrl("persons/" + JACK_WHITE.getId()), PersonDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .hasFieldOrPropertyWithValue("name", JACK_WHITE.getName())
                .hasFieldOrPropertyWithValue("surname", JACK_WHITE.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", JACK_WHITE.getBirthDate());
    }

    @Test
    @Sql(CLEAN_DATABASE_SQL_PATH)
    void updatePersonByIdNonExistingId() throws MalformedURLException {
        final HttpEntity<PersonDto> request = new HttpEntity<>(new PersonDto(JOHNY_BRAVO));

        final ResponseEntity<Person> putResponse =
                restTemplate.exchange(getServiceUrl("persons/1000"),
                        HttpMethod.PUT, request, Person.class);

        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Sql({CLEAN_DATABASE_SQL_PATH, JACK_WHITE_SQL_PATH})
    void updatePersonById() throws MalformedURLException {
        final HttpEntity<PersonDto> request = new HttpEntity<>(new PersonDto(JOHNY_BRAVO));

        final ResponseEntity<Person> putResponse =
                restTemplate.exchange(getServiceUrl("persons/1000"),
                        HttpMethod.PUT, request, Person.class);

        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(putResponse.getBody())
                .hasFieldOrPropertyWithValue("name", JOHNY_BRAVO.getName())
                .hasFieldOrPropertyWithValue("surname", JOHNY_BRAVO.getSurname())
                .hasFieldOrPropertyWithValue("birthDate", JOHNY_BRAVO.getBirthDate());
    }

    private String getServiceUrl(final String endpointPath) throws MalformedURLException {
        return new URL("http://localhost:" + port + "/jpa-exercises/" + endpointPath).toString();
    }
}
