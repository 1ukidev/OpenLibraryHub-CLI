package openlibraryhub;

import openlibraryhub.screens.Home;

public class App {
    public static void main(String[] args) {
        Console.clear();
        Util.checkSystem();
        Home.getInstance().welcome();
    }
}
