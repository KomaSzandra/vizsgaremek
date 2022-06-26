package hu.progmasters.conference.exceptionhandler;

public class LecturerAlreadyHasAPresentationException extends RuntimeException{
    private final Integer presentationId;

    public LecturerAlreadyHasAPresentationException(Integer presentationId) {
        this.presentationId = presentationId;
    }

    public Integer getPresentationId() {
        return presentationId;
    }
}
