package hu.progmasters.conference.controller;

import hu.progmasters.conference.dto.LecturerCreateCommand;
import hu.progmasters.conference.dto.LecturerInfo;
import hu.progmasters.conference.service.LecturerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag(name = "The Controller for lecturers")
@RequestMapping("/api/lecturers")
public class LecturerController {

    private LecturerService lecturerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LecturerController.class);

    private static final String HTTP_RESPONSE = "Http response: %s, %s";
    private static final String LOG_GET = "Http request, GET /api/lecturers%s";
    private static final String LOG_POST = "Http request, POST /api/lecturers, body: {}";
    private static final String LOG_PUT = "Http request, PUT /api/lecturers%s, body: %s";
    private static final String LOG_DELETE = "Http request, DELETE /api/lecturers%s";

    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @PostMapping
    @Operation(summary = "Save a lecturer")
    @ApiResponse(responseCode = "201", description = "Lecturer has been saved")
    @ApiResponse(responseCode = "400", description = "Bad request, lecturer cannot be created")
    public ResponseEntity<LecturerInfo> saveLecturer(@Valid @RequestBody LecturerCreateCommand command) {
        LOGGER.info(LOG_POST, String.format(command.toString()));
        LecturerInfo saved = lecturerService.saveLecturer(command);
        LOGGER.info(String.format(HTTP_RESPONSE, "CREATED", saved));
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

//    @GetMapping
//    @Operation(summary = "Lists all lecturers")
//    @ApiResponse(responseCode = "200", description = "Lecturers have been listed")
//    @ApiResponse(responseCode = "400", description = "Bad request, lecturers cannot be listed")
//    public ResponseEntity<List<LecturerListInfo>> findAllLecturer() {
//        LOGGER.info(String.format(LOG_GET, ""));
//        List<LecturerListInfo> lecturerInfos = lecturerService.findAllLecturer();
//        LOGGER.info(String.format(HTTP_RESPONSE, "OK", lecturerInfos));
//        return new ResponseEntity<>(lecturerInfos, HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an exact lecturer by given id")
    @ApiResponse(responseCode = "200", description = "Lecturer has been found")
    @ApiResponse(responseCode = "400", description = "Bad request, lecturer cannot be found")
    @ApiResponse(responseCode = "404", description = "Lecturer has not been found")
    public ResponseEntity<LecturerInfo> findById(@PathVariable("id") Integer id) {
        LOGGER.info(String.format(LOG_GET, "/" + id));
        LecturerInfo byId = lecturerService.findById(id);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", byId));
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Finds the lecturer by name")
    @ApiResponse(responseCode = "200", description = "Lecturer has been found")
    @ApiResponse(responseCode = "400", description = "Bad request, lecturer cannot be found")
    @ApiResponse(responseCode = "404", description = "Lecturer has not been found")
    public ResponseEntity<LecturerInfo> findByName(@RequestParam("name") String name) {
        LOGGER.info(String.format(LOG_GET, "/" + name));
        LecturerInfo lecturerFound = lecturerService.findByName(name);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", lecturerFound));
        return new ResponseEntity<>(lecturerFound, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes an exact lecturer by given id")
    @ApiResponse(responseCode = "200", description = "Lecturer has been found")
    @ApiResponse(responseCode = "400", description = "Bad request, lecturer cannot be found")
    public ResponseEntity<Void> deleteLecturer(@PathVariable("id") Integer id) {
        LOGGER.info(String.format(LOG_DELETE, "/" + id));
        lecturerService.deleteLecturer(id);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", ""));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
