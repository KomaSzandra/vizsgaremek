package hu.progmasters.conference.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.progmasters.conference.domain.AcademicRank;
import hu.progmasters.conference.dto.LecturerInfo;
import hu.progmasters.conference.dto.LecturerListInfo;
import hu.progmasters.conference.dto.command.LecturerCreateCommand;
import hu.progmasters.conference.dto.command.LecturerUpdateCommand;
import hu.progmasters.conference.exceptionhandler.LecturerNotFoundException;
import hu.progmasters.conference.service.LecturerService;
import org.junit.jupiter.api.DisplayName;
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
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LecturerController.class)
public class LecturerControllerMockMvcTest {

    @MockBean
    LecturerService lecturerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @DisplayName("Lecturer test findAll empty")
    void test_atStart_emptyList() throws Exception {
        mockMvc.perform(get("/api/lecturers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("Lecturer test findAll")
    void testFindAll_lecturer_success() throws Exception {
        when(lecturerService.findAllLecturer())
                .thenReturn(List.of(
                        new LecturerListInfo(1, "Dr. John Doe", AcademicRank.PROFESSOR, "CEU",
                                "ludwig@ceu.com"),
                        new LecturerListInfo(2, "Dr. Jack Doe", AcademicRank.PROFESSOR, "CEU",
                                "doe@ceu.com")
                ));
        mockMvc.perform(get("/api/lecturers"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].name", equalTo("Dr. John Doe")))
                .andExpect(jsonPath("$[1].name", equalTo("Dr. Jack Doe")))
                .andExpect(jsonPath("$[0].email", equalTo("ludwig@ceu.com")))
                .andExpect(jsonPath("$[1].email", equalTo("doe@ceu.com")))
                .andExpect(jsonPath("$[0].academicRank", equalTo("PROFESSOR")))
                .andExpect(jsonPath("$[1].academicRank", equalTo("PROFESSOR")));
    }

    @Test
    @DisplayName("Lecturer test saveLecturer")
    void testSaveLecturer_lecturer_success() throws Exception {
        LecturerCreateCommand command = new LecturerCreateCommand();
        command.setName("Dr. Yu");
        command.setInstitution("BMX");
        command.setEmail("bb@bmx.yu");
        command.setAcademicRank(AcademicRank.SENIOR_RESEARCH_FELLOW);
        command.setDateOfBirth(LocalDate.of(1980, Month.JANUARY, 10));

        mockMvc.perform(post("/api/lecturers")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Lecturer test saveLecturer with blank name")
    void testSaveLecturer_lecturer_invalidName() throws Exception {
        LecturerCreateCommand command = new LecturerCreateCommand();
        command.setName("");
        command.setInstitution("CEU");
        command.setEmail("john@ceu.com");
        command.setDateOfBirth(LocalDate.now().minusDays(1));
        command.setAcademicRank(AcademicRank.CANDIDATE);

        mockMvc.perform(post("/api/lecturers")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("name")))
                .andExpect(jsonPath("$[0].errorMessage", is("Must not be blank")));
    }

    @Test
    @DisplayName("Lecturer test saveLecturer with blank email")
    void testSaveLecturer_lecturer_invalidEmail() throws Exception {
        LecturerCreateCommand command = new LecturerCreateCommand();
        command.setName("Dr. Bob");
        command.setInstitution("CEU");
        command.setEmail("");
        command.setDateOfBirth(LocalDate.now().minusDays(1));
        command.setAcademicRank(AcademicRank.CANDIDATE);

        mockMvc.perform(post("/api/lecturers")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("email")))
                .andExpect(jsonPath("$[0].errorMessage", is("Must not be blank")));
    }

    @Test
    @DisplayName("Lecturer test findByName")
    void testFindByName_lecturer_success() throws Exception {
        LecturerCreateCommand command = new LecturerCreateCommand();
        command.setName("Dr. Bob");
        command.setAcademicRank(AcademicRank.CANDIDATE);
        command.setEmail("bob@gbob.bob");
        command.setDateOfBirth(LocalDate.now().minusDays(1));
        command.setInstitution("BOB");

        mockMvc.perform(post("/api/lecturers")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/lecturers/findByName")
                        .param("name", "Dr. Bob"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Lecturer test update")
    void testAddLecturerToAPresentation_lecturer_success() throws Exception {
        LecturerUpdateCommand command = new LecturerUpdateCommand();
        command.setLecturerId(1);

        mockMvc.perform(put("/api/lecturers/1")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Lecturer test findById")
    void testFindById_lecturer_success() throws Exception {
        when(lecturerService.findById(1)).thenReturn(new LecturerInfo(1, "Dr. Bob",
                        LocalDate.of(1930, Month.SEPTEMBER, 1), "MTA", "drBob@mta.hu",
                        AcademicRank.PROFESSOR_EMERITUS, 1, "Reset"));
        mockMvc.perform(get("/api/lecturers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Dr. Bob")))
                .andExpect(jsonPath("$.email", equalTo("drBob@mta.hu")))
                .andExpect(jsonPath("$.academicRank", equalTo("PROFESSOR_EMERITUS")));
    }

    @Test
    @DisplayName("Lecturer test findById not found")
    void testFindById_lecturer_notFound() throws Exception {
        when(lecturerService.findById(11))
                .thenThrow(LecturerNotFoundException.class);

        mockMvc.perform(get("/api/lecturers/11"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("id")))
                .andExpect(jsonPath("$[0].errorMessage", is("No lecturer found with the given id")));
    }

    @Test
    void testDeleteLecturer_lecturer_success() throws Exception {
        when(lecturerService.findById(1)).thenReturn(new LecturerInfo());

        mockMvc.perform(delete("/api/lecturers/1"))
                .andExpect(status().isOk());
    }
}
