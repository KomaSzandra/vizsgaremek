package hu.progmasters.conference.exceptionhandler;

public class ParticipationNotFoundException extends RuntimeException {
    private Integer id;

    public ParticipationNotFoundException(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public ParticipationNotFoundException setId(Integer id) {
        this.id = id;
        return this;
    }
}
