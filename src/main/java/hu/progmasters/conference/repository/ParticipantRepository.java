package hu.progmasters.conference.repository;

import hu.progmasters.conference.domain.Lecturer;
import hu.progmasters.conference.domain.Participant;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class ParticipantRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public Participant save(Participant toSave) {
        entityManager.persist(toSave);
        return toSave;
    }

    public Optional<Participant> findParticipantById(Integer participantId) {
        return Optional.ofNullable(entityManager.find(Participant.class, participantId));
    }

    public void deleteParticipant(Participant participant) {
        entityManager.remove(participant);
    }

    public List<Participant> findAll() {
        return entityManager.createQuery("SELECT p FROM Participant p", Participant.class)
                .getResultList();
    }

    public Participant update(Participant toUpdate) {
        Participant merged = entityManager.merge(toUpdate);
        return merged;
    }
}
