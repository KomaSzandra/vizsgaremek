package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.Lecturer;
import hu.progmasters.conference.domain.Presentation;
import hu.progmasters.conference.dto.LecturerInfo;
import hu.progmasters.conference.dto.PresentationCreateCommand;
import hu.progmasters.conference.dto.PresentationInfo;
import hu.progmasters.conference.exceptionhandler.LecturerNotFoundException;
import hu.progmasters.conference.exceptionhandler.PresentationNotFoundException;
import hu.progmasters.conference.repository.LecturerRepository;
import hu.progmasters.conference.repository.PresentationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PresentationService {

    private PresentationRepository presentationRepository;
    private ModelMapper modelMapper;
    private LecturerService lecturerService;
    private LecturerRepository lecturerRepository;

    public PresentationService(PresentationRepository presentationRepository, ModelMapper modelMapper, LecturerService lecturerService, LecturerRepository lecturerRepository) {
        this.presentationRepository = presentationRepository;
        this.modelMapper = modelMapper;
        this.lecturerService = lecturerService;
        this.lecturerRepository = lecturerRepository;
    }

    public PresentationInfo savePresentation(PresentationCreateCommand command) {
        Presentation presentationToSave = modelMapper.map(command, Presentation.class);
        Optional<Lecturer> lecturerOptional = lecturerService.findLecturerById(command.getLecturerId());
        if(lecturerOptional.isEmpty()) {
            throw new LecturerNotFoundException();
        }
        presentationToSave.setLecturer(lecturerOptional.get());
        Presentation saved = presentationRepository.save(presentationToSave);
        PresentationInfo info = modelMapper.map(saved, PresentationInfo.class);
        info.setLecturerInfo(modelMapper.map(lecturerOptional.get(), LecturerInfo.class));
        return info;
    }

    public List<PresentationInfo> findAll() {
        List<Presentation> presentations = presentationRepository.findAll();
        return presentations.stream()
                .map(presentation -> modelMapper.map(presentation, PresentationInfo.class))
                .collect(Collectors.toList());
    }

    public PresentationInfo findById(Integer id) {
        Optional<Presentation> presentationOptional = presentationRepository.findPresentationById(id);
        if (presentationOptional.isEmpty()) {
            throw new PresentationNotFoundException();
        }
        return modelMapper.map(presentationOptional.get(), PresentationInfo.class);
    }
}
