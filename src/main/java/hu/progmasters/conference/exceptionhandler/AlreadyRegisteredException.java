package hu.progmasters.conference.exceptionhandler;

public class AlreadyRegisteredException extends RuntimeException {
    private final Integer presentationId;
    private final Integer participantId;

    public AlreadyRegisteredException(Integer presentationId, Integer participantId) {
        //super(presentationId + "," + participantId);
        this.presentationId = presentationId;
        this.participantId = participantId;
    }

    public Integer getPresentationId() {
        return presentationId;
    }

    public Integer getParticipantId() {
        return participantId;
    }


}
