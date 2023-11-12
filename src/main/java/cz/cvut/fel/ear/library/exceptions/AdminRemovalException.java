package cz.cvut.fel.ear.library.exceptions;

public class AdminRemovalException extends AbstractException {
    public AdminRemovalException(String message) {
        super(message);
    }

    public AdminRemovalException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminRemovalException(Throwable cause) {
        super(cause);
    }
}
