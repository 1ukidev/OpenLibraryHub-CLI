package openlibraryhub;

import openlibraryhub.exceptions.IllegalObjectException;

public class Assert {
    /**
     * Checks if the condition is true.
     * Throws an IllegalObjectException if the condition is false.
     * 
     * @param condition The condition to be checked.
     * @param message The message to be shown in the exception.
     */
    public static void check(boolean condition, String message) {
        if (!condition) {
            throw new IllegalObjectException(message);
        }
    }
}
