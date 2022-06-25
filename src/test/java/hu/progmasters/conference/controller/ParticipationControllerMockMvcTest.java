package hu.progmasters.conference.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.progmasters.conference.dto.ParticipationInfo;
import hu.progmasters.conference.exceptionhandler.ParticipationNotFoundException;
import hu.progmasters.conference.service.ParticipationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ParticipationController.class)
public class ParticipationControllerMockMvcTest {

    @MockBean
    ParticipationService participationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Participation test delete")
    void testDelete_participation_success() throws Exception {
        when(participationService.findById(1)).thenReturn(new ParticipationInfo());

        mockMvc.perform(delete("/api/participations/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Participation test findById not found")
    void testFindById_participation_notFound() throws Exception {
        when(participationService.findById(11))
                .thenThrow(ParticipationNotFoundException.class);

        mockMvc.perform(get("/api/participations/11"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("participationId")))
                .andExpect(jsonPath("$[0].errorMessage", is("No participation found with id")));
    }
}
