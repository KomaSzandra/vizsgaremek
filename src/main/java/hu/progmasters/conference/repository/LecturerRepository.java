package hu.progmasters.conference.repository;

import hu.progmasters.conference.domain.Lecturer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class LecturerRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public Lecturer save(Lecturer toSave) {
        entityManager.persist(toSave);
        return toSave;
    }

    public List<Lecturer> findAll() {
        return entityManager.createQuery("SELECT l FROM Lecturer l ORDER BY l.id asc", Lecturer.class)
                .getResultList();
    }

    public Optional<Lecturer> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Lecturer.class, id));
    }

    public Lecturer findByName(String name) {
        TypedQuery<Lecturer> query = entityManager.createQuery("SELECT l FROM Lecturer l WHERE l.name = :nameParam", Lecturer.class);
        query.setParameter("nameParam", name);
        return query.getSingleResult();
    }

    public void deleteLecturer(Lecturer toDelete) {
        entityManager.remove(toDelete);
    }

    public void flush() {
        entityManager.flush();
    }
}
