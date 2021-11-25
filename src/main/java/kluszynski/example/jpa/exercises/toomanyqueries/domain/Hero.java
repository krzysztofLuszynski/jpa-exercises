package kluszynski.example.jpa.exercises.toomanyqueries.domain;

import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@ToString
public class Hero {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String surname;

    private LocalDate birthDate;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "hero_id")
    private final Set<Item> items = new LinkedHashSet<>();

    Hero() {
    }

    public Hero(String name, String surname, LocalDate birthDate) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Set<Item> getItems() {
        return items;
    }
}
