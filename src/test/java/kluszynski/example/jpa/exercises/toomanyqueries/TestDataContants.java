package kluszynski.example.jpa.exercises.toomanyqueries;

import kluszynski.example.jpa.exercises.optimisticlocking.domain.Person;
import kluszynski.example.jpa.exercises.toomanyqueries.domain.Hero;
import kluszynski.example.jpa.exercises.toomanyqueries.domain.Item;

import java.time.LocalDate;
import java.util.Arrays;

public final class TestDataContants {
    public static final String CLEAN_DATABASE_SQL_PATH = "/kluszynski/example/jpa/exercises/toomanyqueries/clean_database.sql";
    public static final String INITIAL_DATA_SQL_PATH = "/kluszynski/example/jpa/exercises/toomanyqueries/initial_data.sql";

    public static final Hero JACK_WHITE = new Hero("Jack", "White", LocalDate.parse("2010-01-01"));
    public static final Hero JOHNY_BRAVO = new Hero("Johny", "Bravo", LocalDate.parse("2000-02-02"));
    public static final Hero BARBARA_STRAISAND = new Hero("Barbara", "Straisand", LocalDate.parse("1980-11-26"));
    public static final Hero TOM_GOODY = new Hero("Tom", "Goody", LocalDate.parse("1992-03-13"));

    public static final Item JACK_WHITE_GUN = new Item("gun");
    public static final Item JACK_WHITE_HELM = new Item("helm");
    public static final Item JACK_WHITE_BOOTS = new Item("boots");
    public static final Item JACK_WHITE_ARMOR = new Item("armor");
    public static final Item JACK_WHITE_GOLD = new Item("gold");

    public static final Item JOHNY_BRAVO_SWORD = new Item("sword");
    public static final Item JOHNY_BRAVO_SHIELD = new Item("shield");
    public static final Item JOHNY_BRAVO_BOOTS = new Item("boots");

    public static final Item TOM_GOODY_MICROPHONE = new Item("microphone");
    public static final Item TOM_GOODY_SHOES = new Item("shoes");

    static {
        JACK_WHITE.setId(1000L);
        JACK_WHITE_GUN.setId(2000L);
        JACK_WHITE_HELM.setId(2001L);
        JACK_WHITE_BOOTS.setId(2002L);
        JACK_WHITE_ARMOR.setId(2003L);
        JACK_WHITE_GOLD.setId(2004L);
        JACK_WHITE.getItems().addAll(Arrays.asList(JACK_WHITE_GUN, JACK_WHITE_HELM, JACK_WHITE_BOOTS, JACK_WHITE_ARMOR, JACK_WHITE_GOLD));

        JOHNY_BRAVO.setId(1001L);
        JOHNY_BRAVO_SWORD.setId(2005L);
        JOHNY_BRAVO_SHIELD.setId(2006L);
        JOHNY_BRAVO_BOOTS.setId(2007L);
        JOHNY_BRAVO.getItems().addAll(Arrays.asList(JOHNY_BRAVO_SWORD, JOHNY_BRAVO_SHIELD, JOHNY_BRAVO_BOOTS));

        BARBARA_STRAISAND.setId(1002L);

        TOM_GOODY.setId(1003L);
        TOM_GOODY_MICROPHONE.setId(2008L);
        TOM_GOODY_SHOES.setId(2009L);
        TOM_GOODY.getItems().addAll(Arrays.asList(TOM_GOODY_MICROPHONE, TOM_GOODY_SHOES));
    }

    public static Person createJackWhite() {
        return new Person(JACK_WHITE.getName(), JACK_WHITE.getSurname(), JACK_WHITE.getBirthDate());
    }
}
