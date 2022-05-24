package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.Lecturer;
import hu.progmasters.conference.dto.LecturerCreateCommand;
import hu.progmasters.conference.dto.LecturerInfo;
import hu.progmasters.conference.dto.LecturerListInfo;
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
        Lecturer toSave = modelMapper.map(command, Lecturer.class);
        Lecturer saved = lecturerRepository.save(toSave);
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
            throw new LecturerNotFoundException();
        }
    }

    public LecturerInfo findByName(String name) {
        return modelMapper.map(lecturerRepository.findByName(name), LecturerInfo.class);
    }

    public void deleteLecturer(Integer lecturerId) {
        Optional<Lecturer> lecturer = lecturerRepository.findById(lecturerId);
        if (lecturer.isEmpty()) {
            throw new LecturerNotFoundException();
        }
        Lecturer lecturerFound = lecturer.get();
        lecturerRepository.deleteLecturer(lecturerFound);
    }
}
