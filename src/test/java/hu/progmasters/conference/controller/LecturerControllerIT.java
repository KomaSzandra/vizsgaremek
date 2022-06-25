package hu.progmasters.conference.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.progmasters.conference.domain.AcademicRank;
import hu.progmasters.conference.dto.command.LecturerCreateCommand;
import hu.progmasters.conference.dto.command.LecturerUpdateCommand;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LecturerControllerIT {

    private MockMvc mockMvc;

    @Autowired
    LecturerController lecturerController;

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.lecturerController).build();
    }

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @DisplayName("Lecturer test findAll")
    void testFindAll_lecturer_success() throws Exception {
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
        command.setInstitution("BMX");
        command.setEmail("bb@bmx.yu");
        command.setAcademicRank(AcademicRank.SENIOR_RESEARCH_FELLOW);
        command.setName("Dr. Yu");
        command.setDateOfBirth(LocalDate.of(1980, Month.JANUARY, 10));

        mockMvc.perform(post("/api/lecturers")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Dr. Yu")))
                .andExpect(jsonPath("$.institution", equalTo("BMX")))
                .andExpect(jsonPath("$.email", equalTo("bb@bmx.yu")));
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Dr. Bob")))
                .andDo(print());
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.presentationTitle", is("Reset")))
                .andExpect(jsonPath("$.presentationId", is(1)))
                .andExpect(jsonPath("$.email", is("ludwig@ceu.com")))
                .andExpect(jsonPath("$.institution", is("Central European University")));
    }

    @Test
    @DisplayName("Lecturer test findById")
    void testFindById_lecturer_success() throws Exception {
        mockMvc.perform(get("/api/lecturers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Dr. John Doe")))
                .andExpect(jsonPath("$.email", equalTo("ludwig@ceu.com")))
                .andExpect(jsonPath("$.academicRank", equalTo("PROFESSOR")));
    }

//    @Test
//    @DisplayName("Lecturer test deleteLecturer")
//    void testDeleteLecturer_lecturer_success() throws Exception {
//        LecturerCreateCommand command = new LecturerCreateCommand();
//        command.setName("Dr. Yu");
//        command.setAcademicRank(AcademicRank.CANDIDATE);
//        command.setEmail("yudr@gmowe.com");
//        command.setDateOfBirth(LocalDate.now().minusDays(1));
//        command.setInstitution("BMX");
//
//        mockMvc.perform(post("/api/lecturers")
//                        .content(objectMapper.writeValueAsString(command))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id", equalTo(4)));
//
//        mockMvc.perform(delete("/api/lecturers/4")
//                        .content(objectMapper.writeValueAsString(command))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
}
