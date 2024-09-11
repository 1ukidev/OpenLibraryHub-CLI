package openlibraryhub;

import java.util.Scanner;

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
        return scanner.nextLine();
    }

    public static int readInt() {
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }
}
