package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.Participant;
import hu.progmasters.conference.domain.Participation;
import hu.progmasters.conference.domain.Presentation;
import hu.progmasters.conference.dto.ParticipationInfo;
import hu.progmasters.conference.dto.PresentationInfo;
import hu.progmasters.conference.dto.command.ParticipationCreateCommand;
import hu.progmasters.conference.exceptionhandler.AlreadyRegisteredException;
import hu.progmasters.conference.exceptionhandler.ParticipantNotFoundException;
import hu.progmasters.conference.exceptionhandler.PresentationNotFoundException;
import hu.progmasters.conference.exceptionhandler.RegistrationClosedException;
import hu.progmasters.conference.repository.ParticipationRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class ParticipationService {

    private final ModelMapper modelMapper;
    private final ParticipationRepository participationRepository;
    private final PresentationService presentationService;
    private final ParticipantService participantService;


    public ParticipationInfo registrate(ParticipationCreateCommand command) {
        Presentation presentation = presentationService.findPresentationById(command.getPresentationId())
                .orElseThrow(()-> new PresentationNotFoundException(command.getPresentationId()));
        Participant participant = participantService.findParticipantById(command.getParticipantId()).orElseThrow(()-> new ParticipantNotFoundException(command.getParticipantId()));;

        if(alreadyRegistrated(participant, presentation)) {
            throw new AlreadyRegisteredException(presentation.getId(), participant.getId());
        }
        Participation participationToSave = new Participation();
        participationToSave.setPresentation(presentation);
        participationToSave.setParticipant(participant);
        participationToSave.setRegistration(command.getRegistration());

        if(presentation.getStartTime().isBefore(command.getRegistration())) {
            throw new RegistrationClosedException(presentation.getId());
        }
        Participation participationSaved = participationRepository.save(participationToSave);

        return modelMapper.map(participationSaved, ParticipationInfo.class);
    }

    private boolean alreadyRegistrated(Participant participant, Presentation presentation) {
        return participationRepository.hasRegistration(participant, presentation);
    }
}
