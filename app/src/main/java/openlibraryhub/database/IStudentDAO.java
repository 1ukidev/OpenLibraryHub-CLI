package openlibraryhub.database;

import java.util.List;

import openlibraryhub.entities.StudentEntity;

public interface IStudentDAO {
    /**
     * Retrieves a student by its id.
     * 
     * @param classId
     * @return the student with the given id, or null if it does not exist.
     */
    List<StudentEntity> getByClassId(Integer classId); 
}
