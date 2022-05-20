package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.Conference;
import hu.progmasters.conference.domain.Participant;
import hu.progmasters.conference.dto.*;
import hu.progmasters.conference.exceptionhandler.ConferenceNotFoundException;
import hu.progmasters.conference.exceptionhandler.ParticipantNotFoundException;
import hu.progmasters.conference.exceptionhandler.PresentationNotFoundException;
import hu.progmasters.conference.repository.ConferenceRepository;
import hu.progmasters.conference.repository.ParticipantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    private ParticipantRepository participantRepository;
    private ConferenceRepository conferenceRepository;
    private ModelMapper modelMapper;

    public ParticipantService(ParticipantRepository participantRepository,
                              ConferenceRepository conferenceRepository,
                              ModelMapper modelMapper) {
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

    public List<ParticipantListInfo> findAll() {
        List<Participant> participants = participantRepository.findAll();

        return participants.stream()
                .map(participant -> modelMapper.map(participant, ParticipantListInfo.class))
                .collect(Collectors.toList());
    }

    public ParticipantInfo findById(Integer id) {
        Optional<Participant> participantOptional = participantRepository.findById(id);
        if (participantOptional.isEmpty()) {
            throw new ParticipantNotFoundException();
        }
        return modelMapper.map(participantOptional.get(), ParticipantInfo.class);
    }

    public ParticipantInfo update(Integer id, ParticipantUpdateCommand command) {
        Optional<Participant> optionalParticipant = participantRepository.findById(id);
        if (optionalParticipant.isEmpty()) {
            throw new ParticipantNotFoundException();
        }
        Participant participant = optionalParticipant.get();

        Optional<Conference> optionalConference = conferenceRepository.findById(command.getConferenceId());
        if(optionalConference.isEmpty()) {
            throw  new ConferenceNotFoundException();
        }
        participant.setConference(optionalConference.get());
        return modelMapper.map(participant, ParticipantInfo.class);
    }


    public void deleteParticipant(Integer conferenceId, Integer participantId) {
        Optional<Conference> conferenceOptional = conferenceRepository.findById(conferenceId);
        if (conferenceOptional.isEmpty()) {
            throw new PresentationNotFoundException();
        }
        Optional<Participant> participant = participantRepository.findById(participantId);
        if (participant.isEmpty() || !participant.get().getConference().equals(conferenceOptional.get())) {
            throw new ParticipantNotFoundException();
        }
        participantRepository.deleteById(participantId);
    }
}
