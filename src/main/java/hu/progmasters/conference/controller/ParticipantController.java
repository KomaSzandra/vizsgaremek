package hu.progmasters.conference.controller;

import hu.progmasters.conference.dto.ParticipantCreateCommand;
import hu.progmasters.conference.dto.ParticipantInfo;
import hu.progmasters.conference.service.ParticipantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    private ParticipantService participantService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LecturerController.class);

    private static final String HTTP_RESPONSE = "Http response: %s, %s";
    private static final String LOG_GET = "Http request, GET /api/participants%s";
    private static final String LOG_POST = "Http request, POST /api/participants, body: {}";
    private static final String LOG_PUT = "Http request, PUT /api/participants%s, body: %s";
    private static final String LOG_DELETE = "Http request, DELETE /api/participants%s";

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping("/{presentationId}/participants")
    public ResponseEntity<ParticipantInfo> saveParticipant(@PathVariable("presentationId") Integer presentationId, @Valid @RequestBody ParticipantCreateCommand command) {
        LOGGER.info("HTTP POST /api/presentations/" + presentationId + "/participants , command: " + command);
        ParticipantInfo saved = participantService.saveParticipant(command, presentationId);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{presentationId}/participants/{participantId}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable("presentationId") Integer presentationId,
                                                  @PathVariable("participantId") Integer participantId) {
        LOGGER.info("HTTP DELETE /api/presentations/" + presentationId + "/participants/" + participantId);
        participantService.deleteParticipant(presentationId, participantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
