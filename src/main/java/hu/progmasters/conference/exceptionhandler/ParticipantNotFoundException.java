package hu.progmasters.conference.exceptionhandler;

public class ParticipantNotFoundException extends RuntimeException {

    private Integer participantId;

    public ParticipantNotFoundException(Integer participantId) {
        this.participantId = participantId;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public ParticipantNotFoundException setParticipantId(Integer participantId) {
        this.participantId = participantId;
        return this;
    }
}
