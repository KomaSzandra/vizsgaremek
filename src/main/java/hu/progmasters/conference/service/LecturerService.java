package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.Lecturer;
import hu.progmasters.conference.domain.Presentation;
import hu.progmasters.conference.dto.command.LecturerCreateCommand;
import hu.progmasters.conference.dto.LecturerInfo;
import hu.progmasters.conference.dto.LecturerListInfo;
import hu.progmasters.conference.dto.command.LecturerUpdateCommand;
import hu.progmasters.conference.exceptionhandler.EmailNotValidException;
import hu.progmasters.conference.exceptionhandler.LecturerAlreadyHasAPresentationException;
import hu.progmasters.conference.exceptionhandler.LecturerNotFoundException;
import hu.progmasters.conference.exceptionhandler.PresentationNotFoundException;
import hu.progmasters.conference.repository.LecturerRepository;
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
public class LecturerService {

    private LecturerRepository lecturerRepository;
    private ModelMapper modelMapper;
    private PresentationService presentationService;


    public LecturerInfo saveLecturer(LecturerCreateCommand command) {
        Lecturer toSave = new Lecturer();
        toSave.setName(command.getName());
        toSave.setAcademicRank(command.getAcademicRank());
        toSave.setInstitution(command.getInstitution());
        toSave.setEmail(command.getEmail());
        toSave.setDateOfBirth(command.getDateOfBirth());
        Lecturer saved;
        try {
             saved = lecturerRepository.save(toSave);
             lecturerRepository.flush();
        } catch (RuntimeException e) {
            throw new EmailNotValidException(toSave.getEmail());
        }
        return modelMapper.map(saved, LecturerInfo.class);
    }

    public List<LecturerListInfo> findAllLecturer() {
        List<Lecturer> lecturers = lecturerRepository.findAll();
        return lecturers.stream()
                .map(lecturer -> modelMapper.map(lecturer, LecturerListInfo.class))
                .collect(Collectors.toList());
    }

    public LecturerInfo findById(Integer id) {
        Optional<Lecturer> lecturer = lecturerRepository.findById(id);
        if(lecturer.isPresent()) {
            return modelMapper.map(lecturer.get(), LecturerInfo.class);
        } else {
            throw new LecturerNotFoundException(id);
        }
    }

    public LecturerInfo findByName(String name) {
        return modelMapper.map(lecturerRepository.findByName(name), LecturerInfo.class);
    }

    public void deleteLecturer(Integer id) {
        Optional<Lecturer> lecturer = lecturerRepository.findById(id);
        if (lecturer.isEmpty()) {
            throw new LecturerNotFoundException(id);
        }
        Lecturer lecturerFound = lecturer.get();
        Presentation presentation = lecturerFound.getPresentation();
        if(presentation != null) {
            throw new LecturerAlreadyHasAPresentationException(presentation.getId(), id);
        }
        lecturerRepository.deleteLecturer(lecturerFound);
    }

    public Optional<Lecturer> findLecturerById(Integer id) {
        return lecturerRepository.findById(id);
    }

    public LecturerInfo addLecturerToPresentation(Integer id, LecturerUpdateCommand command) {
        Presentation presentation = presentationService.findPresentationById(id).orElseThrow(() ->
                new PresentationNotFoundException(id));
        Lecturer lecturer = lecturerRepository.findById(command.getLecturerId()).orElseThrow(() ->
                new LecturerNotFoundException(command.getLecturerId()));

        if(lecturer.getPresentation() != null || presentation.getLecturer() != null) {
            throw new LecturerAlreadyHasAPresentationException(command.getLecturerId(), id);
        }
        presentation.setLecturer(lecturer);
        lecturer.setPresentation(presentation);
        return modelMapper.map(lecturer, LecturerInfo.class);
    }
}
