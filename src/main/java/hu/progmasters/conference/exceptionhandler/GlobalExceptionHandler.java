package hu.progmasters.conference.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationError>> handleValidationError(MethodArgumentNotValidException exception) {
        List<ValidationError> validationErrors = exception.getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PresentationNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handlePresentationNotFound() {
        return new ResponseEntity<>(
                List.of(new ValidationError("presentationId", "No presentation found with id")),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegistrationClosedException.class)
    public ResponseEntity<List<ValidationError>> handleRegistrationClosed() {
        return new ResponseEntity<>(
                List.of(new ValidationError("presentationId", "Registration deadline has expired!")),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParticipantNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleParticipantNotFound() {
        return new ResponseEntity<>(
                List.of(new ValidationError("participantId", "No participant found with the given id")),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LecturerNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleLecturerNotFound() {
        return new ResponseEntity<>(
                List.of(new ValidationError("id", "No lecturer found with the given id")),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParticipantsByNameNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleParticipantsByNameNotFoundException() {
        return new ResponseEntity<>(
                List.of(new ValidationError("name", "No participant found with the given name")),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotValidException.class)
    public ResponseEntity<List<ValidationError>> handleEmailNotUniqueException() {
        return new ResponseEntity<>(
                List.of(new ValidationError("email", "Email already registered")),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TitleNotValidException.class)
    public ResponseEntity<List<ValidationError>> handleTitleNotUniqueException() {
        return new ResponseEntity<>(
                List.of(new ValidationError("title", "The title already exists")),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LecturerAlreadyHasAPresentationException.class)
    public ResponseEntity<List<ValidationError>> handleLecturerHasAPresentation() {
        return new ResponseEntity<>(
                List.of(new ValidationError("presentationId", "Reserved, lecturer already has a lecture")),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyRegisteredException.class)
    public ResponseEntity<List<ValidationError>> handleAlreadyRegisteredException() {
        return new ResponseEntity<>(
                List.of(new ValidationError("participantId",
                        "Participant already registered to this presentation")),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParticipationNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleParticipationNotFoundException() {
        return new ResponseEntity<>(
                List.of(new ValidationError("participationId", "No participation found with id")),
                HttpStatus.BAD_REQUEST);
    }
}
