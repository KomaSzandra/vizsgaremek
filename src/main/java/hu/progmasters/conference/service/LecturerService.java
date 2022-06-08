package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.Lecturer;
import hu.progmasters.conference.dto.LecturerCreateCommand;
import hu.progmasters.conference.dto.LecturerInfo;
import hu.progmasters.conference.dto.LecturerListInfo;
import hu.progmasters.conference.dto.LecturerUpdateCommand;
import hu.progmasters.conference.exceptionhandler.EmailNotValidException;
import hu.progmasters.conference.exceptionhandler.LecturerNotFoundException;
import hu.progmasters.conference.repository.LecturerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LecturerService {

    private LecturerRepository lecturerRepository;
    private ModelMapper modelMapper;

    public LecturerService(LecturerRepository lecturerRepository, ModelMapper modelMapper) {
        this.lecturerRepository = lecturerRepository;
        this.modelMapper = modelMapper;
    }

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
        } catch (Exception e) {
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

    public void deleteLecturer(Integer lecturerId) {
        Optional<Lecturer> lecturer = lecturerRepository.findById(lecturerId);
        if (lecturer.isEmpty()) {
            throw new LecturerNotFoundException(lecturerId);
        }
        Lecturer lecturerFound = lecturer.get();
        lecturerRepository.deleteLecturer(lecturerFound);
    }

    public Optional<Lecturer> findLecturerById(Integer id) {
        return lecturerRepository.findById(id);
    }

    public LecturerInfo update(Integer id, LecturerUpdateCommand command) {
        Optional<Lecturer> lecturer = lecturerRepository.findById(id);
        if(lecturer.isEmpty()) {
            throw new LecturerNotFoundException(id);
        }
        Lecturer lecturerFound = lecturer.get();
        lecturerFound.setAcademicRank(command.getAcademicRank());
        return modelMapper.map(lecturerFound, LecturerInfo.class);
    }
}
