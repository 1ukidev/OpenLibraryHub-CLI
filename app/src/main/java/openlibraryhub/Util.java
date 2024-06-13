package openlibraryhub;

import static java.lang.System.exit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.regex.Pattern;

import static openlibraryhub.Console.clean;
import static openlibraryhub.Console.println;
import static openlibraryhub.Console.scanner;

import openlibraryhub.exceptions.EmptyStringException;
import openlibraryhub.exceptions.EntityNotFoundException;
import openlibraryhub.exceptions.IllegalDateException;

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
        } else if (e instanceof EntityNotFoundException || e instanceof IllegalDateException) {
            println(e.getMessage() + "\n");
        } else {
            println(Errors.UNEXPECTED_ERROR_MESSAGE);
            e.printStackTrace();
            println("");
        }
    }

    public static boolean isDate(String date) {
        String regex = "^\\d{2}/\\d{2}/\\d{4}$";

        Pattern pattern = Pattern.compile(regex);

        if (!pattern.matcher(date).matches()) {
            return false;
        }

        return true;
    }

    public static Date convertStringToDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;

        try {
            date = formatter.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
