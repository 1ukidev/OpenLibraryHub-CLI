package openlibraryhub.exceptions;

public class IllegalDateException extends RuntimeException {
    public IllegalDateException() {
        super("Data inválida!");
    }
}
