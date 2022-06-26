package hu.progmasters.conference.exceptionhandler;

public class ParticipationNotFoundException extends RuntimeException {
    private Integer id;

    public ParticipationNotFoundException(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
