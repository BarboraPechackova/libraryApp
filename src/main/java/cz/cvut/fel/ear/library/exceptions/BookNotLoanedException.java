package cz.cvut.fel.ear.library.exceptions;

public class BookNotLoanedException extends AbstractException {
    public BookNotLoanedException(String message) {
        super(message);
    }

    public BookNotLoanedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookNotLoanedException(Throwable cause) {
        super(cause);
    }
}
