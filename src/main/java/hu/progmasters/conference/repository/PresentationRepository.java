package hu.progmasters.conference.repository;

import hu.progmasters.conference.domain.Presentation;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PresentationRepository {

    private Map<Integer, Presentation> presentations = new HashMap<>();
    private Integer nextId = 1;


    public Presentation save(Presentation presentationToSave) {
        presentationToSave.setId(nextId);
        presentations.put(nextId, presentationToSave);
        nextId++;
        return presentationToSave;
    }

    public List<Presentation> findAll() {
        return presentations.values().stream()
                .sorted(Comparator.comparing(Presentation::getStartTime))
                .collect(Collectors.toList());
    }

    public Optional<Presentation> findPresentationById(Integer id) {
        return presentations.containsKey(id)
                ? Optional.of(presentations.get(id))
                : Optional.empty();
    }
}
