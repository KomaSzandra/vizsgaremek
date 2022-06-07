package hu.progmasters.conference.exceptionhandler;

public class LecturerNotFoundException extends RuntimeException {

    private Integer lecturerId;

    public LecturerNotFoundException(Integer lecturerId) {
        this.lecturerId = lecturerId;
    }

}
