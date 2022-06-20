package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.Participant;
import hu.progmasters.conference.dto.ParticipantInfo;
import hu.progmasters.conference.dto.ParticipantListItem;
import hu.progmasters.conference.dto.command.ParticipantCreateCommand;
import hu.progmasters.conference.dto.command.ParticipantUpdateCommand;
import hu.progmasters.conference.exceptionhandler.EmailNotValidException;
import hu.progmasters.conference.exceptionhandler.ParticipantNotFoundException;
import hu.progmasters.conference.exceptionhandler.ParticipantsByNameNotFoundException;
import hu.progmasters.conference.repository.ParticipantRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final ModelMapper modelMapper;


    public ParticipantInfo saveParticipant(ParticipantCreateCommand command) {
        Participant toSave = modelMapper.map(command, Participant.class);
        Participant saved;
        try {
            saved = participantRepository.save(toSave);
            participantRepository.flush();
        } catch (RuntimeException e) {
            throw new EmailNotValidException(toSave.getEmail());
        }
        return modelMapper.map(saved, ParticipantInfo.class);
    }

    public ParticipantInfo findById(Integer id) {
        Participant participantById = participantRepository.findById(id).orElseThrow(()
                -> new ParticipantNotFoundException(id));
        return modelMapper.map(participantById, ParticipantInfo.class);
    }

    public List<ParticipantListItem> findAll() {
        List<Participant> participants = participantRepository.findAll();
        return participants.stream()
                .map(participant -> modelMapper.map(participant, ParticipantListItem.class))
                .collect(Collectors.toList());
    }

    public ParticipantInfo update(Integer id, ParticipantUpdateCommand command) {
        Participant participantById = participantRepository.findById(id).orElseThrow(()
                -> new ParticipantNotFoundException(id));
        participantById.setInstitution(command.getInstitution());
        return modelMapper.map(participantById, ParticipantInfo.class);
    }

    public List<ParticipantInfo> findAllByName(String name) {
        List<Participant> participants = participantRepository.findAllByName(name);
        if (!participants.isEmpty()) {
            return participants.stream()
                    .map(participant -> modelMapper.map(participant, ParticipantInfo.class))
                    .collect(Collectors.toList());
        } else {
            throw new ParticipantsByNameNotFoundException(name);
        }
    }

    public Participant findParticipantById(Integer id) {
        return participantRepository.findById(id).orElseThrow(()
                -> new ParticipantNotFoundException(id));
    }
}
