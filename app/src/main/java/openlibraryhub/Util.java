package openlibraryhub;

import static java.lang.System.exit;

import java.time.LocalTime;

import static openlibraryhub.Console.println;

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
        if (Constants.DB_URL == null || Constants.DB_USER == null || Constants.DB_PASSWORD == null || Constants.DB_SCHEMA == null) {
            println("Por favor, defina as variáveis de ambiente OLH_DB_URL, OLH_DB_USER, OLH_DB_PASSWORD e OLH_DB_SCHEMA.");
            exit(1);
        }
    }
}
