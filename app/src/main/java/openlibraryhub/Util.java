package openlibraryhub;

import static java.lang.System.exit;

import java.time.LocalTime;
import java.util.InputMismatchException;

import static openlibraryhub.Console.clean;
import static openlibraryhub.Console.println;
import static openlibraryhub.Console.scanner;

import openlibraryhub.exceptions.EmptyStringException;
import openlibraryhub.exceptions.EntityNotFoundException;
import openlibraryhub.exceptions.FailedSaveException;
import openlibraryhub.exceptions.FailedUpdateException;

public class Util {
    public static String greet() {
        int hours = LocalTime.now().getHour();
        if (hours >= 6 && hours < 12) {
            return "Bom dia!";
        } else if (hours >= 12 && hours < 18) {
            return "Boa tarde!";
        } else {
            return "Boa noite!";
        }
    }

    public static void checkSystem() {
        if (Constants.DB_URL == null || Constants.DB_USER == null
                || Constants.DB_PASSWORD == null || Constants.DB_SCHEMA == null) {
            println(Errors.INVALID_DB_MESSAGE);
            exit(1);
        }
    }

    public static void handleException(Exception e) {
        clean();

        if (e instanceof InputMismatchException) {
            println(Errors.INVALID_INPUT_MESSAGE);
            scanner.next();
        } else if (e instanceof EmptyStringException) {
            println(Errors.EMPTY_INPUT_MESSAGE);
        } else if (e instanceof FailedSaveException || e instanceof FailedUpdateException
                                                    || e instanceof EntityNotFoundException) {
            println(e.getMessage() + "\n");
        } else {
            println(Errors.UNEXPECTED_ERROR_MESSAGE);
            e.printStackTrace();
            println("");
        }
    }
}
