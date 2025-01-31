package openlibraryhub;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.function.Function;

public class Console {
    private static Scanner scanner = new Scanner(System.in);

    public static void println(Object obj) {
        System.out.println(obj);
    }

    public static void print(Object obj) {
        System.out.print(obj);
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String readString() {
        String value;

        try {
            value = scanner.nextLine();
        } catch (Exception e) {
            print("Valor inválido. Digite um texto: ");
            return readString();
        }

        if (value.isEmpty()) {
            return readString();
        }

        return value;
    }

    public static int readInt() {
        return readNumber("Valor inválido. Digite um número inteiro: ", Integer::parseInt);
    }

    public static long readLong() {
        return readNumber("Valor inválido. Digite um número inteiro: ", Long::parseLong);
    }

    public static LocalDate readLocalDate() {
        String value = readString();
        LocalDate date = null;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            date = LocalDate.parse(value, formatter);
        } catch (Exception e) {
            print("Data inválida. Digite no formato [DD/MM/YYYY]: ");
            return readLocalDate();
        }

        return date;
    }

    private static <T> T readNumber(String errorMessage, Function<String, T> parser) {
        String value = readString();
        T number = null;

        try {
            number = parser.apply(value);
        } catch (Exception e) {
            print(errorMessage);
            return readNumber(errorMessage, parser);
        }

        return number;
    }
}
