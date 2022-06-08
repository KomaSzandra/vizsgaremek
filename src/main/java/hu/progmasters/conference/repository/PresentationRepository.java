package hu.progmasters.conference.repository;

import hu.progmasters.conference.domain.Presentation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class PresentationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Presentation save(Presentation toSave) {
        entityManager.persist(toSave);
        return toSave;
    }

    public List<Presentation> findAll() {
        return entityManager.createQuery("SELECT p FROM Presentation p", Presentation.class)
                .getResultList();
    }

    public Optional<Presentation> findById(Integer presentationId) {
        return Optional.ofNullable(entityManager.find(Presentation.class, presentationId));
    }

    public Presentation findByTitle(String titleToFind) {
        TypedQuery<Presentation> query = entityManager.createQuery("SELECT p FROM Presentation p " +
                "WHERE p.title = :titleParam", Presentation.class);
        query.setParameter("titleParam", titleToFind);
        Presentation presentationWithTitle = query.getSingleResult();
        return presentationWithTitle;
    }

    public Presentation update(Presentation toUpdate) {
        return entityManager.merge(toUpdate);
    }

    public void delete(Presentation toDelete) {
        entityManager.remove(toDelete);
    }

    public void flush() {
        entityManager.flush();
    }
}
