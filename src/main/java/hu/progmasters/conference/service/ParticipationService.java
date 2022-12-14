package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.Participant;
import hu.progmasters.conference.domain.Participation;
import hu.progmasters.conference.domain.Presentation;
import hu.progmasters.conference.dto.ParticipationInfo;
import hu.progmasters.conference.dto.command.ParticipationCreateCommand;
import hu.progmasters.conference.dto.command.ParticipationUpdateCommand;
import hu.progmasters.conference.exceptionhandler.*;
import hu.progmasters.conference.repository.ParticipationRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ParticipationService {

    private final ModelMapper modelMapper;
    private final ParticipationRepository participationRepository;
    private final PresentationService presentationService;
    private final ParticipantService participantService;


    public ParticipationInfo registrate(ParticipationCreateCommand command) {
        Presentation presentation = presentationService.findPresentationById(command.getPresentationId());
        Participant participant = participantService.findParticipantById(command.getParticipantId());

        if(alreadyRegistrated(participant, presentation)) {
            throw new AlreadyRegisteredException(presentation.getId(), participant.getId());
        }
        Participation participationToSave = new Participation();
        participationToSave.setPresentation(presentation);
        participationToSave.setParticipant(participant);
        participationToSave.setRegistration(LocalDateTime.now());
        if(presentation.getStartTime().isBefore(participationToSave.getRegistration())) {
            throw new RegistrationClosedException(presentation.getId());
        }

        Participation participationSaved = participationRepository.save(participationToSave);
        return modelMapper.map(participationSaved, ParticipationInfo.class);
    }

    private boolean alreadyRegistrated(Participant participant, Presentation presentation) {
        return participationRepository.hasRegistration(participant, presentation);
    }

    public List<ParticipationInfo> findAll() {
        List<Participation> participations = participationRepository.findAll();
        return participations.stream()
                .map(participation -> modelMapper.map(participation, ParticipationInfo.class))
                .collect(Collectors.toList());
    }

    public ParticipationInfo findById(Integer id) {
        Participation participationById = getParticipation(id);
        return modelMapper.map(participationById, ParticipationInfo.class);
    }

    private Participation getParticipation(Integer id) {
        return participationRepository.findById(id)
                .orElseThrow(() -> new ParticipationNotFoundException(id));
    }

    public List<ParticipationInfo> findByParticipant(Integer participantId) {
        participantService.findParticipantById(participantId);
        List<Participation> byParticipant = participationRepository.findByParticipant(participantId);
        return byParticipant.stream()
                .map(participation -> modelMapper.map(participation, ParticipationInfo.class))
                .collect(Collectors.toList());
    }

    public void deleteParticipation(Integer participationId) {
        Participation participation = getParticipation(participationId);
        participationRepository.delete(participation);
    }

    public void cancelParticipant(Integer participantId) {
        participantService.findParticipantById(participantId);
        for (Participation participation : participationRepository.findAll()) {
            if(participation.getParticipant().getId().equals(participantId)) {
                participationRepository.delete(participation);
            }
        }
    }

    public void cancelPresentation(Integer presentationId) {
        Presentation presentation = presentationService.findPresentationById(presentationId);
        presentation.setLecturer(null);
        for (Participation participation : participationRepository.findAll()) {
            if(participation.getPresentation().getId().equals(presentationId)) {
                participationRepository.delete(participation);
            }
        }
    }

    public ParticipationInfo updateParticipantsPresentation(Integer participationId, ParticipationUpdateCommand command) {
        Participation participation = getParticipation(participationId);
        Presentation presentation = presentationService.findPresentationById(command.getPresentationId());
        participation.setPresentation(presentation);
        return modelMapper.map(participation, ParticipationInfo.class);
    }
}
