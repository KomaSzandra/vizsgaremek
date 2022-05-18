package hu.progmasters.conference.repository;

import hu.progmasters.conference.domain.Conference;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

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
}
