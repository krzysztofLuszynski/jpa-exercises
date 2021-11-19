package kluszynski.example.jpa.exercises.repository;

import kluszynski.example.jpa.exercises.domain.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class PersonRepository {
    private final EntityManager entityManager;

    public PersonRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Person person) {
        entityManager.persist(person);
    }

    public Person getById(Long id) {
        return entityManager.find(Person.class, id);
    }

    public Person update(Person person) {
        return entityManager.merge(person);
    }
}
