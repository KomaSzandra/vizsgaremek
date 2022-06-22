package hu.progmasters.conference.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.progmasters.conference.dto.command.ParticipantUpdateCommand;
import hu.progmasters.conference.dto.command.PresentationCreateCommand;
import hu.progmasters.conference.dto.command.PresentationUpdateCommand;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.Month;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PresentationControllerIt {

    private MockMvc mockMvc;

    @Autowired
    PresentationController presentationController;

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.presentationController).build();
    }

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testCreatePresentation_success() throws Exception {
        PresentationCreateCommand command = new PresentationCreateCommand();
        command.setTitle("Title");
        command.setStartTime(LocalDateTime.of(2022, Month.SEPTEMBER, 26, 0,0,0));

        mockMvc.perform(post("/api/presentations")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void testCreatePresentation_inValidTitle() throws Exception {
        PresentationCreateCommand command = new PresentationCreateCommand();
        command.setTitle("");
        command.setStartTime(LocalDateTime.of(2022, Month.SEPTEMBER, 26, 0,0,0));

        mockMvc.perform(post("/api/presentations")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$[0].field", is("title")))
//                .andExpect(jsonPath("$[0].errorMessage", is("must not be blank")));
    }

    @Test
    void testCreatePresentation_inValidStartTime() throws Exception {
        PresentationCreateCommand command = new PresentationCreateCommand();
        command.setTitle("Reset");
        command.setStartTime(null);

        mockMvc.perform(post("/api/presentations")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$[0].field", is("startTime")))
//                .andExpect(jsonPath("$[0].errorMessage", is("must not be null")));

    }

    @Test
    @DisplayName("Presentation test findAll")
    void testFindAllPresentation_success() throws Exception {
        mockMvc.perform(get("/api/presentations"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].title", equalTo("Reset")));
    }

    @Test
    void testFindById_success() throws Exception {
        mockMvc.perform(get("/api/presentations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", equalTo("Reset")));
    }

    @Test
    void testFindPresentationByTitle_success() throws Exception {
        PresentationCreateCommand command = new PresentationCreateCommand();
        command.setTitle("Test title");
        command.setStartTime(LocalDateTime.of(2022, Month.SEPTEMBER, 26, 13, 00, 00));

        mockMvc.perform(post("/api/presentations")
                .content(objectMapper.writeValueAsString(command))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/presentations/findByTitle")
                .param("title", "Test title"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", equalTo("Test title")))
                .andDo(print());
    }

    @Test
    void testUpdatePresentation_success() throws Exception {
        PresentationUpdateCommand command = new PresentationUpdateCommand();
        command.setStartTime(LocalDateTime.of(2022, Month.SEPTEMBER, 26, 12, 0, 0, 0));

        mockMvc.perform(put("/api/presentations/1")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
