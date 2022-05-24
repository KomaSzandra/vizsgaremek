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
                List.of(new ValidationError("presentationId", "no presentation found with id")),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParticipantNotFoundException.class)
    public ResponseEntity<Void> handleParticipantNotFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LecturerNotFoundException.class)
    public ResponseEntity<Void> handleLecturerNotFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
