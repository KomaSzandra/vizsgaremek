package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.AcademicRank;
import hu.progmasters.conference.domain.Participant;
import hu.progmasters.conference.dto.ParticipantInfo;
import hu.progmasters.conference.dto.ParticipationInfo;
import hu.progmasters.conference.dto.command.ParticipantCreateCommand;
import hu.progmasters.conference.dto.command.ParticipantUpdateCommand;
import hu.progmasters.conference.exceptionhandler.EmailNotValidException;
import hu.progmasters.conference.exceptionhandler.ParticipantNotFoundException;
import hu.progmasters.conference.exceptionhandler.ParticipantsByNameNotFoundException;
import hu.progmasters.conference.repository.ParticipantRepository;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
public class ParticipantServiceTest {

    @Mock
    ParticipantRepository participantRepository;

    @InjectMocks
    ParticipantService participantService;

    private final ModelMapper modelMapper = new ModelMapper();


    private Participant participant;
    private Participant participant1;
    private ParticipantCreateCommand createCommand;
    private ParticipantCreateCommand createCommand1;
    private ParticipantInfo participantInfo;
    private ParticipantInfo participantInfo1;
    private ParticipantUpdateCommand updateCommand;

    @BeforeEach
    void init() {
        participantService = new ParticipantService(participantRepository, modelMapper);
        participant = new Participant(1, "Dr. Jack Doe", LocalDate.of(1980, Month.APRIL, 20), AcademicRank.RESEARCH_FELLOW, "BMX", "jackDr@bmx.hu", new ArrayList<>());
        participant1 = new Participant(2, "Dr. Jane Doe", LocalDate.of(1980, Month.APRIL, 20), AcademicRank.RESEARCH_FELLOW, "BMX", "jackDr@bmx.hu", new ArrayList<>());
        createCommand = new ParticipantCreateCommand("Dr. Jack Doe", LocalDate.of(1980, Month.APRIL, 20), AcademicRank.RESEARCH_FELLOW, "BMX", "jackDr@bmx.hu");
        createCommand1 = new ParticipantCreateCommand("Dr. Jane Doe", LocalDate.of(1980, Month.APRIL, 20), AcademicRank.RESEARCH_FELLOW, "BMX", "jackDr@bmx.hu");
        participantInfo = new ParticipantInfo(1, "Dr. Jack Doe", "BMX", "jackDr@bmx.hu", AcademicRank.RESEARCH_FELLOW,  LocalDate.of(1980, Month.APRIL, 20));
        participantInfo1 = new ParticipantInfo(1, "Dr. Jack Doe", "BME", "jackDr@bmx.hu", AcademicRank.RESEARCH_FELLOW,  LocalDate.of(1980, Month.APRIL, 20));
        updateCommand = new ParticipantUpdateCommand("BME");
    }

    @Test
    @DisplayName("Participant test findAll empty")
    void testFindAll_participant_emptyList() {
        when(participantRepository.findAll()).thenReturn(List.of());
        assertThat(participantService.findAll()).isEmpty();
        verify(participantRepository, times(1)).findAll();
        verifyNoMoreInteractions(participantRepository);
    }

    @Test
    @DisplayName("Participant test saveParticipant")
    void testSaveParticipant_participant_success () {
        when(participantRepository.save(any())).thenReturn(participant);
        ParticipantInfo saved = participantService.saveParticipant(createCommand);
        assertEquals(participantInfo, saved);
        verify(participantRepository, times(1)).save(any());
        verify(participantRepository, times(1)).flush();
        verifyNoMoreInteractions(participantRepository);
    }

    @Test
    @DisplayName("Participant test saveParticipant with invalid email")
    void testSaveParticipant_invalidEmail() {
        when(participantRepository.save(participant1)).thenThrow(new EmailNotValidException(participant1.getEmail()));
        assertThrows(EmailNotValidException.class, () -> participantService.saveParticipant(createCommand1));
        verify(participantRepository, times(1)).save(any());
        verifyNoMoreInteractions(participantRepository);
    }

    @Test
    @DisplayName("Participant test findAll")
    void testList_singleParticipantSaved_singleParticipantInList() {
        when(participantRepository.save(any())).thenReturn(participant);
        when(participantRepository.findAll()).thenReturn(List.of(participant));
        participantService.saveParticipant(createCommand);
        assertThat(participantService.findAll())
                .hasSize(1);
        verify(participantRepository, times(1)).save(any());
        verify(participantRepository, times(1)).flush();
        verify(participantRepository, times(1)).findAll();
        verifyNoMoreInteractions(participantRepository);
    }

    @Test
    @DisplayName("Participant test findById")
    void testFindById_participant_successfulFind() {
        when(participantRepository.findById(1)).thenReturn(Optional.of(participant));
        assertEquals(participantService.findById(1), participantInfo);
        verify(participantRepository, times(1)).findById(1);
        verifyNoMoreInteractions(participantRepository);
    }

    @Test
    @DisplayName("Participant test findById not found")
    void testFindById_participant_notFound() {
        when(participantRepository.findById(9)).thenThrow(new ParticipantNotFoundException(9));
        assertThrows(ParticipantNotFoundException.class, () -> participantService.findById(9));
        verify(participantRepository, times(1)).findById(9);
        verifyNoMoreInteractions(participantRepository);
    }

    @Test
    @DisplayName("Participant test findParticipantById")
    void testFindParticipantById_participant_success() {
        when(participantRepository.findById(1)).thenReturn(Optional.of(participant));
        assertEquals(participantService.findParticipantById(1), participant);
        verify(participantRepository, times(1)).findById(1);
        verifyNoMoreInteractions(participantRepository);
    }

    @Test
    @DisplayName("Participant test findParticipantById not found")
    void testFindParticipantById_participant_notFound() {
        when(participantRepository.findById(9)).thenThrow(new ParticipantNotFoundException(9));
        assertThrows(ParticipantNotFoundException.class, () -> participantService.findParticipantById(9));
        verify(participantRepository, times(1)).findById(9);
        verifyNoMoreInteractions(participantRepository);
    }

    @Test
    @DisplayName("Participant test update")
    void testUpdateParticipant_institution_success() {
        when(participantRepository.findById(1)).thenReturn(Optional.of(participant));
        ParticipantInfo info = participantService.update(1, updateCommand);
        assertEquals(participantInfo1, info);
        verify(participantRepository, times(1)).findById(1);
        verifyNoMoreInteractions(participantRepository);
    }

    @Test
    @DisplayName("Participant test update not found")
    void testUpdateParticipant_participant_notFound() {
        when(participantRepository.findById(8)).thenThrow(new ParticipantNotFoundException(8));
        assertThrows(ParticipantNotFoundException.class, () -> participantService.findById(8));
        verify(participantRepository, times(1)).findById(8);
        verifyNoMoreInteractions(participantRepository);
    }

    @Test
    @DisplayName("Participant test findAllByName")
    void testFindAllByName_participant_success() {
        when(participantRepository.findAllByName("Dr. Jack Doe")).thenReturn(List.of(participant));
        List<ParticipantInfo> byName = participantService.findAllByName("Dr. Jack Doe");
        assertNotNull(byName);
        assertEquals(byName.get(0), participantInfo);
        assertEquals(byName.get(0).getId(), participant.getId());
        verify(participantRepository, times(1)).findAllByName("Dr. Jack Doe");
        verifyNoMoreInteractions(participantRepository);
    }

    @Test
    @DisplayName("Participant test findAllByName not found")
    void testFindAllByName_participant_notFound() {
        when(participantRepository.findAllByName("Dr. Jack")).thenThrow(new ParticipantsByNameNotFoundException("Dr. Jack"));
        assertThrows(ParticipantsByNameNotFoundException.class, () -> participantService.findAllByName("Dr. Jack"));
        verify(participantRepository, times(1)).findAllByName("Dr. Jack");
        verifyNoMoreInteractions(participantRepository);
    }

}
