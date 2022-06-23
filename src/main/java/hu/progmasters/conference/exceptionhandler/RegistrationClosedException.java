package hu.progmasters.conference.exceptionhandler;

public class RegistrationClosedException extends RuntimeException {

    private Integer presentationId;

    public RegistrationClosedException(Integer presentationId) {
        this.presentationId = presentationId;
    }

    public Integer getPresentationId() {
        return presentationId;
    }

    public RegistrationClosedException setPresentationId(Integer presentationId) {
        this.presentationId = presentationId;
        return this;
    }
}
