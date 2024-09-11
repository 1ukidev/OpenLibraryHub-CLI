package openlibraryhub;

import openlibraryhub.exceptions.IllegalObjectException;

public class Assert {
    /**
     * Checks if the condition is true.
     * Throws an IllegalObjectException if the condition is false.
     */
    public static void check(boolean condition, String message) {
        if (!condition) {
            throw new IllegalObjectException(message);
        }
    }
}
