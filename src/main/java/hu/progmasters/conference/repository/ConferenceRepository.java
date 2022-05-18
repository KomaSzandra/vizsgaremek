package hu.progmasters.conference.repository;

import hu.progmasters.conference.domain.Conference;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ConferenceRepository {

    private Map<Integer, Conference> conferences = new HashMap<>();
    private Integer nextId = 1;


    public Conference saveConference(Conference conferenceToSave) {
        conferenceToSave.setId(nextId);
        conferences.put(nextId, conferenceToSave);
        nextId++;
        return conferenceToSave;
    }

    public List<Conference> listAllConferences() {
        return conferences.values().stream()
                .sorted(Comparator.comparing(Conference::getLocalDate))
                .collect(Collectors.toList());
    }

    public Optional<Conference> findById(Integer id) {
        return conferences.containsKey(id)
                ? Optional.of(conferences.get(id))
                : Optional.empty();
    }

    public void deleteConferenceById(Integer id) {
        conferences.remove(id);
    }
}
