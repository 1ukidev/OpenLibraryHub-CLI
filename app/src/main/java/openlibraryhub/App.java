package openlibraryhub;

import static openlibraryhub.Console.clean;
import static openlibraryhub.Util.checkSystem;

import openlibraryhub.screens.Home;

public class App {
    public static void main(String[] args) {
        clean();
        checkSystem();
        Home.getInstance().welcome();
    }
}
