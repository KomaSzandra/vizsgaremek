package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.Lecturer;
import hu.progmasters.conference.domain.Presentation;
import hu.progmasters.conference.dto.LecturerCreateUpdateCommand;
import hu.progmasters.conference.dto.LecturerInfo;
import hu.progmasters.conference.exceptionhandler.LecturerNotFoundException;
import hu.progmasters.conference.exceptionhandler.PresentationNotFoundException;
import hu.progmasters.conference.repository.LecturerRepository;
import hu.progmasters.conference.repository.PresentationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LecturerService {

    private ModelMapper modelMapper;
    private LecturerRepository lecturerRepository;
    private PresentationRepository presentationRepository;

    public LecturerService(ModelMapper modelMapper, LecturerRepository lecturerRepository, PresentationRepository presentationRepository) {
        this.modelMapper = modelMapper;
        this.lecturerRepository = lecturerRepository;
        this.presentationRepository = presentationRepository;
    }

    public LecturerInfo save(@Valid LecturerCreateUpdateCommand command) {
        Lecturer toSave = modelMapper.map(command, Lecturer.class);
        Lecturer saved = lecturerRepository.save(toSave);
        return modelMapper.map(saved, LecturerInfo.class);
    }

    public LecturerInfo findById(Integer id) {
        Optional<Lecturer> lecturerOptional = lecturerRepository.findById(id);
        if (lecturerOptional.isEmpty()) {
            throw new LecturerNotFoundException();
        }
        return modelMapper.map(lecturerOptional.get(), LecturerInfo.class);
    }

    public Optional<Lecturer> findLecturerById(int id) {
        return lecturerRepository.findById(id);
    }


    public List<LecturerInfo> findAll() {
        List<Lecturer> lecturers = lecturerRepository.findAll();
        return lecturers.stream()
                .map(lecturer -> modelMapper.map(lecturer, LecturerInfo.class))
                .collect(Collectors.toList());
    }

    public LecturerInfo updateLecturer(Integer id, @Valid LecturerCreateUpdateCommand command) {
        Optional<Lecturer> optionalLecturer = lecturerRepository.findById(id);
        if (optionalLecturer.isEmpty()) {
            throw new LecturerNotFoundException();
        }
        Lecturer lecturer = optionalLecturer.get();
        lecturer.setName(command.getName());
        lecturer.setInstitution(command.getInstitution());
        return modelMapper.map(optionalLecturer.get(), LecturerInfo.class);
    }

    public void delete(Integer presentationId, Integer lecturerId) {
        Optional<Presentation> presentationOptional = presentationRepository.findPresentationById(presentationId);
        if (presentationOptional.isEmpty()) {
            throw new PresentationNotFoundException();
        }

        Optional<Lecturer> optionalLecturer = lecturerRepository.findById(lecturerId);
        if (optionalLecturer.isEmpty() || ! presentationOptional.get().getLecturer().equals(optionalLecturer.get())) {
            throw new LecturerNotFoundException();
        }

        presentationRepository.deletePresentationById(presentationId);
        lecturerRepository.deleteById(lecturerId);
    }
}
