package kluszynski.example.jpa.exercises.toomanyqueries.repository;

import kluszynski.example.jpa.exercises.toomanyqueries.domain.Hero;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class HeroRepository {
    private final EntityManager entityManager;

    public HeroRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Hero> getAll() {
        TypedQuery<Hero> query = entityManager.createQuery("SELECT h FROM Hero h ORDER by h.id", Hero.class);
        return query.getResultList();
    }

    public List<Hero> getAllFixed() {
        TypedQuery<Hero> query = entityManager.createQuery("SELECT DISTINCT h FROM Hero h JOIN FETCH h.items i ORDER by h.id ", Hero.class);
        return query.getResultList();
    }

    public Hero getById(Long id) {
        Hero hero = entityManager.find(Hero.class, id);

        if (hero == null) {
            throw new EntityNotFoundException();
        }

        return hero;
    }
}
