package openlibraryhub;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.function.Function;

public class Console {
    private static Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
        while (true) {
            try {
                String value = scanner.nextLine().trim();

                if (!value.isEmpty()) {
                    return value;
                }

                print("Entrada vazia. Digite um texto: ");
            } catch (Exception e) {
                print("Valor inválido. Digite um texto: ");
                scanner.next();
            }
        }
    }

    public static int readInt() {
        return readNumber("Valor inválido. Digite um número inteiro: ", Integer::parseInt);
    }

    public static long readLong() {
        return readNumber("Valor inválido. Digite um número inteiro: ", Long::parseLong);
    }

    public static LocalDate readLocalDate() {
        while (true) {
            try {
                String value = readString();
                return LocalDate.parse(value, formatter);
            } catch (DateTimeParseException e) {
                print("Data inválida. Digite no formato [DD/MM/YYYY]: ");
            }
        }
    }

    private static <T extends Number> T readNumber(String errorMessage, Function<String, T> parser) {
        while (true) {
            try {
                String value = readString();
                return parser.apply(value);
            } catch (Exception e) {
                print(errorMessage);
            }
        }
    }
}
