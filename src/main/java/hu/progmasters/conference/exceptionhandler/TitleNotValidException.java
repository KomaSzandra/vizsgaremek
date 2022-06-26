package hu.progmasters.conference.exceptionhandler;

public class TitleNotValidException extends RuntimeException {

    private String title;

    public TitleNotValidException(String title) {
        this.title = title;
    }

}
