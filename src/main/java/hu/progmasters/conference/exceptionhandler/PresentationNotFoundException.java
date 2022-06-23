package hu.progmasters.conference.exceptionhandler;

public class PresentationNotFoundException extends RuntimeException {

    private Integer presentationId;

    public PresentationNotFoundException(Integer presentationId) {
        this.presentationId = presentationId;
    }

    public Integer getPresentationId() {
        return presentationId;
    }

    public PresentationNotFoundException setPresentationId(Integer presentationId) {
        this.presentationId = presentationId;
        return this;
    }
}
