package openlibraryhub.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static openlibraryhub.Console.clean;
import static openlibraryhub.Console.println;

import openlibraryhub.Assert;
import openlibraryhub.Constants;
import openlibraryhub.entities.StudentEntity;
import openlibraryhub.interfaces.CRUDRepository;

public class StudentRepository implements CRUDRepository<StudentEntity> {
    public StudentEntity getById(int id) {
        StudentEntity entity = null;

        try {
            Assert.notNull(id, "ID inválido!");

            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "SELECT * FROM students WHERE id = ?"; 
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int i = 0;
            pstmt.setInt(++i, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                entity = new StudentEntity();
                entity.setId(rs.getInt("id"));
                entity.setName(rs.getString("name"));
                entity.setClassEntity(ClassRepository.getInstance().getById(rs.getInt("class_id")));
            }

            pstmt.close();
            conn.close();
        } catch (SQLException | IllegalArgumentException e) {
            clean();
            e.printStackTrace();
            println("");
        }

        return entity;
    }

    public StudentEntity save(StudentEntity entity) {
        try {
            Assert.notNull(entity, "O estudante não pode ser nulo!");
            Assert.notEmpty(entity.getName(), "Nome inválido!");
            Assert.notNull(entity.getClassEntity(), "Turma inválida!");

            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "INSERT INTO students (name, class_id) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int i = 0;
            pstmt.setString(++i, entity.getName());
            pstmt.setInt(++i, entity.getClassEntity().getId());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(1));
            }

            pstmt.close();
            conn.close();
        } catch (SQLException | IllegalArgumentException e) {
            clean();
            e.printStackTrace();
            println("");
        }

        return entity;
    }

    public StudentEntity update(StudentEntity t) {
        // TODO
        return null;
    }

    public void delete(StudentEntity t) {
        // TODO
    }

    private StudentRepository() {}

    private static final StudentRepository instance = new StudentRepository();

    public static synchronized StudentRepository getInstance() {
        return instance;
    }
}
