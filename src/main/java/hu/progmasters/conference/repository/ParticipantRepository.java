package hu.progmasters.conference.repository;

import hu.progmasters.conference.domain.Participant;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<Participant> findAll() {
        return participants.values().stream()
                .sorted(Comparator.comparing(Participant::getId))
                .collect(Collectors.toList());
    }

    public Optional<Participant> findById(Integer id) {
        return participants.containsKey(id) ? Optional.of(participants.get(id)) : Optional.empty();
    }

    public void deleteById(Integer participantId) {
        participants.remove(participantId);
    }
}
