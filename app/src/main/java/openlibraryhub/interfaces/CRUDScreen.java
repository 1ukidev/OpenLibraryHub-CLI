package openlibraryhub.interfaces;

public interface CRUDScreen {
    /**
     * Displays the options menu and manages user interaction with the system.
     */
    void welcome();

    /** 
     * Handles the option selected by the user and executes the corresponding action.
     * @return true if the menu should continue to be displayed, false if it should return to previous menu.
     */
    boolean handleOption();

    /**
     * Saves a new entity to the repository.
     */
    void save();

    /**
     * Updates an existing entity in the repository.
     */
    void update();

    /**
     * Deletes an existing entity from the repository.
     */
    void delete();

    /**
     * Searches for a specific entity in the repository.
     */
    void search();

    /**
     * Lists all entities in the repository.
     */
    void list();
}
