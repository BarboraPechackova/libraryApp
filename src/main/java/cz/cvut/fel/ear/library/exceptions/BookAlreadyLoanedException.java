package cz.cvut.fel.ear.library.exceptions;

public class BookAlreadyLoanedException extends AbstractException {
    public BookAlreadyLoanedException(String message) {
        super(message);
    }

    public BookAlreadyLoanedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookAlreadyLoanedException(Throwable cause) {
        super(cause);
    }
}
