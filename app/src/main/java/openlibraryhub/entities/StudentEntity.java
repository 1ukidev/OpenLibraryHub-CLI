package openlibraryhub.entities;

public class StudentEntity extends Entity {
    private Integer id;
    private String name;
    private ClassEntity classEntity;

    public StudentEntity() {}

    public StudentEntity(String name, ClassEntity classEntity) {
        this.name = name;
        this.classEntity = classEntity;
    }

    public Integer getId() {
        return id;
    }

    public StudentEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public StudentEntity setName(String name) {
        this.name = name;
        return this;
    }

    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public StudentEntity setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
        return this;
    }

    public String toString() {
        return "ID: " + id + "\n" +
               "Nome: " + name + "\n" +
               "Turma: " + classEntity.getName() + "\n";
    }
}
