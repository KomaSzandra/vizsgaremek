package hu.progmasters.conference.exceptionhandler;

public class AlreadyHasAParticipationException extends RuntimeException {
    private Integer id;

    public AlreadyHasAParticipationException(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
