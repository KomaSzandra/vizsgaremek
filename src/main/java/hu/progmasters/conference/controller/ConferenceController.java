package hu.progmasters.conference.controller;

import hu.progmasters.conference.dto.ConferenceCreateCommand;
import hu.progmasters.conference.dto.ConferenceInfo;
import hu.progmasters.conference.service.ConferenceService;
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
@RequestMapping("/api/conference")
public class ConferenceController {

    private ConferenceService conferenceService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceController.class);

    private static final String HTTP_RESPONSE = "Http response: %s, %s";
    private static final String LOG_GET = "Http request, GET /api/presentations%s";
    private static final String LOG_POST = "Http request, POST /api/presentations, body: {}";

    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @PostMapping
    public ResponseEntity<ConferenceInfo> saveConference(@Valid @RequestBody ConferenceCreateCommand command) {
        LOGGER.info(LOG_POST, String.format(command.toString()));
        ConferenceInfo info = conferenceService.saveConference(command);
        LOGGER.info(String.format(HTTP_RESPONSE, "CREATED", info));
        return new ResponseEntity<>(info, HttpStatus.CREATED);
    }
}
