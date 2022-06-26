package hu.progmasters.conference.exceptionhandler;

public class PresentationNotFoundException extends RuntimeException {

    private final Integer presentationId;

    public PresentationNotFoundException(Integer presentationId) {
        this.presentationId = presentationId;
    }

    public Integer getPresentationId() {
        return presentationId;
    }
}
