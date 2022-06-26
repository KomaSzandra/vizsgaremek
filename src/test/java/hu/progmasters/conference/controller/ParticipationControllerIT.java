package hu.progmasters.conference.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.progmasters.conference.dto.command.ParticipationCreateCommand;
import hu.progmasters.conference.dto.command.ParticipationUpdateCommand;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ParticipationControllerIT {

    private MockMvc mockMvc;

    @Autowired
    ParticipationController participationController;

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.participationController).build();
    }

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Participation test findAll")
    void testFindAll_participation_success() throws Exception {
        mockMvc.perform(get("/api/participations"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].participant.name", equalTo("Dr. Jane Doe")))
                .andExpect(jsonPath("$[0].participant.institution", equalTo("Central European University")))
                .andExpect(jsonPath("$[0].participant.email", equalTo("Drjane@ceu.com")))
                .andExpect(jsonPath("$[0].presentation.title", equalTo("Reset")))
                .andExpect(jsonPath("$[0].presentation.id", equalTo(1)));
    }

    @Test
    @DisplayName("Participation test findById")
    void testFindById_participation_success() throws Exception {
        mockMvc.perform(get("/api/participations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.presentation.title", equalTo("Reset")))
                .andExpect(jsonPath("$.presentation.id", equalTo(1)))
                .andExpect(jsonPath("$.participant.name", equalTo("Dr. Jane Doe")))
                .andExpect(jsonPath("$.participant.institution", equalTo("Central European University")))
                .andExpect(jsonPath("$.participant.email", equalTo("Drjane@ceu.com")));
    }

    @Test
    @DisplayName("Participation test registrate")
    void testRegistrate_participation_success() throws Exception {
        ParticipationCreateCommand command = new ParticipationCreateCommand();
        command.setParticipantId(1);
        command.setPresentationId(3);

        mockMvc.perform(post("/api/participations")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Participation test registrate fail")
    void testRegistrate_participant_notFound() throws Exception {
        ParticipationCreateCommand command = new ParticipationCreateCommand();
        command.setParticipantId(2);
        command.setPresentationId(1);

        mockMvc.perform(get("/api/participants")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Participation test findAllByParticipant")
    void testFindAllByParticipant_participation_success() throws Exception {
        mockMvc.perform(get("/api/participations/findAllByParticipant")
                        .param("participantId", String.valueOf(1)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Participation test updateParticipantsPresentation")
    void testUpdateParticipantsPresentation_participation_success() throws Exception {
        ParticipationUpdateCommand command = new ParticipationUpdateCommand();
        command.setPresentationId(2);

        mockMvc.perform(put("/api/participations/1")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.presentation.title", is("Structures")));
    }
}
