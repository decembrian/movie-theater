package pdm.persistence.web.exception;

public class ExceptionMessage {
    private String message;

    public ExceptionMessage() {
    }

    public ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
