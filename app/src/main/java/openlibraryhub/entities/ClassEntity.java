package openlibraryhub.entities;

import openlibraryhub.annotations.EntityName;

@EntityName("Turma")
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

    public ClassEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ClassEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String toString() {
        String id = getId() != null ? getId().toString() : "N/A";
        String name = getName() != null ? getName() : "N/A";
        return String.format("Id: %s\nNome: %s\n", id, name);
    }
}
