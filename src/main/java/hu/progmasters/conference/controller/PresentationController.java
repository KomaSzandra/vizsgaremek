package hu.progmasters.conference.controller;

import hu.progmasters.conference.dto.PresentationInfo;
import hu.progmasters.conference.dto.PresentationListItem;
import hu.progmasters.conference.dto.command.PresentationCreateCommand;
import hu.progmasters.conference.dto.command.PresentationUpdateCommand;
import hu.progmasters.conference.service.ParticipationService;
import hu.progmasters.conference.service.PresentationService;
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
@Tag(name = "The controller for presentations")
@RequestMapping("/api/presentations")
@AllArgsConstructor
public class PresentationController {

    private PresentationService presentationService;
    private ParticipationService participationService;

    private static final String HTTP_RESPONSE = "Http response: %s, %s";
    private static final String LOG_GET = "Http request, GET /api/presentations%s";
    private static final String LOG_POST = "Http request, POST /api/presentations, body: {}";
    private static final String LOG_PUT = "Http request, PUT /api/presentations%s, body: %s";
    private static final String LOG_DELETE = "Http request, DELETE /api/presentations%s";

    private static final Logger LOGGER = LoggerFactory.getLogger(LecturerController.class);


    @PostMapping
    @Operation(summary = "Save a presentation")
    @ApiResponse(responseCode = "201", description = "Presentation has been saved")
    @ApiResponse(responseCode = "400", description = "Bad request, presentation cannot be created")
    public ResponseEntity<PresentationInfo> savePresentation(@Valid @RequestBody PresentationCreateCommand command) {
        LOGGER.info(LOG_POST, command.toString());
        PresentationInfo saved = presentationService.savePresentation( command);
        LOGGER.info(String.format(HTTP_RESPONSE, "CREATED", saved));
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Lists all presentations")
    @ApiResponse(responseCode = "200", description = "Presentations have been listed")
    @ApiResponse(responseCode = "400", description = "Bad request, presentations cannot be listed")
    public ResponseEntity<List<PresentationListItem>> findAll() {
        LOGGER.info(String.format(LOG_GET, ""));
        List<PresentationListItem> presentations = presentationService.findAll();
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", presentations));
        return new ResponseEntity<>(presentations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an exact presentation by given id")
    @ApiResponse(responseCode = "200", description = "Presentation has been found")
    @ApiResponse(responseCode = "400", description = "Bad request, presentation cannot be found")
    @ApiResponse(responseCode = "404", description = "Presentation has not been found")
    public ResponseEntity<PresentationInfo> findById(@PathVariable("id") Integer id) {
        LOGGER.info(String.format(LOG_GET, "/" + id));
        PresentationInfo presentationInfo = presentationService.findById(id);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", presentationInfo));
        return new ResponseEntity<>(presentationInfo, HttpStatus.OK);
    }

    @GetMapping("findByTitle")
    @Operation(summary = "Finds the presentation by title")
    @ApiResponse(responseCode = "200", description = "Presentation has been found")
    @ApiResponse(responseCode = "400", description = "Bad request, presentation cannot be found")
    @ApiResponse(responseCode = "404", description = "Presentation has not been found")
    public ResponseEntity<PresentationInfo> findByTitle(@RequestParam("title") String title) {
        LOGGER.info(String.format(LOG_GET, "/" + title));
        PresentationInfo presentationFound = presentationService.findByTitle(title);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", presentationFound));
        return new ResponseEntity<>(presentationFound, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates presentation's start time")
    @ApiResponse(responseCode = "200", description = "Presentation has been updated")
    @ApiResponse(responseCode = "400", description = "Bad request, presentation cannot be updated")
    public ResponseEntity<PresentationInfo> updatePresentation(@Valid @PathVariable("id") Integer id,
                                                   @Valid @RequestBody PresentationUpdateCommand command) {
        LOGGER.info(String.format(LOG_PUT, "/" + id, command.toString()));
        PresentationInfo updated = presentationService.updatePresentation(id, command);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", ""));
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{presentationId}")
    @Operation(summary = "Cancellation of the presentation")
    @ApiResponse(responseCode = "200", description = "Presentation has been cancelled")
    @ApiResponse(responseCode = "400", description = "Bad request, presentation cannot be cancelled")
    public ResponseEntity<Void> cancelPresentation(@PathVariable("presentationId") Integer presentationId) {
        LOGGER.info(String.format(LOG_DELETE, "/" + presentationId));
        participationService.cancelPresentation(presentationId);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", ""));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

