package hu.progmasters.conference.service;

import hu.progmasters.conference.dto.ParticipantInfo;
import hu.progmasters.conference.dto.ParticipationInfo;
import hu.progmasters.conference.dto.PresentationInfo;
import hu.progmasters.conference.dto.command.ParticipantCreateCommand;
import hu.progmasters.conference.dto.command.ParticipationCreateCommand;
import hu.progmasters.conference.dto.command.PresentationCreateCommand;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ParticipationServiceTest {


    ParticipationService participationService;
    ParticipantService participantService;
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
        } catch (Exception e) {
            fail(e);
        }

        assertNotNull(participationInfo);
        assertNotNull(participationInfo.getParticipant());
        assertEquals(participantInfo.getId(), participationInfo.getParticipant().getId());
        assertEquals(presentationInfo.getId(), participationInfo.getPresentation().getId());
    }

    private ParticipantInfo createParticipant() {
        ParticipantCreateCommand command = new ParticipantCreateCommand();
        command.setName("Dr. Jack Doe");

        ParticipantInfo info = participantService.saveParticipant(command);
        return info;
    }

    private PresentationInfo createPresentation() {
        PresentationCreateCommand command = new PresentationCreateCommand();
        command.setTitle("Test project");
        PresentationInfo info = presentationService.savePresentation(command);
        return info;
    }
}
