package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.Conference;
import hu.progmasters.conference.dto.ConferenceCreateCommand;
import hu.progmasters.conference.dto.ConferenceInfo;
import hu.progmasters.conference.repository.ConferenceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ConferenceService {

    private ModelMapper modelMapper;
    private ConferenceRepository conferenceRepository;

    public ConferenceService(ModelMapper modelMapper, ConferenceRepository conferenceRepository) {
        this.modelMapper = modelMapper;
        this.conferenceRepository = conferenceRepository;
    }

    public ConferenceInfo saveConference(ConferenceCreateCommand command) {
        Conference conferenceToSave = modelMapper.map(command, Conference.class);
        conferenceToSave.setPresentations(new ArrayList<>());
        Conference saved = conferenceRepository.saveConference(conferenceToSave);
        return modelMapper.map(saved, ConferenceInfo.class);
    }
}
