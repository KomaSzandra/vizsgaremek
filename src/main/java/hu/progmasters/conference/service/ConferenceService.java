package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.Conference;
import hu.progmasters.conference.dto.ConferenceCreateCommand;
import hu.progmasters.conference.dto.ConferenceInfo;
import hu.progmasters.conference.dto.ConferenceListInfo;
import hu.progmasters.conference.dto.ConferenceUpdateCommand;
import hu.progmasters.conference.exceptionhandler.ConferenceNotFoundException;
import hu.progmasters.conference.repository.ConferenceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<ConferenceListInfo> listAllConferences() {
        List<Conference> conferences = conferenceRepository.listAllConferences();
        return conferences.stream()
                .map(conference -> modelMapper.map(conference, ConferenceListInfo.class))
                .collect(Collectors.toList());
    }

    public ConferenceInfo findConferenceById(Integer id) {
        Optional<Conference> conferenceOptional = conferenceRepository.findById(id);
        if (conferenceOptional.isEmpty()) {
            throw new ConferenceNotFoundException();
        }
        return modelMapper.map(conferenceOptional.get(), ConferenceInfo.class);
    }

    public ConferenceInfo updateConference(Integer id, ConferenceUpdateCommand command) {
        Optional<Conference> optionalConference = conferenceRepository.findById(id);
        if (optionalConference.isEmpty()) {
            throw new ConferenceNotFoundException();
        }
        Conference conference = optionalConference.get();
        conference.setLocalDate(command.getLocalDate());
        return modelMapper.map(optionalConference.get(), ConferenceInfo.class);
    }

    public void deleteConference(Integer id) {
        Optional<Conference> optionalConference = conferenceRepository.findById(id);
        if (optionalConference.isEmpty()) {
            throw new ConferenceNotFoundException();
        }
        conferenceRepository.deleteConferenceById(id);
    }
}
