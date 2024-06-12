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

public class StudentRepository {
    public static StudentEntity getById(int id) {
        StudentEntity studentEntity = null;

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
                studentEntity = new StudentEntity();
                studentEntity.setId(rs.getInt("id"));
                studentEntity.setName(rs.getString("name"));
                studentEntity.setClassEntity(ClassRepository.getById(rs.getInt("class_id")));
            }

            pstmt.close();
            conn.close();
        } catch (SQLException | IllegalArgumentException e) {
            clean();
            e.printStackTrace();
            println("");
        }

        return studentEntity;
    }

    public static StudentEntity save(StudentEntity student) {
        try {
            Assert.notNull(student, "O estudante não pode ser nulo!");
            Assert.notEmpty(student.getName(), "Nome inválido!");
            Assert.notNull(student.getClassEntity(), "Turma inválida!");

            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "INSERT INTO students (name, class_id) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int i = 0;
            pstmt.setString(++i, student.getName());
            pstmt.setInt(++i, student.getClassEntity().getId());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                student.setId(rs.getInt(1));
            }

            pstmt.close();
            conn.close();
        } catch (SQLException | IllegalArgumentException e) {
            clean();
            e.printStackTrace();
            println("");
        }

        return student;
    }

    public static void update(StudentEntity t) {
        // TODO
    }

    public static void delete(StudentEntity t) {
        // TODO
    }
}
