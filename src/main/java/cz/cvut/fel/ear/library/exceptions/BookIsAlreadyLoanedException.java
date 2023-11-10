package cz.cvut.fel.ear.library.exceptions;

public class BookIsAlreadyLoanedException extends AbstractException {
    public BookIsAlreadyLoanedException(String message) {
        super(message);
    }

    public BookIsAlreadyLoanedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookIsAlreadyLoanedException(Throwable cause) {
        super(cause);
    }
}
