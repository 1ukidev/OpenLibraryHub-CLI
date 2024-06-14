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
     * Saves a new entity.
     */
    void save();

    /**
     * Updates an existing entity.
     */
    void update();

    /**
     * Deletes an existing entity.
     */
    void delete();

    /**
     * Searches for a specific entity.
     */
    void search();

    /**
     * Lists all entities.
     */
    void list();
}
