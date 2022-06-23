package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.AcademicRank;
import hu.progmasters.conference.dto.ParticipantInfo;
import hu.progmasters.conference.dto.ParticipationInfo;
import hu.progmasters.conference.dto.PresentationInfo;
import hu.progmasters.conference.dto.command.ParticipantCreateCommand;
import hu.progmasters.conference.dto.command.ParticipationCreateCommand;
import hu.progmasters.conference.dto.command.PresentationCreateCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ParticipationServiceIT {

    @Autowired
    ParticipationService participationService;
    @Autowired
    ParticipantService participantService;
    @Autowired
    PresentationService presentationService;

    @Test
    void testRegistrate() {
        ParticipantInfo participantInfo = createParticipant();
        PresentationInfo presentationInfo = createPresentation();

        ParticipationCreateCommand command = new ParticipationCreateCommand();
        command.setParticipantId(participantInfo.getId());
        command.setPresentationId(presentationInfo.getId());

        ParticipationInfo participationInfo = null;
        try {
            participationInfo = participationService.registrate(command);
        } catch (RuntimeException e) {
            fail(e);
        }

        assertNotNull(participationInfo);
        assertNotNull(participationInfo.getParticipant());
        assertEquals(participantInfo.getId(), participationInfo.getParticipant().getId());
        assertEquals(presentationInfo.getId(), participationInfo.getPresentation().getId());
    }

    private ParticipantInfo createParticipant() {
        ParticipantCreateCommand command = new ParticipantCreateCommand();
        LocalDate dateOfBirth = LocalDate.of(1990, Month.SEPTEMBER, 26);
        command.setName("Dr. Jack Doe");
        command.setAcademicRank(AcademicRank.CANDIDATE);
        command.setEmail("lilDoe@uni.com");
        command.setInstitution("BME");
        command.setDateOfBirth(dateOfBirth);
        return participantService.saveParticipant(command);
    }

    private PresentationInfo createPresentation() {
        PresentationCreateCommand command = new PresentationCreateCommand();
        LocalDateTime startTime = LocalDateTime.of(2022, Month.SEPTEMBER, 26, 10, 0, 0);
        command.setTitle("Test project");
        command.setStartTime(startTime);
        return presentationService.savePresentation(command);
    }
}
