package openlibraryhub.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static openlibraryhub.Constants.DB_PASSWORD;
import static openlibraryhub.Constants.DB_URL;
import static openlibraryhub.Constants.DB_USER;

import openlibraryhub.Assert;
import openlibraryhub.Util;
import openlibraryhub.entities.StudentEntity;
import openlibraryhub.exceptions.IllegalObjectException;
import openlibraryhub.interfaces.DAO;

public class StudentDAO implements DAO<StudentEntity>, IStudentDAO {
    private static final String NULL_STUDENT = "Estudante inválido!";
    private static final String NULL_ID = "Id inválido!";
    private static final String NULL_NAME = "Nome inválido!";
    private static final String NULL_CLASS = "Turma inválida!";

    public StudentEntity save(StudentEntity entity) {
        try {
            Assert.check(entity != null, NULL_STUDENT);
            Assert.check(entity.getName() != null, NULL_NAME);
            Assert.check(entity.getClassEntity() != null, NULL_CLASS);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

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
            Assert.check(entity != null, NULL_STUDENT);
            Assert.check(entity.getId() != null, NULL_ID);
            Assert.check(entity.getName() != null, NULL_NAME);
            Assert.check(entity.getClassEntity() != null, NULL_CLASS);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

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
            Assert.check(entity != null, NULL_STUDENT);
            Assert.check(entity.getId() != null, NULL_ID);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

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

    public StudentEntity getById(Integer id) {
        StudentEntity entity = null;

        try {
            Assert.check(id != null, "ID inválido!");

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT * FROM students WHERE id = ?"; 
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                entity = new StudentEntity();
                entity.setId(rs.getInt("id"))
                      .setName(rs.getString("name"))
                      .setClassEntity(ClassDAO.getInstance().getById(rs.getInt("class_id")));
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
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT * FROM students";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                StudentEntity entity = new StudentEntity();
                entity.setId(rs.getInt("id"))
                      .setName(rs.getString("name"))
                      .setClassEntity(ClassDAO.getInstance().getById(rs.getInt("class_id")));
                entities.add(entity);
            }

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            Util.handleException(e);
        }

        return entities;
    }

    public List<StudentEntity> getByClassId(Integer classId) {
        List<StudentEntity> entities = new ArrayList<>();

        try {
            Assert.check(classId != null, NULL_ID);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT * FROM students WHERE class_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int i = 0;
            pstmt.setInt(++i, classId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                StudentEntity entity = new StudentEntity();
                entity.setId(rs.getInt("id"))
                      .setName(rs.getString("name"))
                      .setClassEntity(ClassDAO.getInstance().getById(rs.getInt("class_id")));
                entities.add(entity);
            }

            pstmt.close();
            conn.close();
        } catch (SQLException | IllegalObjectException e) {
            Util.handleException(e);
        }

        return entities;
    }

    private StudentDAO() {}

    private static final StudentDAO instance = new StudentDAO();

    public static synchronized StudentDAO getInstance() {
        return instance;
    }
}
