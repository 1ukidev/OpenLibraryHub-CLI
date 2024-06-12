package openlibraryhub;

import java.util.Scanner;

public class Console {
    public static void println(String s) {
        System.out.println(s);
    }

    public static void print(String s) {
        System.out.print(s);
    }

    public static void clean() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static Scanner scanner = new Scanner(System.in);
}
