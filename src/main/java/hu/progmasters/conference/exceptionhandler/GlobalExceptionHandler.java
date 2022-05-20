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
    public ResponseEntity<List<ValidationError>> handleValidationException(MethodArgumentNotValidException exception) {
        List<ValidationError> validationErrors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LecturerNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleLecturerNotFoundException() {
        return new ResponseEntity<>(
                List.of(new ValidationError("lecturerId", "no lecturer found with id")),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConferenceNotFoundException.class)
    public ResponseEntity<Void> handleConferenceNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PresentationNotFoundException.class)
    public ResponseEntity<Void> handlePresentationNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ParticipantNotFoundException.class)
    public ResponseEntity<Void> handleParticipantNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
