package hu.progmasters.conference.exceptionhandler;

public class LecturerNotFoundException extends RuntimeException {

    private Integer id;

    public LecturerNotFoundException(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public LecturerNotFoundException setId(Integer id) {
        this.id = id;
        return this;
    }
}
