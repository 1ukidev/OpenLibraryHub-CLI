package openlibraryhub.exceptions;

import openlibraryhub.Errors;

public class IllegalDateException extends RuntimeException {
    public IllegalDateException() {
        super(Errors.INVALID_DATE_MESSAGE);
    }
}
