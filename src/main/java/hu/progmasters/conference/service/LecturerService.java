package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.Lecturer;
import hu.progmasters.conference.dto.LecturerCreateCommand;
import hu.progmasters.conference.dto.LecturerInfo;
import hu.progmasters.conference.exceptionhandler.LecturerNotFoundException;
import hu.progmasters.conference.repository.LecturerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LecturerService {

    private ModelMapper modelMapper;
    private LecturerRepository lecturerRepository;

    public LecturerService(ModelMapper modelMapper, LecturerRepository lecturerRepository) {
        this.modelMapper = modelMapper;
        this.lecturerRepository = lecturerRepository;
    }

    public LecturerInfo save(LecturerCreateCommand command) {
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
}
