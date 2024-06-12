package openlibraryhub.interfaces;

public interface CRUDScreen {
    void welcome();
    boolean handleOption();
    void save();
    void update();
    void delete();
    void search();
    void list();
}
