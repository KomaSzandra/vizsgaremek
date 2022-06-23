package hu.progmasters.conference.repository;

import hu.progmasters.conference.domain.AcademicRank;
import hu.progmasters.conference.domain.Participant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ParticipantRepositoryTest {

    @Autowired
    ParticipantRepository repository;

    @Test
    @Transactional
    void testSave() {
        Participant participantToSave = new Participant();
        participantToSave.setName("Dr. B. Bob");
        participantToSave.setDateOfBirth(LocalDate.of(1990, Month.APRIL, 20));
        participantToSave.setAcademicRank(AcademicRank.CANDIDATE);
        participantToSave.setInstitution("BMX");
        participantToSave.setEmail("bobDr@bmx.hu");

        Optional<Participant> expectedNull = repository.findById(2);
        assertTrue(expectedNull.isEmpty());

        Participant saved = repository.save(participantToSave);
        assertEquals(2, saved.getId());
        assertEquals("Dr. B. Bob", saved.getName());
        assertEquals(LocalDate.of(1990, Month.APRIL, 20), saved.getDateOfBirth());
        assertEquals("BMX", saved.getInstitution());
        assertEquals("bobDr@bmx.hu", saved.getEmail());
        assertEquals(AcademicRank.CANDIDATE, saved.getAcademicRank());

        Optional<Participant> byId = repository.findById(2);
        assumeThat(byId.isPresent());
        assertEquals(2, byId.get().getId());
        assertEquals("Dr. B. Bob", byId.get().getName());
        assertEquals("BMX", byId.get().getInstitution());
        assertEquals(LocalDate.of(1990, Month.APRIL, 20), byId.get().getDateOfBirth());
        assertEquals("bobDr@bmx.hu", byId.get().getEmail());
    }
}
