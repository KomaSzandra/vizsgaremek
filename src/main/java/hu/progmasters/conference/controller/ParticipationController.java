package hu.progmasters.conference.controller;

import hu.progmasters.conference.dto.command.ParticipationCreateCommand;
import hu.progmasters.conference.dto.ParticipationInfo;
import hu.progmasters.conference.service.ParticipationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
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
@RequestMapping("/api/participations")
@Tag(name = "Participation's controller")
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
    @Operation(summary = "Registrate participant to a presentation")
    @ApiResponse(responseCode = "201", description = "Participation has been saved")
    @ApiResponse(responseCode = "400", description = "Bad request, participation cannot be saved")
    public ResponseEntity<ParticipationInfo> registrate(@Valid @RequestBody ParticipationCreateCommand command) {
        LOGGER.info(LOG_POST, String.format(command.toString()));
        ParticipationInfo saved = participationService.registrate(command);
        LOGGER.info(String.format(HTTP_RESPONSE, "CREATED", saved));
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
