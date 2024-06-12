package openlibraryhub.entities;

import openlibraryhub.interfaces.Entity;

public class StudentEntity extends Entity {
    private Integer id;
    private String name;
    private ClassEntity classEntity;

    public StudentEntity() {
        
    }

    public StudentEntity(String name, ClassEntity classEntity) {
        this.name = name;
        this.classEntity = classEntity;
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

    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }
}
