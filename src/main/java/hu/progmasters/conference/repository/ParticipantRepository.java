package hu.progmasters.conference.repository;

import hu.progmasters.conference.domain.Participant;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ParticipantRepository {

    private Map<Integer, Participant> participants = new HashMap<>();
    private Integer nextId = 1;


    public Participant saveParticipant(Participant toSave) {
        toSave.setId(nextId);
        participants.put(nextId, toSave);
        nextId++;
        return toSave;
    }
}
