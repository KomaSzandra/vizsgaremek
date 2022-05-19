package hu.progmasters.conference.controller;

import hu.progmasters.conference.dto.PresentationCreateCommand;
import hu.progmasters.conference.dto.PresentationInfo;
import hu.progmasters.conference.dto.PresentationUpdateCommand;
import hu.progmasters.conference.service.PresentationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/presentations")
public class PresentationController {

    private static final String HTTP_RESPONSE = "Http response: %s, %s";
    private static final String LOG_GET = "Http request, GET /api/presentations%s";
    private static final String LOG_POST = "Http request, POST /api/presentations, body: {}";
    private static final String LOG_PUT = "Http request, PUT /api/presentations%s, body: %s";
    private static final String LOG_DELETE = "Http request, DELETE /api/presentations%s";

    private static final Logger LOGGER = LoggerFactory.getLogger(PresentationController.class);

    private PresentationService presentationService;

    public PresentationController(PresentationService presentationService) {
        this.presentationService = presentationService;
    }

    @PostMapping
    public ResponseEntity<PresentationInfo> savePresentation(@Valid @RequestBody PresentationCreateCommand command) {
        LOGGER.info(LOG_POST, String.format(command.toString()));
        PresentationInfo presentationSaved = presentationService.savePresentation(command);
        LOGGER.info(String.format(HTTP_RESPONSE, "CREATED", presentationSaved));
        return new ResponseEntity<>(presentationSaved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PresentationInfo>> findAll() {
        LOGGER.info(String.format(LOG_GET, ""));
        List<PresentationInfo> presentations = presentationService.findAll();
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", presentations));
        return new ResponseEntity<>(presentations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PresentationInfo> findById(@PathVariable("id") Integer id) {
        LOGGER.info(String.format(LOG_GET, "/" + id));
        PresentationInfo presentationFound= presentationService.findById(id);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", presentationFound));
        return new ResponseEntity<>(presentationFound, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PresentationInfo> update(@Valid @PathVariable("id") Integer id,
                                                   @Valid @RequestBody PresentationUpdateCommand command) {
        LOGGER.info(String.format(LOG_PUT, "/" + id, command.toString()));
        PresentationInfo updated = presentationService.update(id, command);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", ""));
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        LOGGER.info(String.format(LOG_DELETE, "/" + id));
        presentationService.delete(id);
        LOGGER.info(String.format(HTTP_RESPONSE, "OK", ""));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
