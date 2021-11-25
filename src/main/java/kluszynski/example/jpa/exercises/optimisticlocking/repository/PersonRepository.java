package kluszynski.example.jpa.exercises.optimisticlocking.repository;

import kluszynski.example.jpa.exercises.optimisticlocking.domain.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PersonRepository {
    private final EntityManager entityManager;

    public PersonRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Person person) {
        entityManager.persist(person);
    }

    public Person getById(Long id) {
        Person person = entityManager.find(Person.class, id);

        if (person == null) {
            throw new EntityNotFoundException();
        }

        return person;
    }

    public List<Person> getAll() {
        TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p ORDER by p.id", Person.class);
        return query.getResultList();
    }

    public Person update(Person person) {
        return entityManager.merge(person);
    }
}
