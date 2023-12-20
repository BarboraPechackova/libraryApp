package cz.cvut.fel.ear.library.exceptions;

public class BookAlreadyReturnedException extends AbstractException {
    public BookAlreadyReturnedException(String message) {
        super(message);
    }

    public BookAlreadyReturnedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookAlreadyReturnedException(Throwable cause) {
        super(cause);
    }
}
