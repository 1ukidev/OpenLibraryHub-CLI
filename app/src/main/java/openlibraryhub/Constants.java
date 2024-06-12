package openlibraryhub;

public class Constants {
    // Only for testing purposes.
    public static final String DB_URL = "jdbc:mysql://localhost:3306";
    public static final String DB_USER = "smt";
    public static final String DB_PASSWORD = "123456";
    public static final String DB_SCHEMA = "openlibraryhub";

    // Uncomment the following lines and comment the previous ones to use environment variables.
    // public static final String DB_URL = System.getenv("OLH_DB_URL");
    // public static final String DB_USER = System.getenv("OLH_DB_USER");
    // public static final String DB_PASSWORD = System.getenv("OLH_DB_PASSWORD");
    // public static final String DB_SCHEMA = System.getenv("OLH_DB_SCHEMA");

    public static final String VERSION = "1.0.0";
}
