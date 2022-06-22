package hu.progmasters.conference.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.progmasters.conference.dto.command.ParticipationCreateCommand;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ParticipationControllerTest {

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
    void testFindAllParticipation_success() throws Exception {
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
    void testFindById_success() throws Exception {
        mockMvc.perform(get("/api/participations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.presentation.title", equalTo("Reset")))
                .andExpect(jsonPath("$.presentation.id", equalTo(1)))
                .andExpect(jsonPath("$.participant.name", equalTo("Dr. Jane Doe")))
                .andExpect(jsonPath("$.participant.institution", equalTo("Central European University")))
                .andExpect(jsonPath("$.participant.email", equalTo("Drjane@ceu.com")));
    }

//    @Test
//    void testCreateParticipation_alreadyRegistered() throws Exception {
//        ParticipationCreateCommand command = new ParticipationCreateCommand();
//        command.setParticipantId(1);
//        command.setPresentationId(1);
//
//        mockMvc.perform(post("/api/participations")
//                .content(objectMapper.writeValueAsString(command))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect((ResultMatcher) jsonPath("$[0].field", is("participantId")))
//                .andExpect((ResultMatcher) jsonPath("$[0].errorMessage", is("Participant already registered to this presentation")));
//
//    }

    @Test
    void testCreateParticipation_success() throws Exception {
        ParticipationCreateCommand command = new ParticipationCreateCommand();
        command.setParticipantId(1);
        command.setPresentationId(2);

        mockMvc.perform(post("/api/participations")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateParticipation_participantNotFound() throws Exception {
        ParticipationCreateCommand command = new ParticipationCreateCommand();
        command.setParticipantId(2);
        command.setPresentationId(1);

        mockMvc.perform(get("/api/participants")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
//                .andExpect(jsonPath("$[0].field", is("participantId")))
//                .andExpect(jsonPath("$[0].errorMessage", is("No participant found with id")));
    }
}
