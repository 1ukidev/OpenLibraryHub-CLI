package openlibraryhub.interfaces;

public interface Screen {
    /**
     * Displays the options menu and manages user interaction with the system.
     */
    void welcome();

    /**
     * Handles the option selected by the user and executes the corresponding action.
     * @return true if the menu should continue to be displayed, false if it should return to previous menu.
     */
    boolean handleOption();
}
