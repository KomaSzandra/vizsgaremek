package hu.progmasters.conference.controller;

import hu.progmasters.conference.dto.ParticipantInfo;
import hu.progmasters.conference.dto.ParticipantListItem;
import hu.progmasters.conference.dto.command.ParticipantCreateCommand;
import hu.progmasters.conference.dto.command.ParticipantUpdateCommand;
import hu.progmasters.conference.service.ParticipantService;
import hu.progmasters.conference.service.ParticipationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "The controller for participants")
@RequestMapping("/api/participants")
public class ParticipantController {

    private ParticipantService participantService;
    private ParticipationService participationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LecturerController.class);

    private static final String HTTP_RESPONSE = "Http response: %s, %s";
    private static final String LOG_GET = "Http request, GET /api/participants%s";
    private static final String LOG_POST = "Http request, POST /api/participants, body: {}";
    private static final String LOG_PUT = "Http request, PUT /api/participants%s, body: %s";
    private static final String LOG_DELETE = "Http request, DELETE /api/participants%s";


    @PostMapping()
    @Operation(summary = "Save a participant")
    @ApiResponse(responseCode = "201", description = "Participant has been created")
    @ApiResponse(responseCode = "400", description = "Bad request, participant cannot be created")
    public ResponseEntity<ParticipantInfo> saveParticipant(@Valid @RequestBody ParticipantCreateCommand command) {
        LOGGER.info(LOG_POST, command.toString());
        ParticipantInfo saved = participantService.saveParticipant(command);
        LOGGER.info(String.format(HTTP_RESPONSE, "CREATED", saved));
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a participant by given id")
    @ApiResponse(responseCode = "200", description = "Participant has been found")
    @ApiResponse(responseCode = "400", description = "Bad request, participant cannot be found")
    @ApiResponse(responseCode = "404", description = "Participant has not been found")
    public ResponseEntity<ParticipantInfo> findById(@PathVariable("id") Integer id) {
        LOGGER.info(String.format(LOG_GET, "/" + id));
        ParticipantInfo byId = participantService.findById(id);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", byId));
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Lists all participants")
    @ApiResponse(responseCode = "200", description = "Participants have been listed")
    @ApiResponse(responseCode = "400", description = "Bad request, participants cannot be listed")
    public ResponseEntity<List<ParticipantListItem>> findAll() {
        LOGGER.info(String.format(LOG_GET, ""));
        List<ParticipantListItem> listItems = participantService.findAll();
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", listItems));
        return new ResponseEntity<>(listItems, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a participant's institution")
    @ApiResponse(responseCode = "200", description = "Participant has been updated")
    @ApiResponse(responseCode = "400", description = "Bad request, participant cannot be updated")
    public ResponseEntity<ParticipantInfo> update(@PathVariable("id") Integer id,
                                                  @Valid @RequestBody ParticipantUpdateCommand command) {
        LOGGER.info(String.format(LOG_PUT, "/" + id, command.toString()));
        ParticipantInfo info = participantService.update(id, command);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", info));
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @GetMapping("findAllByName")
    @Operation(summary = "Get participants by name")
    @ApiResponse(responseCode = "200", description = "Participants have been found")
    @ApiResponse(responseCode = "400", description = "Bad request, participants cannot be found")
    public ResponseEntity<List<ParticipantInfo>> findAllByName(@RequestParam("name") String name) {
        LOGGER.info(String.format(LOG_GET, "/" + name));
        List<ParticipantInfo> participants = participantService.findAllByName(name);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", participants));
        return new ResponseEntity<>(participants, HttpStatus.OK);
    }

    @DeleteMapping("/{participantId}")
    @Operation(summary = "Cancels all participations of the participant")
    @ApiResponse(responseCode = "200", description = "Participations has been cancelled")
    @ApiResponse(responseCode = "400", description = "Bad request, participations cannot be candelled")
    public ResponseEntity<Void> deleteParticipations(@PathVariable("participantId") Integer participantId) {
        LOGGER.info(String.format(LOG_DELETE, "/" + participantId));
        participationService.cancelParticipant(participantId);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", ""));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

