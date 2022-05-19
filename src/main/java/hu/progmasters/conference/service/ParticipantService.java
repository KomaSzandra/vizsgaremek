package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.Conference;
import hu.progmasters.conference.domain.Participant;
import hu.progmasters.conference.dto.ConferenceInfo;
import hu.progmasters.conference.dto.ParticipantCreateCommand;
import hu.progmasters.conference.dto.ParticipantInfo;
import hu.progmasters.conference.exceptionhandler.ConferenceNotFoundException;
import hu.progmasters.conference.repository.ConferenceRepository;
import hu.progmasters.conference.repository.ParticipantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParticipantService {

    private ParticipantRepository participantRepository;
    private ConferenceRepository conferenceRepository;
    private ModelMapper modelMapper;

    public ParticipantService(ParticipantRepository participantRepository, ConferenceRepository conferenceRepository, ModelMapper modelMapper) {
        this.participantRepository = participantRepository;
        this.conferenceRepository = conferenceRepository;
        this.modelMapper = modelMapper;
    }

    public ParticipantInfo saveParticipant(ParticipantCreateCommand command) {
        Optional<Conference> conference = conferenceRepository.findById(command.getConferenceId());
        if (conference.isEmpty()) {
            throw new ConferenceNotFoundException();
        }
        Participant toSave = modelMapper.map(command, Participant.class);
        toSave.setConference(conference.get());
        Participant saved = participantRepository.saveParticipant(toSave);
        ParticipantInfo info = modelMapper.map(saved, ParticipantInfo.class);
        info.setConference(modelMapper.map(saved.getConference(), ConferenceInfo.class));
        return info;
    }


}
