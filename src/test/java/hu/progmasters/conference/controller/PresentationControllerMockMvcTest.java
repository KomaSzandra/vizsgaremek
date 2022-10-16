package hu.progmasters.conference.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.progmasters.conference.domain.Presentation;
import hu.progmasters.conference.dto.LecturerListInfo;
import hu.progmasters.conference.dto.PresentationInfo;
import hu.progmasters.conference.dto.command.PresentationCreateCommand;
import hu.progmasters.conference.exceptionhandler.PresentationNotFoundException;
import hu.progmasters.conference.exceptionhandler.PresentationTitleNotFoundException;
import hu.progmasters.conference.exceptionhandler.TitleNotValidException;
import hu.progmasters.conference.service.ParticipationService;
import hu.progmasters.conference.service.PresentationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.Month;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PresentationController.class)
public class PresentationControllerMockMvcTest {

    @MockBean
    PresentationService presentationService;

    @MockBean
    ParticipationService participationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Presentation test cancelPresentation")
    void testCancelPresentation_participation_success() throws Exception {
        when(presentationService.findPresentationById(1)).thenReturn(new Presentation());

        mockMvc.perform(delete("/api/presentations/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Presentation test findById not found")
    void testFindById_presentation_notFound() throws Exception {
        when(presentationService.findById(11))
                .thenThrow(PresentationNotFoundException.class);

        mockMvc.perform(get("/api/presentations/11"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("presentationId")))
                .andExpect(jsonPath("$[0].errorMessage", is("No presentation found with id")));
    }

    @Test
    @DisplayName("Presentation test savePresentation with blank title")
    void testSavePresentation_presentation_invalidTitle() throws Exception {
        PresentationCreateCommand command = new PresentationCreateCommand();
        command.setTitle("");
        command.setStartTime(LocalDateTime.of(2022, Month.SEPTEMBER, 26, 10,0,0));

        mockMvc.perform(post("/api/presentations")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("title")))
                .andExpect(jsonPath("$[0].errorMessage", is("must not be blank")));
    }

    @Test
    @DisplayName("Presentation test savePresentation with same title")
    void testSavePresentation_presentationTitle_alreadyExists() throws Exception {
        PresentationCreateCommand command = new PresentationCreateCommand();
        command.setTitle("Test title");
        command.setStartTime(LocalDateTime.of(2022, Month.SEPTEMBER, 26, 10,0,0));
        when(presentationService.savePresentation(command)).thenReturn(new PresentationInfo(1, new LecturerListInfo(),
                "Test title", LocalDateTime.of(2022, Month.SEPTEMBER, 26, 10,0,0)));

        mockMvc.perform(post("/api/presentations")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        PresentationCreateCommand command1 = new PresentationCreateCommand();
        command1.setTitle("Test title");
        command1.setStartTime(LocalDateTime.of(2022, Month.SEPTEMBER, 26, 10,0,0));

        when(presentationService.savePresentation(command1)).thenThrow(TitleNotValidException.class);

        mockMvc.perform(post("/api/presentations")
                        .content(objectMapper.writeValueAsString(command1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("title")))
                .andExpect(jsonPath("$[0].errorMessage", is("The title already exists")));
    }
}
