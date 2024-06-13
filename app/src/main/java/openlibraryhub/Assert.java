package openlibraryhub;

import openlibraryhub.exceptions.IllegalObjectException;

public class Assert {
    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalObjectException(message);
        }
    }

    public static void notEmpty(String str, String message) {
        if (str == null || str.isEmpty()) {
            throw new IllegalObjectException(message);
        }
    }
}
