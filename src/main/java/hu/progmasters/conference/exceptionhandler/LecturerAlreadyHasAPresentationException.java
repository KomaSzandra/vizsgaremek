package hu.progmasters.conference.exceptionhandler;

public class LecturerAlreadyHasAPresentationException extends RuntimeException{
    private Integer presentationId;
    private Integer lecturerId;

    public LecturerAlreadyHasAPresentationException(Integer presentationId, Integer lecturerId) {
        this.presentationId = presentationId;
        this.lecturerId = lecturerId;
    }
}
