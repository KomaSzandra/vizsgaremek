package hu.progmasters.conference.controller;

import hu.progmasters.conference.dto.LecturerCreateCommand;
import hu.progmasters.conference.dto.LecturerInfo;
import hu.progmasters.conference.service.LecturerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/lecturers")
public class LecturerController {

    private static final String HTTP_RESPONSE = "Http response: %s, %s";
    private static final String LOG_GET = "Http request, GET /api/lecturers%s";
    private static final String LOG_POST = "Http request, POST /api/lecturers, body: {}";
    private static final String LOG_PUT = "Http request, PUT /api/lecturers%s, body: %s";
    private static final String LOG_DELETE = "Http request, DELETE /api/lecturers%s";

    private static final Logger LOGGER = LoggerFactory.getLogger(LecturerController.class);

    private LecturerService lecturerService;

    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @PostMapping
    public ResponseEntity<LecturerInfo> saveLecturer(@Valid @RequestBody LecturerCreateCommand command) {
        LOGGER.info(LOG_POST, String.format(command.toString()));
        LecturerInfo saved = lecturerService.save(command);
        LOGGER.info(String.format(HTTP_RESPONSE, "CREATED", saved));
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LecturerInfo> findLecturerById(@PathVariable("id") Integer id) {
        LOGGER.info(String.format(LOG_GET, "/" + id));
        LecturerInfo lecturerFound = lecturerService.findById(id);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", lecturerFound));
        return new ResponseEntity<>(lecturerFound, HttpStatus.OK);
    }
}
