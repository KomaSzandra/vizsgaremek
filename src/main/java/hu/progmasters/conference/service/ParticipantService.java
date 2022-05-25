package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.Participant;
import hu.progmasters.conference.domain.Presentation;
import hu.progmasters.conference.dto.ParticipantCreateCommand;
import hu.progmasters.conference.dto.ParticipantInfo;
import hu.progmasters.conference.dto.PresentationInfo;
import hu.progmasters.conference.exceptionhandler.ParticipantNotFoundException;
import hu.progmasters.conference.exceptionhandler.PresentationNotFoundException;
import hu.progmasters.conference.repository.ParticipantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ParticipantService {

    private ParticipantRepository participantRepository;
    private ModelMapper modelMapper;
    private PresentationService presentationService;

    public ParticipantService(ParticipantRepository participantRepository, ModelMapper modelMapper, PresentationService presentationService) {
        this.participantRepository = participantRepository;
        this.modelMapper = modelMapper;

        this.presentationService = presentationService;
    }

    public ParticipantInfo saveParticipant(ParticipantCreateCommand command, Integer presentationId) {
        Participant toSave = modelMapper.map(command, Participant.class);
        Optional<Presentation> presentation = presentationService.findPresentationById(presentationId);
        if (presentation.isEmpty()) {
            throw new PresentationNotFoundException();
        }
        toSave.setPresentation(presentation.get());
        Participant saved = participantRepository.save(toSave);
        ParticipantInfo savedInfo = modelMapper.map(saved, ParticipantInfo.class);
        savedInfo.setPresentation(modelMapper.map(presentation.get(), PresentationInfo.class));
        return savedInfo;
    }

    public void deleteParticipant(Integer presentationId, Integer participantId) {
        Optional<Presentation> presentation = presentationService.findPresentationById(presentationId);
        if (presentation.isEmpty()) {
            throw new PresentationNotFoundException();
        }

        Optional<Participant> participant = participantRepository.findParticipantById(participantId);
        if (participant.isEmpty() || !participant.get().getPresentation().equals(presentation.get())) {
            throw new ParticipantNotFoundException();
        }
        Participant participantFound = participant.get();

        participantRepository.deleteParticipant(participantFound);
    }
}
