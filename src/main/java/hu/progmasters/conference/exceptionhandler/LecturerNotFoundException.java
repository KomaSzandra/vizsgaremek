package hu.progmasters.conference.exceptionhandler;

public class LecturerNotFoundException extends RuntimeException {

    private final Integer id;

    public LecturerNotFoundException(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
