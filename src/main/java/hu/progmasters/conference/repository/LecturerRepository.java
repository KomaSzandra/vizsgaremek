package hu.progmasters.conference.repository;

import hu.progmasters.conference.domain.Lecturer;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class LecturerRepository {

    private Map<Integer, Lecturer> lecturers = new HashMap<>();
    private Integer nextId = 1;

    public Lecturer save(Lecturer toSave) {
        toSave.setId(nextId);
        lecturers.put(nextId, toSave);
        nextId++;
        return toSave;
    }

    public Optional<Lecturer> findById(Integer id) {
        return lecturers.containsKey(id) ? Optional.of(lecturers.get(id)) : Optional.empty();
    }

    public List<Lecturer> findAll() {
        return lecturers.values().stream()
                .sorted(Comparator.comparing(Lecturer::getInstitution))
                .collect(Collectors.toList());
    }

    public void deleteById(Integer lecturerId) {
        lecturers.remove(lecturerId);
    }
}
