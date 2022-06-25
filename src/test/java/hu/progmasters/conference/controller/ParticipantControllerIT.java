package hu.progmasters.conference.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.progmasters.conference.domain.AcademicRank;
import hu.progmasters.conference.dto.ParticipantInfo;
import hu.progmasters.conference.dto.ParticipantListItem;
import hu.progmasters.conference.dto.ParticipationInfo;
import hu.progmasters.conference.dto.command.ParticipantCreateCommand;
import hu.progmasters.conference.dto.command.ParticipantUpdateCommand;
import hu.progmasters.conference.exceptionhandler.EmailNotValidException;
import hu.progmasters.conference.exceptionhandler.ParticipantNotFoundException;
import hu.progmasters.conference.service.ParticipantService;
import hu.progmasters.conference.service.ParticipationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = ParticipantController.class)
public class ParticipantControllerIT {

    @MockBean
    ParticipantService participantService;

    @MockBean
    ParticipationService participationService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Participant test findAll empty")
    void test_atStart_emptyList() throws Exception {
        mockMvc.perform(get("/api/participants"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("Participant test findAll")
    void testFindAll_participant_success() throws Exception {
        when(participantService.findAll())
                .thenReturn(List.of(
                        new ParticipantListItem(1, "Dr. Jack Doe", "CEU", AcademicRank.CANDIDATE),
                        new ParticipantListItem(2, "Dr. Jane Doe", "CEU", AcademicRank.CANDIDATE)
                ));

        mockMvc.perform(get("/api/participants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", equalTo("Dr. Jack Doe")))
                .andExpect(jsonPath("$[1].name", equalTo("Dr. Jane Doe")))
                .andExpect(jsonPath("$[0].institution", equalTo("CEU")));
    }

    @Test
    @DisplayName("Participant test saveParticipant")
    void testSaveParticipant_participant_success() throws Exception {
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
    @DisplayName("Participant test saveParticipant with invalid name")
    void testSaveParticipant_participant_inValidName() throws Exception {
        ParticipantCreateCommand command = new ParticipantCreateCommand();
        command.setName("");
        command.setInstitution("CEU");
        command.setEmail("john@ceu.com");
        command.setDateOfBirth(LocalDate.now().minusDays(1));
        command.setAcademicRank(AcademicRank.CANDIDATE);

        mockMvc.perform(post("/api/participants")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("name")))
                .andExpect(jsonPath("$[0].errorMessage", is("Must not be blank")));
    }

    @Test
    @DisplayName("Participant test update")
    void testUpdate_participant_success() throws Exception {
        when(participantService.findById(1))
                .thenReturn(new ParticipantInfo(1, "Doe", "CEU", "d@ceu.com", AcademicRank.CANDIDATE, LocalDate.of(1980, Month.JANUARY, 16)));
        ParticipantUpdateCommand command1 = new ParticipantUpdateCommand();
        command1.setInstitution("BMX");
        when(participantService.update(1, command1)).thenReturn(new ParticipantInfo(1, "Doe", "BMX", "d@ceu.com", AcademicRank.CANDIDATE, LocalDate.of(1980, Month.JANUARY, 16)));

        mockMvc.perform(put("/api/participants/1")
                        .content(objectMapper.writeValueAsString(command1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.institution", is("BMX")));
    }

    @Test
    void testSaveParticipant_participant_invalidEmail() throws Exception {
        ParticipantCreateCommand command = new ParticipantCreateCommand();
        command.setName("Dr. John");
        command.setInstitution("CEU");
        command.setEmail("john@ceu.com");
        command.setDateOfBirth(LocalDate.of(1980, Month.JANUARY, 16));
        command.setAcademicRank(AcademicRank.CANDIDATE);
        when(participantService.saveParticipant(command)).thenReturn(new ParticipantInfo(1, "Dr. John", "CEU", "john@ceu.com", AcademicRank.CANDIDATE, LocalDate.of(1980, Month.JANUARY, 16)));

        mockMvc.perform(post("/api/participants")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        ParticipantCreateCommand command1 = new ParticipantCreateCommand();
        command1.setName("Dr. John");
        command1.setInstitution("CEU");
        command1.setEmail("john@ceu.com");
        command1.setDateOfBirth(LocalDate.of(1980, Month.JANUARY, 16));
        command1.setAcademicRank(AcademicRank.CANDIDATE);
        when(participantService.saveParticipant(command)).thenThrow(EmailNotValidException.class);

        mockMvc.perform(post("/api/participants")
                        .content(objectMapper.writeValueAsString(command1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("email")))
                .andExpect(jsonPath("$[0].errorMessage", is("Email already registered")));
    }

    @Test
    @DisplayName("Participant test findAllByName")
    void testFindAllByName_participant_success() throws Exception {
        ParticipantCreateCommand command = new ParticipantCreateCommand();
        command.setName("Bob");
        command.setAcademicRank(AcademicRank.CANDIDATE);
        command.setEmail("bob@gmowe.hu");
        command.setDateOfBirth(LocalDate.now().minusDays(1));
        command.setInstitution("BMX");

        mockMvc.perform(post("/api/participants")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/participants/findAllByName")
                        .param("name", "Bob"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Participant test findById")
    void testFindById_participant_success() throws Exception {
        when(participantService.findById(1))
                .thenReturn(new ParticipantInfo(1, "Doe", "CEU", "d@ceu.com", AcademicRank.CANDIDATE, LocalDate.of(1980, Month.JANUARY, 16)));

        mockMvc.perform(get("/api/participants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Doe")));
    }

    @Test
    @DisplayName("Participant test findById not found")
    void testFindById_participant_notFound() throws Exception {
        when(participantService.findById(11))
                .thenThrow(ParticipantNotFoundException.class);

        mockMvc.perform(get("/api/participants/11"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$[0].field", is("participantId")))
                .andExpect(jsonPath("$[0].errorMessage", is("No participant found with id")));
    }

    @Test
    void testDeleteParticipations_participant_success() throws Exception {
        when(participationService.findByParticipant(1)).thenReturn(new ArrayList<ParticipationInfo>());

        mockMvc.perform(delete("/api/participants/1"))
                .andExpect(status().isOk());
    }
}
