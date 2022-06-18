package hu.progmasters.conference.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.progmasters.conference.domain.AcademicRank;
import hu.progmasters.conference.dto.ParticipantInfo;
import hu.progmasters.conference.dto.ParticipantListItem;
import hu.progmasters.conference.dto.command.ParticipantCreateCommand;
import hu.progmasters.conference.service.ParticipantService;
import hu.progmasters.conference.service.ParticipationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ParticipantController.class)
public class ParticipantControllerTest {

    @MockBean
    ParticipantService participantService;

    @MockBean
    ParticipationService participationService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testFindAll_success() throws Exception {
        when(participantService.findAll())
                .thenReturn(List.of(
                        new ParticipantListItem(1, "Dr. Jack Doe", "CEU", AcademicRank.CANDIDATE),
                        new ParticipantListItem(2, "Dr. Jane Doe", "CEU", AcademicRank.CANDIDATE)
                ));

        mockMvc.perform(get("/api/participants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", equalTo("Dr. Jack Doe")));
    }

    @Test
    void testCreateParticipant_success() throws Exception {
        ParticipantCreateCommand command = new ParticipantCreateCommand();
        command.setName("John");
        command.setInstitution("CEU");
        command.setEmail("john@ceu.com");
        command.setDateOfBirth(LocalDate.now().minusDays(1));
        command.setAcademicRank(AcademicRank.CANDIDATE);

        mockMvc.perform(post("/api/participants")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void findById_success() throws Exception {
        when(participantService.findById(1))
                .thenReturn(new ParticipantInfo(1, "Doe", "CEU", "d@ceu.com", AcademicRank.CANDIDATE, LocalDate.of(1980, Month.JANUARY, 16)));

        mockMvc.perform(get("/api/participants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Doe")));
    }



}
