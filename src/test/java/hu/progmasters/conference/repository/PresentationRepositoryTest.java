package hu.progmasters.conference.repository;

import hu.progmasters.conference.domain.Presentation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PresentationRepositoryTest {

    @Autowired
    PresentationRepository repository;

    @Test
    @Transactional
    void testSave() {
        Presentation toSave = new Presentation();
        toSave.setTitle("Reset");
        LocalDateTime startTime = LocalDateTime.of(2022, Month.SEPTEMBER, 26, 10, 0, 0);
        toSave.setStartTime(startTime);

        Optional<Presentation> expectedNull = repository.findById(1);
        assertTrue(expectedNull.isEmpty());

        Presentation saved = repository.save(toSave);
        assertEquals(1, saved.getId());
        assertEquals("Reset", saved.getTitle());
        assertEquals(startTime, saved.getStartTime());

        Optional<Presentation> foundById = repository.findById(1);
        assumeThat(foundById.isPresent());
        assertEquals(1, foundById.get().getId());
        assertEquals("Reset", foundById.get().getTitle());
        assertEquals(startTime, foundById.get().getStartTime());
    }
}
