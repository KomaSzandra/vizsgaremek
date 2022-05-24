package hu.progmasters.conference.repository;

import hu.progmasters.conference.domain.Participant;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
}
