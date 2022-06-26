package hu.progmasters.conference.exceptionhandler;

public class ParticipantsByNameNotFoundException extends RuntimeException {

    private String name;

    public ParticipantsByNameNotFoundException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
