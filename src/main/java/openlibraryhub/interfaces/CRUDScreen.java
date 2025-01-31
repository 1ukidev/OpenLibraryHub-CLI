package openlibraryhub.interfaces;

public interface CRUDScreen {
    void display();
    boolean handleOption();
    void save();
    void update();
    void delete();
    void search();
    void list();
}
