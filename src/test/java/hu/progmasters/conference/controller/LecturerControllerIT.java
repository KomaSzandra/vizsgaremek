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
                .andExpect(jsonPath("$[0].name", equalTo("Dr. John Doe")));
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
                .andExpect(jsonPath("$.institution", equalTo("BMX")))
                .andExpect(jsonPath("$.email", equalTo("bb@bmx.yu")));
    }

    @Test
    @DisplayName("Lecturer test findByName")
    void testFindByName_lecturer_success() throws Exception {
        LecturerCreateCommand command = new LecturerCreateCommand();
        command.setName("Jsoa");
        command.setAcademicRank(AcademicRank.CANDIDATE);
        command.setEmail("dasha@gmowe.hu");
        command.setDateOfBirth(LocalDate.now().minusDays(1));
        command.setInstitution("Bla");

        mockMvc.perform(post("/api/lecturers")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/lecturers/findByName")
                        .param("name", "Jsoa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Jsoa")))
                .andDo(print());
    }

    @Test
    @DisplayName("Lecturer test update")
    void testAddLecturerToAPresentation_success() throws Exception {
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

//    @Test
//    void testAddLecturerToPresentation_alreadyHas() throws Exception {
//        LecturerUpdateCommand command = new LecturerUpdateCommand();
//        command.setLecturerId(1);
//
//        mockMvc.perform(put("/api/lecturers/1")
//                        .content(objectMapper.writeValueAsString(command))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//        mockMvc.perform(put("/api/lecturers/2")
//                        .content(objectMapper.writeValueAsString(command))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$[0].field", is("lecturerId")))
//                .andExpect(jsonPath("$[0].errorMessage", is("Reserved, lecturer already has a lecture")));
//    }

//    @Test
//    void testLecturerNotFound() throws Exception {
//        LecturerUpdateCommand command = new LecturerUpdateCommand();
//        command.setLecturerId(3);
//
//        mockMvc.perform(put("/api/lecturers/1")
//        .content(objectMapper.writeValueAsString(command))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$[0].field", is("lecturerId")))
//                .andExpect(jsonPath("$[0].errorMessage", is("No lecturer found with id")));
//    }

    @Test
    @DisplayName("Lecturer test findById")
    void testFindById_lecturer_success() throws Exception {
        mockMvc.perform(get("/api/lecturers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Dr. John Doe")));
               /* .andExpect(jsonPath("$.academicRank", equalTo(AcademicRank.PROFESSOR)));*/
    }

//    @Test
//    void testFindById_notFound() throws Exception {
//        mockMvc.perform(get("/api/lecturers/7"))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$[0].field", is("id")))
//                .andExpect(jsonPath("$[0].errorMessage", is("No lecturer found with id")));
//    }



//    @Test
//    void deleteLecturer() throws Exception {
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
//                .andExpect(status().isCreated());
//
//        mockMvc.perform(delete("/api/lecturers/1")
//                        .content(objectMapper.writeValueAsString(command))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
}
