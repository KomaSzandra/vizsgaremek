package hu.progmasters.conference.exceptionhandler;

public class LecturerAlreadyHasAPresentationException extends RuntimeException{
    private Integer id;

    public LecturerAlreadyHasAPresentationException(Integer id) {
        this.id = id;
    }
}
