package hu.progmasters.conference.controller;

import hu.progmasters.conference.dto.ConferenceCreateCommand;
import hu.progmasters.conference.dto.ConferenceInfo;
import hu.progmasters.conference.dto.ConferenceListInfo;
import hu.progmasters.conference.dto.ConferenceUpdateCommand;
import hu.progmasters.conference.service.ConferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/conferences")
public class ConferenceController {

    private ConferenceService conferenceService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceController.class);

    private static final String HTTP_RESPONSE = "Http response: %s, %s";
    private static final String LOG_GET = "Http request, GET /api/conferences%s";
    private static final String LOG_POST = "Http request, POST /api/conferences, body: {}";
    private static final String LOG_PUT = "Http request, PUT /api/conferences%s, body: %s";
    private static final String LOG_DELETE = "Http request, DELETE /api/conferences%s";

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

    @GetMapping
    public ResponseEntity<List<ConferenceListInfo>> listAllConferences() {
        LOGGER.info(String.format(LOG_GET, ""));
        List<ConferenceListInfo> conferences = conferenceService.listAllConferences();
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", conferences));
        return new ResponseEntity<>(conferences, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConferenceInfo> findConferenceById(@PathVariable("id") Integer id) {
        LOGGER.info(String.format(LOG_GET, "/" + id));
        ConferenceInfo conferenceById = conferenceService.findConferenceById(id);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", conferenceById));
        return new ResponseEntity<>(conferenceById, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConferenceInfo> updateConference(@PathVariable("id") Integer id,
                                                           @Valid @RequestBody ConferenceUpdateCommand command) {
        LOGGER.info(String.format(LOG_PUT, "/" + id, command.toString()));
        ConferenceInfo updateConference = conferenceService.updateConference(id, command);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", ""));
        return new ResponseEntity<>(updateConference, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        LOGGER.info(String.format(LOG_DELETE, "/" + id));
        conferenceService.deleteConference(id);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", ""));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
