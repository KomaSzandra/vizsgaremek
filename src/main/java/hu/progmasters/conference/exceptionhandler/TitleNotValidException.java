package hu.progmasters.conference.exceptionhandler;

public class TitleNotValidException extends RuntimeException {

    private String title;

    public TitleNotValidException(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public TitleNotValidException setTitle(String title) {
        this.title = title;
        return this;
    }
}
