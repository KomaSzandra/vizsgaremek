package hu.progmasters.conference.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.progmasters.conference.domain.AcademicRank;
import hu.progmasters.conference.dto.command.LecturerCreateCommand;
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

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LecturerControllerWebMvcIt {

    private MockMvc mockMvc;

    @Autowired
    LecturerController lecturerController;

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.lecturerController).build();
    }

//    @Autowired
//    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Lecturer test findAll")
    void testFindAllLecturer_lecturer_success() throws Exception {

        mockMvc.perform(get("/api/lecturers"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].name", equalTo("Dr. John Doe")));
    }

    @Test
    void testFindLecturerByName_success() throws Exception {
//        when(lecturerService.findByName(anyString()))
//                .thenReturn(new LecturerInfo(1, "Dr. John Doe", LocalDate.now(), "CEU", "profDoe@ceu.com", AcademicRank.PROFESSOR));
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
}
