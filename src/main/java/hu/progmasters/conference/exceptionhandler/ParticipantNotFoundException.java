package hu.progmasters.conference.exceptionhandler;

public class ParticipantNotFoundException extends RuntimeException {

    private Integer participantId;

    public ParticipantNotFoundException(Integer participantId) {
        this.participantId = participantId;
    }

}
