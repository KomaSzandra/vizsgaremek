package hu.progmasters.conference.controller;

import hu.progmasters.conference.dto.*;
import hu.progmasters.conference.service.ParticipantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Path;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    private ParticipantService participantService;

    private static final String HTTP_RESPONSE = "Http response: %s, %s";
    private static final String LOG_GET = "Http request, GET /api/participants%s";
    private static final String LOG_POST = "Http request, POST /api/participants, body: {}";
    private static final String LOG_PUT = "Http request, PUT /api/participants%s, body: %s";
    private static final String LOG_DELETE = "Http request, DELETE /api/participants%s";

    private static final Logger LOGGER = LoggerFactory.getLogger(LecturerController.class);

    @PostMapping
    public ResponseEntity<ParticipantInfo> saveParticipant(@Valid @RequestBody ParticipantCreateCommand command) {
        LOGGER.info(LOG_POST, String.format(command.toString()));
        ParticipantInfo participant = participantService.saveParticipant(command);
        LOGGER.info(String.format(HTTP_RESPONSE, "CREATED", participant));
        return new ResponseEntity<>(participant, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ParticipantListInfo>> listParticipants() {
        LOGGER.info(String.format(LOG_GET, ""));
        List<ParticipantListInfo> participants = participantService.findAll();
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", participants));
        return new ResponseEntity<>(participants, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantInfo> findParticipantById(@PathVariable("id") Integer id) {
        LOGGER.info(String.format(LOG_GET, "/" + id));
        ParticipantInfo byId = participantService.findById(id);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", byId));
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipantInfo> updateParticipant(@PathVariable("id") Integer id,
                                                         @Valid @RequestBody ParticipantUpdateCommand command) {
        LOGGER.info(String.format(LOG_PUT, "/" + id, command.toString()));
        ParticipantInfo participantModified = participantService.update(id, command);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", participantModified));
        return new ResponseEntity<>(participantModified, HttpStatus.OK);
    }

    @DeleteMapping("/{participantId}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable("participantId") Integer participantId, Integer conferenceId) {
        LOGGER.info(String.format(LOG_DELETE, "/" + participantId));
        participantService.deleteParticipant(conferenceId, participantId);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", ""));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


