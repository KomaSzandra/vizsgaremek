package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.*;
import hu.progmasters.conference.dto.LecturerListInfo;
import hu.progmasters.conference.dto.ParticipantInfo;
import hu.progmasters.conference.dto.ParticipationInfo;
import hu.progmasters.conference.dto.PresentationInfo;
import hu.progmasters.conference.dto.command.ParticipationCreateCommand;
import hu.progmasters.conference.dto.command.ParticipationUpdateCommand;
import hu.progmasters.conference.exceptionhandler.ParticipationNotFoundException;
import hu.progmasters.conference.repository.ParticipationRepository;
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
public class ParticipationServiceTest {

    @Mock
    ParticipationRepository participationRepository;

    @Mock
    ParticipantService participantService;

    @Mock
    PresentationService presentationService;

    @InjectMocks
    ParticipationService participationService;

    private Participation participation;
    private ParticipationCreateCommand createCommand;
    private ParticipationInfo participationInfo;
    private Participant participant;
    private ParticipantInfo participantInfo;
    private Presentation presentation;
    private Presentation presentation1;
    private PresentationInfo presentationInfo;
    private PresentationInfo presentationInfo1;
    private ParticipationInfo participationInfo1;
    private ParticipationUpdateCommand updateCommand;

    @BeforeEach
    void init() {
        participationService = new ParticipationService(new ModelMapper(), participationRepository,
                presentationService, participantService);
        participant = new Participant(1, "Dr. Jack Doe", LocalDate.of(1980, Month.APRIL, 20), AcademicRank.RESEARCH_FELLOW, "BMX", "jackDr@bmx.hu", new ArrayList<>());
        presentation = new Presentation(1, new Lecturer(), "Reset", LocalDateTime.of(
                2022, Month.SEPTEMBER, 26, 8, 0,0), new ArrayList<>());
        presentation1 = new Presentation(2, new Lecturer(), "Datas", LocalDateTime.of(2022, Month.SEPTEMBER, 26, 8, 0,0), new ArrayList<>());
        participation = new Participation(1, LocalDateTime.of(2022, Month.AUGUST, 1, 0,0,0), participant, presentation);
        createCommand = new ParticipationCreateCommand(1,1);
        participantInfo = new ParticipantInfo(1, "Dr. Jack Doe", "BMX", "jackDr@bmx.hu", AcademicRank.RESEARCH_FELLOW,  LocalDate.of(1980, Month.APRIL, 20));
        presentationInfo = new PresentationInfo(1, new LecturerListInfo(), "Reset", LocalDateTime.of(2022, Month.SEPTEMBER, 26, 8, 0,0));
        presentationInfo1 = new PresentationInfo(2, new LecturerListInfo(), "Datas", LocalDateTime.of(2022, Month.SEPTEMBER, 26, 8, 0,0));
        participationInfo = new ParticipationInfo(1, LocalDateTime.of(2022, Month.AUGUST, 1, 0,0,0), participantInfo, presentationInfo);
        participationInfo1 = new ParticipationInfo(1, LocalDateTime.of(2022, Month.AUGUST, 1, 0,0,0), participantInfo, presentationInfo1);
        updateCommand = new ParticipationUpdateCommand(2);
    }

    @Test
    @DisplayName("Participation test findAll empty")
    void testList_atStart_emptyList() {
        when(participationRepository.findAll()).thenReturn(List.of());
        assertTrue(participationService.findAll().isEmpty());
        verify(participationRepository, times(1)).findAll();
        verifyNoMoreInteractions(participationRepository);
    }

    @Test
    @DisplayName("Participation test findById")
    void testFindById_participation_success() {
        when(participationRepository.findById(1)).thenReturn(Optional.of(participation));
        ParticipationInfo byId = participationService.findById(1);
        assertEquals(participationInfo, byId);
        verify(participationRepository, times(1)).findById(1);
        verifyNoMoreInteractions(participationRepository);
    }

    @Test
    @DisplayName("Participation test findById not found")
    void testFindById_participation_notFound() {
        when(participationRepository.findById(9)).thenThrow(new ParticipationNotFoundException(9));
        assertThrows(ParticipationNotFoundException.class, () -> participationService.findById(9));
        verify(participationRepository, times(1)).findById(9);
        verifyNoMoreInteractions(participationRepository);
    }

    @Test
    @DisplayName("Participation test findAll")
    void testList_singleParticipationSaved_singleParticipationInList() {
        when(participationRepository.save(any())).thenReturn(participation);
        when(presentationService.findPresentationById(1)).thenReturn(presentation);
        when(participantService.findParticipantById(1)).thenReturn(participant);
        when(participationRepository.findAll()).thenReturn(List.of(participation));
        participationService.registrate(createCommand);
        assertThat(participationService.findAll())
                .hasSize(1);
        verify(participationRepository, times(1)).save(any());
        verify(participationRepository, times(1)).hasRegistration(participant, presentation);
        verify(presentationService, times(1)).findPresentationById(1);
        verify(participantService, times(1)).findParticipantById(1);
        verify(participationRepository, times(1)).findAll();
        verifyNoMoreInteractions(participationRepository, presentationService, participantService);
    }

    @Test
    @DisplayName("Participation test updateParticipantsPresentation")
    void testUpdateParticipantsPresentation_participation_success() {
        when(participationRepository.findById(1)).thenReturn(Optional.of(participation));
        when(presentationService.findPresentationById(updateCommand.getPresentationId())).thenReturn(presentation1);
        ParticipationInfo info = participationService.updateParticipantsPresentation(1, updateCommand);
        assertEquals(participationInfo1, info);
        verify(participationRepository, times(1)).findById(1);
        verify(presentationService, times(1)).findPresentationById(updateCommand.getPresentationId());
        verify(participationRepository, times(1)).update(participation);
        verifyNoMoreInteractions(participationRepository, presentationService);
    }

    @Test
    @DisplayName("Participation test findAllByParticipant")
    void testFindByParticipant_participation_success() {
        when(participationRepository.findByParticipant(1)).thenReturn(List.of(participation));
        List<ParticipationInfo> byParticipant = participationService.findByParticipant(1);
        assertNotNull(byParticipant);
        assertEquals(byParticipant.get(0), participationInfo);
        assertEquals(byParticipant.get(0).getParticipant().getId(), participant.getId());
        verify(participationRepository, times(1)).findByParticipant(1);
        verifyNoMoreInteractions(participationRepository);
    }

    @Test
    @DisplayName("Participation test delete")
    void testDelete_participation_success() {
        when(participationRepository.findById(1)).thenReturn(Optional.of(participation));
        participationService.deleteParticipation(1);
        verify(participationRepository, times(1)).findById(1);
        verify(participationRepository, times(1)).delete(participation);
        verifyNoMoreInteractions(participationRepository);
    }

    @Test
    @DisplayName("Participation test delete not found")
    void testDelete_participation_notFound() {
        when(participationRepository.findById(3)).thenThrow(new ParticipationNotFoundException(3));
        assertThrows(ParticipationNotFoundException.class, () -> participationService.deleteParticipation(3));
        verify(participationRepository, times(1)).findById(3);
        verifyNoMoreInteractions(participationRepository);
    }

    @Test
    @DisplayName("Participation test cancelPresentation")
    void testCancelPresentation_participation_success() {
        when(presentationService.findPresentationById(1)).thenReturn(presentation);
        when(participationRepository.findAll()).thenReturn(List.of(participation));
        participationService.cancelPresentation(1);
        assertTrue(presentation.getParticipations().isEmpty());
        verify(presentationService, times(1)).findPresentationById(1);
        verify(participationRepository, times(1)).findAll();
        verify(participationRepository, times(1)).delete(participation);
        verifyNoMoreInteractions(presentationService, participationRepository);
    }

    @Test
    @DisplayName("Participation test cancelParticipant")
    void testCancelParticipant_participation_success() {
        when(participantService.findParticipantById(1)).thenReturn(participant);
        when(participationRepository.findAll()).thenReturn(List.of(participation));
        participationService.cancelParticipant(1);
        assertTrue(participant.getParticipations().isEmpty());
        verify(participantService, times(1)).findParticipantById(1);
        verify(participationRepository, times(1)).findAll();
        verify(participationRepository, times(1)).delete(participation);
        verifyNoMoreInteractions(participantService, participationRepository);
    }
}
