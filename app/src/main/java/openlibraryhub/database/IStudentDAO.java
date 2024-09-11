package openlibraryhub.database;

import java.util.List;

import openlibraryhub.entities.StudentEntity;

public interface IStudentDAO {
    /**
     * Retrieves a student by its id.
     * 
     * @param classId
     */
    List<StudentEntity> getByClassId(Integer classId); 
}
