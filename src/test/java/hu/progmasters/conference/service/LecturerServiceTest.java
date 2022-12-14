package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.AcademicRank;
import hu.progmasters.conference.domain.Lecturer;
import hu.progmasters.conference.domain.Presentation;
import hu.progmasters.conference.dto.LecturerInfo;
import hu.progmasters.conference.dto.LecturerListInfo;
import hu.progmasters.conference.dto.command.LecturerCreateCommand;
import hu.progmasters.conference.dto.command.LecturerUpdateCommand;
import hu.progmasters.conference.exceptionhandler.EmailNotValidException;
import hu.progmasters.conference.exceptionhandler.LecturerAlreadyHasAPresentationException;
import hu.progmasters.conference.exceptionhandler.LecturerNotFoundException;
import hu.progmasters.conference.repository.LecturerRepository;
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
public class LecturerServiceTest {

    @Mock
    LecturerRepository lecturerRepository;

    @Mock
    PresentationService presentationService;

    @InjectMocks
    LecturerService lecturerService;

    private Presentation presentation;
    private Lecturer lecturer;
    private Lecturer lecturer1;
    private LecturerCreateCommand createCommand;
    private LecturerCreateCommand createCommand1;
    private LecturerUpdateCommand updateCommand;
    private LecturerUpdateCommand updateCommand1;
    private LecturerInfo lecturerInfo;
    private LecturerInfo lecturerInfo1;


    @BeforeEach
    void init() {
        lecturerService = new LecturerService(lecturerRepository, new ModelMapper(), presentationService);
        presentation = new Presentation(1, new Lecturer(), "Reset", LocalDateTime.of(
                2022, Month.SEPTEMBER, 26, 8, 0, 0), new ArrayList<>());
        lecturer = new Lecturer(1, "Dr. John Doe", LocalDate.of(
                1980, 8, 5), "CEU", AcademicRank.SENIOR_LECTURER,
                "drDoe@lecturer.com", new Presentation());
        lecturer1 = new Lecturer(2, "Dr. John Doe", LocalDate.of(
                1980, 8, 5), "CEU", AcademicRank.SENIOR_RESEARCH_FELLOW,
                "drDoe@lecturer.com", new Presentation());
        lecturerInfo = new LecturerInfo(1, "Dr. John Doe", LocalDate.of(
                1980, 8, 5), "CEU", "drDoe@lecturer.com",
                AcademicRank.SENIOR_LECTURER, null, null);
        lecturerInfo1 = new LecturerInfo(2, "Dr. John Doe", LocalDate.of(
                1980, 8, 5), "CEU", "drDoe@lecturer.com",
                AcademicRank.SENIOR_RESEARCH_FELLOW, 1, "Reset");
        updateCommand = new LecturerUpdateCommand(1);
        updateCommand1 = new LecturerUpdateCommand(2);
        createCommand = new LecturerCreateCommand("Dr. John Doe", String.valueOf(AcademicRank.SENIOR_LECTURER), "CEU",
                "drDoe@lecturer.com", LocalDate.of(1980, 8, 5));
        createCommand1 = new LecturerCreateCommand("Dr. John Doe", String.valueOf(AcademicRank.SENIOR_RESEARCH_FELLOW),
                "CEU", "drDoe@lecturer.com", LocalDate.of(1980, 8, 5));
    }

    @Test
    @DisplayName("Lecturer test findAll empty")
    void testList_atStart_emptyList() {
        when(lecturerRepository.findAll()).thenReturn(List.of());
        assertTrue(lecturerService.findAllLecturer().isEmpty());
        verify(lecturerRepository, times(1)).findAll();
        verifyNoMoreInteractions(lecturerRepository);
    }

    @Test
    @DisplayName("Lecturer test saveLecturer")
    void testSaveLecturer_lecturer_success() {
        when(lecturerRepository.save(any())).thenReturn(lecturer);
        LecturerInfo saved = lecturerService.saveLecturer(createCommand);
        assertEquals(lecturerInfo, saved);
        verify(lecturerRepository, times(1)).save(any());
        verify(lecturerRepository, times(1)).flush();
        verifyNoMoreInteractions(lecturerRepository);
    }

    @Test
    @DisplayName("Lecturer test saveLecturer with invalid email")
    void testSaveLecturer_lecturer_invalidEmail() {
        when(lecturerRepository.save(lecturer1)).thenThrow(new EmailNotValidException(lecturer1.getEmail()));
        assertThrows(EmailNotValidException.class, () -> lecturerService.saveLecturer(createCommand1));
        verify(lecturerRepository, times(1)).save(any());
        verifyNoMoreInteractions(lecturerRepository);
    }

    @Test
    @DisplayName("Lecturer test findAll")
    void testList_singleLecturerSaved_singleLecturerInList() {
        when(lecturerRepository.save(any())).thenReturn(lecturer);
        when(lecturerRepository.findAll()).thenReturn(List.of(lecturer));
        lecturerService.saveLecturer(createCommand);
        LecturerListInfo listInfo = new LecturerListInfo(1, "Dr. John Doe", AcademicRank.SENIOR_LECTURER,
                "CEU", "drDoe@lecturer.com");
        assertThat(lecturerService.findAllLecturer())
                .hasSize(1)
                .containsExactly(listInfo);
        verify(lecturerRepository, times(1)).save(any());
        verify(lecturerRepository, times(1)).flush();
        verify(lecturerRepository, times(1)).findAll();
        verifyNoMoreInteractions(lecturerRepository);
    }

    @Test
    @DisplayName("Lecturer test findById")
    void testFindById_lecturer_successfulFind() {
        when(lecturerRepository.findById(1)).thenReturn(Optional.of(lecturer));
        assertEquals(lecturerService.findById(1), lecturerInfo);
        verify(lecturerRepository, times(1)).findById(1);
        verifyNoMoreInteractions(lecturerRepository);
    }

    @Test
    @DisplayName("Lecturer test findById not found")
    void testFindById_lecturer_notFound() {
        when(lecturerRepository.findById(9)).thenThrow(new LecturerNotFoundException(9));
        assertThrows(LecturerNotFoundException.class, () -> lecturerService.findById(9));
        verify(lecturerRepository, times(1)).findById(9);
        verifyNoMoreInteractions(lecturerRepository);
    }

    @Test
    @DisplayName("Lecturer test update exception")
    void testAddLecturerToPresentation_lecturer_alreadyHas() {
        when(presentationService.findPresentationById(2)).thenReturn(presentation);
        when(lecturerRepository.findById(2)).thenReturn(Optional.of(lecturer1));
        assertThrows(LecturerAlreadyHasAPresentationException.class, ()
                -> lecturerService.addLecturerToPresentation(2, updateCommand1));
        verify(lecturerRepository, times(1)).findById(2);
        verify(presentationService, times(1)).findPresentationById(2);
        verifyNoMoreInteractions(lecturerRepository, presentationService);
    }

    @Test
    @DisplayName("Lecturer test deleteLecturer exception")
    void testDeleteLecturer_lecturer_alreadyHasAPresentationexception() {
        when(lecturerRepository.findById(2)).thenReturn(Optional.of(lecturer1));
        assertThrows(LecturerAlreadyHasAPresentationException.class, () -> lecturerService.deleteLecturer(2));
        verify(lecturerRepository, times(1)).findById(2);
        verifyNoMoreInteractions(lecturerRepository);
    }
}
