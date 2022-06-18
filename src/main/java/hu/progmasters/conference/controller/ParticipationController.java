package hu.progmasters.conference.controller;

import hu.progmasters.conference.dto.ParticipantInfo;
import hu.progmasters.conference.dto.command.ParticipationCreateCommand;
import hu.progmasters.conference.dto.ParticipationInfo;
import hu.progmasters.conference.dto.command.ParticipationUpdateCommand;
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
@RequestMapping("/api/participations")
@Tag(name = "The controller for participations")
@AllArgsConstructor
public class ParticipationController {

    private ParticipationService participationService;

    private static final String HTTP_RESPONSE = "Http response: %s, %s";
    private static final String LOG_GET = "Http request, GET /api/participations%s";
    private static final String LOG_POST = "Http request, POST /api/participations, body: {}";
    private static final String LOG_PUT = "Http request, PUT /api/participations%s, body: %s";
    private static final String LOG_DELETE = "Http request, DELETE /api/participations%s";

    private static final Logger LOGGER = LoggerFactory.getLogger(LecturerController.class);


    @PostMapping
    @Operation(summary = "Registrate a participant to a presentation")
    @ApiResponse(responseCode = "201", description = "Participation has been saved")
    @ApiResponse(responseCode = "400", description = "Bad request, participation cannot be saved")
    public ResponseEntity<ParticipationInfo> registrate(@Valid @RequestBody ParticipationCreateCommand command) {
        LOGGER.info(LOG_POST, String.format(command.toString()));
        ParticipationInfo saved = participationService.registrate(command);
        LOGGER.info(String.format(HTTP_RESPONSE, "CREATED", saved));
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Lists all participations")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "Participations have been listed")
    @ApiResponse(responseCode = "400", description = "Bad request, participations cannot be listed")
    public List<ParticipationInfo> findAll() {
        LOGGER.info(String.format(LOG_GET, ""));
        List<ParticipationInfo> infos = participationService.findAll();
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", infos));
        return infos;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a participation by given id")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "Participation has been found")
    @ApiResponse(responseCode = "400", description = "Bad request, participation cannot be found")
    public ParticipationInfo findById(@PathVariable("id") Integer id) {
        LOGGER.info(String.format(LOG_GET, "/" + id));
        ParticipationInfo byId = participationService.findById(id);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", byId));
        return byId;
    }

    @GetMapping("findAllByParticipant")
    @Operation(summary = "Get participations by participant")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "Participations have been listed")
    @ApiResponse(responseCode = "400", description = "Bad request, participations cannot be listed")
    public List<ParticipationInfo> findAllByParticipant(@RequestParam("participantId") Integer participantId) {
        LOGGER.info(String.format(LOG_GET, "/" + participantId));
        List<ParticipationInfo> participations = participationService.findByParticipant(participantId);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", participations));
        return participations;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a participation")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "Participations has been deleted")
    @ApiResponse(responseCode = "400", description = "Bad request, participations cannot be deleted")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        LOGGER.info(String.format(LOG_DELETE, "/" + id));
        participationService.deleteParticipation(id);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", ""));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Transferring a participant's participation to another presentation")
    @ApiResponse(responseCode = "200", description = "Participation has been updated")
    @ApiResponse(responseCode = "400", description = "Bad request, participation cannot be updated")
    public ParticipationInfo updateParticipantsPresentation(@PathVariable("id") Integer id,
                                                          @RequestBody ParticipationUpdateCommand command) {
        LOGGER.info(String.format(LOG_PUT, "/" + id, command.toString()));
        return participationService.updateParticipantsPresentation(id, command);
    }
}
