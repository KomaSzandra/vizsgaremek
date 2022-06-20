package hu.progmasters.conference.exceptionhandler;

public class RegistrationClosedException extends RuntimeException {

    private Integer presentationId;

    public RegistrationClosedException(Integer presentationId) {
        this.presentationId = presentationId;
    }
}
