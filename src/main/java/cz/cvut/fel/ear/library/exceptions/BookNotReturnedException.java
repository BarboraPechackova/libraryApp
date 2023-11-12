package cz.cvut.fel.ear.library.exceptions;

public class BookNotReturnedException extends AbstractException {

    public BookNotReturnedException(String message) {
        super(message);
    }

    public BookNotReturnedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookNotReturnedException(Throwable cause) {
        super(cause);
    }
}
