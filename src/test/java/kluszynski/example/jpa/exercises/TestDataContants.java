package kluszynski.example.jpa.exercises;

import kluszynski.example.jpa.exercises.domain.Person;

import java.time.LocalDate;

public final class TestDataContants {
    public static final String CLEAN_DATABASE_SQL_PATH = "/kluszynski/example/jpa/exercises/clean_database.sql";
    public static final String JACK_WHITE_SQL_PATH = "/kluszynski/example/jpa/exercises/jack_white.sql";
    public static final String JOHNY_BRAVO_SQL_PATH = "/kluszynski/example/jpa/exercises/johny_bravo.sql";

    public static final Person JACK_WHITE = new Person("Jack", "White", LocalDate.parse("2010-01-01"));
    public static final Person JOHNY_BRAVO = new Person("Johny", "Bravo", LocalDate.parse("2000-02-02"));

    static {
        JACK_WHITE.setId(1000L);
        JOHNY_BRAVO.setId(1001L);
    }

    public static Person createJackWhite() {
        return new Person(JACK_WHITE.getName(), JACK_WHITE.getSurname(), JACK_WHITE.getBirthDate());
    }
}
