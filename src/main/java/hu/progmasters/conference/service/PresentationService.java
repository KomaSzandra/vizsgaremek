package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.Lecturer;
import hu.progmasters.conference.domain.Participation;
import hu.progmasters.conference.domain.Presentation;
import hu.progmasters.conference.dto.PresentationInfo;
import hu.progmasters.conference.dto.PresentationListItem;
import hu.progmasters.conference.dto.command.PresentationCreateCommand;
import hu.progmasters.conference.dto.command.PresentationUpdateCommand;
import hu.progmasters.conference.exceptionhandler.LecturerAlreadyHasAPresentationException;
import hu.progmasters.conference.exceptionhandler.PresentationNotFoundException;
import hu.progmasters.conference.exceptionhandler.TitleNotValidException;
import hu.progmasters.conference.repository.PresentationRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PresentationService {

    private PresentationRepository presentationRepository;
    private ModelMapper modelMapper;


    public PresentationInfo savePresentation(PresentationCreateCommand command) {
        Presentation toSave = modelMapper.map(command, Presentation.class);

        Presentation saved;
        try {
            saved = presentationRepository.save(toSave);
            presentationRepository.flush();
        } catch (RuntimeException e) {
            throw new TitleNotValidException(command.getTitle());
        }
        return modelMapper.map(saved, PresentationInfo.class);
    }

    public PresentationInfo findById(Integer id) {
        Presentation presentationFound = presentationRepository.findById(id).orElseThrow(() ->
                new PresentationNotFoundException(id));
        return modelMapper.map(presentationFound, PresentationInfo.class);
    }

    public List<PresentationListItem> findAll() {
        return presentationRepository.findAll().stream()
                .map(presentation -> modelMapper.map(presentation, PresentationListItem.class))
                .collect(Collectors.toList());
    }

    public PresentationInfo findByTitle(String title) {
        return modelMapper.map(presentationRepository.findPresentationByTitle(title), PresentationInfo.class);
    }

    public PresentationInfo updatePresentation(Integer id, PresentationUpdateCommand command) {
        Presentation presentationFound = presentationRepository.findById(id).orElseThrow(() ->
                new PresentationNotFoundException(id));
        presentationFound.setStartTime(command.getStartTime());
        return modelMapper.map(presentationFound, PresentationInfo.class);
    }

    public Optional<Presentation> findPresentationById(Integer id) {
        return presentationRepository.findById(id);
    }

}
