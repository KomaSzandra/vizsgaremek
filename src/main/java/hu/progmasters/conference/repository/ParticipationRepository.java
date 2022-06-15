package hu.progmasters.conference.repository;

import hu.progmasters.conference.domain.Participant;
import hu.progmasters.conference.domain.Participation;
import hu.progmasters.conference.domain.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class ParticipationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Participation save(Participation toSave) {
        entityManager.persist(toSave);
        return toSave;
    }

    public Optional<Participation> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Participation.class, id));
    }

    public List<Participation> findAll() {
        return entityManager.createQuery("SELECT p FROM Participation p", Participation.class).getResultList();
    }

    public boolean hasRegistration(Participant participant, Presentation presentation) {
        return !entityManager.createQuery("SELECT TRUE FROM Participation p " +
                                "WHERE p.participant = :participantParam AND p.presentation = :presentationParam",
                        Boolean.class)
                .setParameter("participantParam", participant)
                .setParameter("presentationParam", presentation)
                .getResultList().isEmpty();
    }

}
