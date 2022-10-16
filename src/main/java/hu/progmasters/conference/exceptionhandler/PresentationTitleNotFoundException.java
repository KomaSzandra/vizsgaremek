package hu.progmasters.conference.exceptionhandler;

public class PresentationTitleNotFoundException extends RuntimeException {
    private String title;

    public PresentationTitleNotFoundException(String title) {
        this.title = title;
    }
}
