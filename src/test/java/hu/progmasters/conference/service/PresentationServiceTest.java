package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.AcademicRank;
import hu.progmasters.conference.domain.Lecturer;
import hu.progmasters.conference.domain.Presentation;
import hu.progmasters.conference.dto.LecturerListInfo;
import hu.progmasters.conference.dto.PresentationInfo;
import hu.progmasters.conference.dto.command.PresentationCreateCommand;
import hu.progmasters.conference.dto.command.PresentationUpdateCommand;
import hu.progmasters.conference.exceptionhandler.GlobalExceptionHandler;
import hu.progmasters.conference.exceptionhandler.PresentationNotFoundException;
import hu.progmasters.conference.exceptionhandler.TitleNotValidException;
import hu.progmasters.conference.repository.PresentationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PresentationServiceTest {

    @Mock
    PresentationRepository presentationRepository;

    @InjectMocks
    PresentationService presentationService;

    private Presentation presentation;
    private Presentation presentation1;
    private Presentation presentation2;
    private PresentationCreateCommand createCommand;
    private PresentationCreateCommand createCommand1;
    private PresentationUpdateCommand updateCommand;
    private PresentationUpdateCommand updateCommand1;
    private PresentationInfo presentationInfo;
    private PresentationInfo presentationInfo1;
    private Lecturer lecturer;


    @BeforeEach
    void init() {
        presentationService = new PresentationService(presentationRepository, new ModelMapper());
        presentation = new Presentation(1, new Lecturer(), "Reset", LocalDateTime.of(
                2022, Month.SEPTEMBER, 26, 8, 0,0), new ArrayList<>());
        presentation1 = new Presentation(2, new Lecturer(), "Reset", LocalDateTime.of(
                2022, Month.SEPTEMBER, 26, 8, 0,0), new ArrayList<>());
        presentation2 = new Presentation(3, new Lecturer(), "Datas", LocalDateTime.of(
                2022, Month.SEPTEMBER, 26, 8, 0,0), new ArrayList<>());
        lecturer = new Lecturer(1, "Dr. John Doe", LocalDate.of(
                1980, 8, 5), "CEU", AcademicRank.SENIOR_LECTURER,
                "drDoe@lecturer.com", new Presentation());
        createCommand = new PresentationCreateCommand("Reset", LocalDateTime.of(
                2022, Month.SEPTEMBER, 26, 8, 0,0));
        createCommand1 = new PresentationCreateCommand("Datas", LocalDateTime.of(
                2022, Month.SEPTEMBER, 26, 8, 0,0));
        updateCommand = new PresentationUpdateCommand(LocalDateTime.of(
                2022, Month.SEPTEMBER, 26, 10, 0,0));
        updateCommand1 = new PresentationUpdateCommand(LocalDateTime.of(
                2022, Month.APRIL, 26, 10, 0,0));
        presentationInfo = new PresentationInfo(1, new LecturerListInfo(), "Reset",
                LocalDateTime.of(2022, Month.SEPTEMBER, 26, 8, 0,0));
        presentationInfo1 = new PresentationInfo(1, new LecturerListInfo(), "Reset",
                LocalDateTime.of(2022, Month.SEPTEMBER, 26, 10, 0,0));

    }

    @Test
    @DisplayName("Presentation test findAll empty")
    void testList_atStart_emptyList() {
        when(presentationRepository.findAll()).thenReturn(List.of());
        assertTrue(presentationService.findAll().isEmpty());
        verify(presentationRepository, times(1)).findAll();
        verifyNoMoreInteractions(presentationRepository);
    }

    @Test
    @DisplayName("Presentation test savePresentation")
    void testSavePresentation_presentation_success() {
        when(presentationRepository.save(any())).thenReturn(presentation);
        PresentationInfo saved = presentationService.savePresentation(createCommand);
        assertEquals(presentationInfo, saved);
        verify(presentationRepository, times(1)).save(any());
        verify(presentationRepository, times(1)).flush();
        verifyNoMoreInteractions(presentationRepository);
    }

    @Test
    @DisplayName("Presentation test savePresentation with invalid title")
    void testSavePresentation_presentation_invalidTitle() {
        when(presentationRepository.save(presentation1)).thenThrow(new TitleNotValidException(presentation1.getTitle()));
        assertThrows(TitleNotValidException.class, () -> presentationService.savePresentation(createCommand));
        verify(presentationRepository, times(1)).save(any());
        verifyNoMoreInteractions(presentationRepository);
    }

    @Test
    @DisplayName("Presentation test findById")
    void testFindById_presentation_success() {
        when(presentationRepository.findById(1)).thenReturn(Optional.of(presentation));
        PresentationInfo byId = presentationService.findById(1);
        assertEquals(presentationInfo, byId);
        verify(presentationRepository, times(1)).findById(1);
        verifyNoMoreInteractions(presentationRepository);
    }

    @Test
    @DisplayName("Presentation test findById not found")
    void testFindById_presentation_notFound() {
        when(presentationRepository.findById(9)).thenThrow(new PresentationNotFoundException(9));
        assertThrows(PresentationNotFoundException.class, () ->presentationService.findById(9));
        verify(presentationRepository, times(1)).findById(9);
        verifyNoMoreInteractions(presentationRepository);
    }

    @Test
    @DisplayName("Presentation test findAll")
    void testList_twoPresentationSaved_twoPresentationInList() {
        when(presentationRepository.save(any())).thenReturn(presentation);
        when(presentationRepository.save(any())).thenReturn(presentation2);
        when(presentationRepository.findAll()).thenReturn(List.of(presentation, presentation2));
        presentationService.savePresentation(createCommand);
        presentationService.savePresentation(createCommand1);
        assertThat(presentationService.findAll())
                .hasSize(2);
        verify(presentationRepository, times(2)).save(any());
        verify(presentationRepository, times(2)).flush();
        verify(presentationRepository, times(1)).findAll();
        verifyNoMoreInteractions(presentationRepository);
    }

    @Test
    @DisplayName("Presentation test updatePresentation")
    void testUpdate_presentation_startTime_success() {
        when(presentationRepository.findById(1)).thenReturn(Optional.of(presentation));
        PresentationInfo info = presentationService.updatePresentation(1, updateCommand);
        assertEquals(presentationInfo1, info);
        verify(presentationRepository, times(1)).findById(1);
        verifyNoMoreInteractions(presentationRepository);
    }
}

