package openlibraryhub;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

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
        while (true) {
            String value = scanner.nextLine();
            if (!value.isEmpty()) return value;
        }
    }

    public static int readInt() {
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    public static long readLong() {
        long value = scanner.nextLong();
        scanner.nextLine();
        return value;
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
}
