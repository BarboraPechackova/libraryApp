package cz.cvut.fel.ear.library.exceptions;

public class BookLoanDatesException extends AbstractException {
    public BookLoanDatesException(String message) {
        super(message);
    }

    public BookLoanDatesException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookLoanDatesException(Throwable cause) {
        super(cause);
    }
}
