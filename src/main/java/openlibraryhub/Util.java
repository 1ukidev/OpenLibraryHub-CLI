package openlibraryhub;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    public static String formatDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatDate(LocalDate date) {
        return formatDate(date, "dd/MM/yyyy");
    }
}
