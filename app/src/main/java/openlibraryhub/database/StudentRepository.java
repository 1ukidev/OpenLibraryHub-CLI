package openlibraryhub.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import openlibraryhub.Assert;
import openlibraryhub.Constants;
import openlibraryhub.Util;
import openlibraryhub.entities.StudentEntity;
import openlibraryhub.exceptions.IllegalObjectException;
import openlibraryhub.interfaces.CRUDRepository;

public class StudentRepository implements CRUDRepository<StudentEntity> {
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
        } catch (SQLException | IllegalObjectException e) {
            Util.handleException(e);
        }

        return entity;
    }

    public StudentEntity update(StudentEntity entity) {
        try {
            Assert.notNull(entity, "O estudante não pode ser nulo!");
            Assert.notNull(entity.getId(), "ID inválido!");
            Assert.notEmpty(entity.getName(), "Nome inválido!");
            Assert.notNull(entity.getClassEntity(), "Turma inválida!");

            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "UPDATE students SET name = ?, class_id = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int i = 0;
            pstmt.setString(++i, entity.getName());
            pstmt.setInt(++i, entity.getClassEntity().getId());
            pstmt.setInt(++i, entity.getId());
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (SQLException | IllegalObjectException e) {
            Util.handleException(e);
        }

        return entity;
    }

    public void delete(StudentEntity entity) {
        try {
            Assert.notNull(entity, "O estudante não pode ser nulo!");
            Assert.notNull(entity.getId(), "ID inválido!");

            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "DELETE FROM students WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int i = 0;
            pstmt.setInt(++i, entity.getId());
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (SQLException | IllegalObjectException e) {
            Util.handleException(e);
        }
    }

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
                entity.setId(rs.getInt("id"))
                      .setName(rs.getString("name"))
                      .setClassEntity(ClassRepository.getInstance().getById(rs.getInt("class_id")));
            }

            pstmt.close();
            conn.close();
        } catch (SQLException | IllegalObjectException e) {
            Util.handleException(e);
        }

        return entity;
    }

    public List<StudentEntity> getAll() {
        List<StudentEntity> entities = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "SELECT * FROM students";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                StudentEntity entity = new StudentEntity();
                entity.setId(rs.getInt("id"))
                      .setName(rs.getString("name"))
                      .setClassEntity(ClassRepository.getInstance().getById(rs.getInt("class_id")));
                entities.add(entity);
            }

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            Util.handleException(e);
        }

        return entities;
    }

    private StudentRepository() {}

    private static final StudentRepository instance = new StudentRepository();

    public static synchronized StudentRepository getInstance() {
        return instance;
    }
}
