package kluszynski.example.jpa.exercises.dto;

import kluszynski.example.jpa.exercises.domain.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PersonDto {
    String name;
    String surname;
    LocalDate birthDate;

    public PersonDto(Person person) {
        this.name = person.getName();
        this.surname = person.getSurname();
        this.birthDate = person.getBirthDate();
    }

    public static Person getPerson(PersonDto personDto) {
        return new Person(personDto.getName(), personDto.getSurname(), personDto.getBirthDate());
    }
}
