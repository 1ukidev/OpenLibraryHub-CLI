package openlibraryhub;

import java.util.Date;
import java.util.Scanner;

import openlibraryhub.exceptions.EmptyStringException;
import openlibraryhub.exceptions.IllegalDateException;

public class Console {
    private static Scanner scanner = new Scanner(System.in);

    public static void println(Object object) {
        System.out.println(object.toString());
    }

    public static void print(Object object) {
        System.out.print(object.toString());
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String read() {
        String result = scanner.nextLine();
        if (result == null || result.isEmpty()) {
            throw new EmptyStringException();
        }
        return result;
    }

    public static int readInt() {
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

    public static Date readDate() {
        String result = scanner.nextLine();

        if (result == null || result.isEmpty()) {
            throw new EmptyStringException();
        }
        if (!Util.isDate(result)) {
            throw new IllegalDateException();
        }

        Date date = Util.convertStringToDate(result);
        if (date == null) {
            throw new IllegalDateException();
        }

        return date;
    }
}
