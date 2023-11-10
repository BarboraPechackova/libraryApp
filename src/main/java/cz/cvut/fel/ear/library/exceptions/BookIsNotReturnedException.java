package cz.cvut.fel.ear.library.exceptions;

public class BookIsNotReturnedException extends AbstractException {

    public BookIsNotReturnedException(String message) {
        super(message);
    }

    public BookIsNotReturnedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookIsNotReturnedException(Throwable cause) {
        super(cause);
    }
}
