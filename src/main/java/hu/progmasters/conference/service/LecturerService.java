package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.Lecturer;
import hu.progmasters.conference.domain.Presentation;
import hu.progmasters.conference.dto.LecturerInfo;
import hu.progmasters.conference.dto.LecturerListInfo;
import hu.progmasters.conference.dto.command.LecturerCreateCommand;
import hu.progmasters.conference.dto.command.LecturerUpdateCommand;
import hu.progmasters.conference.exceptionhandler.EmailNotValidException;
import hu.progmasters.conference.exceptionhandler.LecturerAlreadyHasAPresentationException;
import hu.progmasters.conference.exceptionhandler.LecturerNotFoundException;
import hu.progmasters.conference.repository.LecturerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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
        Lecturer lecturerFound = lecturerRepository.findById(id).orElseThrow(() ->
                new LecturerNotFoundException(id));
        return modelMapper.map(lecturerFound, LecturerInfo.class);
    }

    public LecturerInfo findByName(String name) {
        return modelMapper.map(lecturerRepository.findByName(name), LecturerInfo.class);
    }

    public void deleteLecturer(Integer id) {
        Lecturer lecturerFound = lecturerRepository.findById(id).orElseThrow(() ->
                new LecturerNotFoundException(id));

        Presentation presentation = lecturerFound.getPresentation();
        if (presentation != null) {
            throw new LecturerAlreadyHasAPresentationException(presentation.getId());
        }
        lecturerRepository.deleteLecturer(lecturerFound);
    }

    public LecturerInfo addLecturerToPresentation(Integer id, LecturerUpdateCommand command) {
        Presentation presentationFound = presentationService.findPresentationById(id);
        Lecturer lecturerFound = lecturerRepository.findById(command.getLecturerId()).orElseThrow(() ->
                new LecturerNotFoundException(command.getLecturerId()));

        if (lecturerFound.getPresentation() != null || presentationFound.getLecturer() != null) {
            throw new LecturerAlreadyHasAPresentationException(id);
        }
        presentationFound.setLecturer(lecturerFound);
        lecturerFound.setPresentation(presentationFound);
        return modelMapper.map(lecturerFound, LecturerInfo.class);
    }
}
