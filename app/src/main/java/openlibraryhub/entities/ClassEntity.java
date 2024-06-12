package openlibraryhub.entities;

public class ClassEntity extends Entity {
    private Integer id;
    private String name;

    public ClassEntity() {}

    public ClassEntity(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "ID: " + id + "\n" +
               "Nome: " + name + "\n";
    }
}
