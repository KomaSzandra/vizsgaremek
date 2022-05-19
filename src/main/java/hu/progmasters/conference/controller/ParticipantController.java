package hu.progmasters.conference.controller;

import hu.progmasters.conference.dto.LecturerCreateUpdateCommand;
import hu.progmasters.conference.dto.LecturerInfo;
import hu.progmasters.conference.dto.ParticipantCreateCommand;
import hu.progmasters.conference.dto.ParticipantInfo;
import hu.progmasters.conference.service.ParticipantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}


