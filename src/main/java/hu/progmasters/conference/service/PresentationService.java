package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.Lecturer;
import hu.progmasters.conference.domain.Presentation;
import hu.progmasters.conference.dto.*;
import hu.progmasters.conference.exceptionhandler.LecturerNotFoundException;
import hu.progmasters.conference.exceptionhandler.PresentationNotFoundException;
import hu.progmasters.conference.repository.PresentationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PresentationService {

    private PresentationRepository presentationRepository;
    private ModelMapper modelMapper;
    private LecturerService lecturerService;

    public PresentationService(PresentationRepository presentationRepository, ModelMapper modelMapper, LecturerService lecturerService) {
        this.presentationRepository = presentationRepository;
        this.modelMapper = modelMapper;
        this.lecturerService = lecturerService;
    }

    public PresentationInfo savePresentation(Integer lecturerId, PresentationCreateCommand command) {
        Presentation toSave = modelMapper.map(command, Presentation.class);
        Optional<Lecturer> lecturer = lecturerService.findLecturerById(lecturerId);
        if (lecturer.isEmpty()) {
            throw new LecturerNotFoundException();
        }
        toSave.setLecturer(lecturer.get());
        Presentation saved = presentationRepository.save(toSave);
        PresentationInfo savedInfo = modelMapper.map(saved, PresentationInfo.class);
        savedInfo.setLecturer(modelMapper.map(lecturer.get(), LecturerInfo.class));
        return savedInfo;
    }

    public PresentationInfo findById(Integer id) {
        Optional<Presentation> presentation = presentationRepository.findById(id);
        if (presentation.isPresent()) {
            return modelMapper.map(presentation.get(), PresentationInfo.class);
        } else {
            throw new PresentationNotFoundException();
        }
    }

    public List<PresentationListItem> findAll() {
        List<Presentation> presentations = presentationRepository.findAll();
        return presentations.stream()
                .map(presentation -> modelMapper.map(presentation, PresentationListItem.class))
                .collect(Collectors.toList());
    }

    public PresentationInfo findByTitle(String title) {
        return modelMapper.map(presentationRepository.findByTitle(title), PresentationInfo.class);
    }

    public PresentationInfo updatePresentation(Integer id, PresentationUpdateCommand command) {
        Optional<Presentation> presentation = presentationRepository.findById(id);
        if(presentation.isEmpty()) {
            throw new PresentationNotFoundException();
        }
        Presentation presentationFound = presentation.get();
        presentationFound.setStartTime(command.getStartTime());
        return modelMapper.map(presentationFound, PresentationInfo.class);
    }

    public void deletePresentation(Integer presentationId) {
        Optional<Presentation> presentation = presentationRepository.findById(presentationId);
        if(presentation.isEmpty()) {
            throw new PresentationNotFoundException();
        }
        Presentation presentationFound = presentation.get();
        presentationRepository.delete(presentationFound);
    }

    public Optional<Presentation> findPresentationById(int id) {
        return presentationRepository.findById(id);
    }

}
