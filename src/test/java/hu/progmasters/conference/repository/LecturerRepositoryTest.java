package hu.progmasters.conference.repository;

import hu.progmasters.conference.domain.AcademicRank;
import hu.progmasters.conference.domain.Lecturer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LecturerRepositoryTest {

    @Autowired
    LecturerRepository lecturerRepository;

    @Test
    @Transactional
    void testSave() {
        Lecturer lecturerToSave = new Lecturer();
        lecturerToSave.setName("Dr YU");
        lecturerToSave.setAcademicRank(AcademicRank.ASSISTANT_RESEARCH_FELLOW);
        lecturerToSave.setInstitution("BMX");
        lecturerToSave.setEmail("yuDr@bmx.hu");
        lecturerToSave.setDateOfBirth(LocalDate.of(1984, Month.APRIL, 20));

        Lecturer saved = lecturerRepository.save(lecturerToSave);
        assertEquals(5, saved.getId());
        assertEquals("Dr YU", saved.getName());
        assertEquals("yuDr@bmx.hu", saved.getEmail());
        assertEquals(LocalDate.of(1984, Month.APRIL, 20), saved.getDateOfBirth());
        assertEquals(AcademicRank.ASSISTANT_RESEARCH_FELLOW, saved.getAcademicRank());
        assertEquals("BMX", saved.getInstitution());

        Optional<Lecturer> byId = lecturerRepository.findById(5);
        assumeThat(byId.isPresent());
        assertEquals(5, byId.get().getId());
        assertEquals("Dr YU", byId.get().getName());
        assertEquals("yuDr@bmx.hu", byId.get().getEmail());
        assertEquals(LocalDate.of(1984, Month.APRIL, 20), byId.get().getDateOfBirth());
        assertEquals(AcademicRank.ASSISTANT_RESEARCH_FELLOW, byId.get().getAcademicRank());
        assertEquals("BMX", byId.get().getInstitution());
    }
}
