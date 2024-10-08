package openlibraryhub.database;

import static openlibraryhub.Constants.DB_PASSWORD;
import static openlibraryhub.Constants.DB_URL;
import static openlibraryhub.Constants.DB_USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import openlibraryhub.Assert;
import openlibraryhub.Errors;
import openlibraryhub.Util;
import openlibraryhub.entities.ClassEntity;
import openlibraryhub.exceptions.EntityNotFoundException;
import openlibraryhub.interfaces.DAO;

public class ClassDAO implements DAO<ClassEntity> {
    public ClassEntity save(ClassEntity entity) {
        try {
            Assert.check(entity != null, Errors.NULL_CLASS);
            Assert.check(entity.getName() != null, Errors.NULL_NAME);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "INSERT INTO classes (name) VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int i = 0;
            pstmt.setString(++i, entity.getName());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(1));
            }

            pstmt.close();
            conn.close();
        } catch (Exception e) {
            Util.handleException(e);
        }

        return entity;
    }

    public ClassEntity update(ClassEntity entity) {
        try {
            Assert.check(entity != null, Errors.NULL_CLASS);
            Assert.check(entity.getId() != null, Errors.NULL_ID);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "UPDATE classes SET name = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int i = 0;
            pstmt.setString(++i, entity.getName());
            pstmt.setInt(++i, entity.getId());
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (Exception e) {
            Util.handleException(e);
        }

        return entity;
    }

    public void delete(ClassEntity entity) {
        try {
            Assert.check(entity != null, Errors.NULL_CLASS);
            Assert.check(entity.getId() != null, Errors.NULL_ID);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "DELETE FROM classes WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int i = 0;
            pstmt.setInt(++i, entity.getId());
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (Exception e) {
            Util.handleException(e);
        }
    }

    public ClassEntity getById(Integer id) {
        ClassEntity entity = null;

        try {
            Assert.check(id != null, Errors.NULL_ID);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT * FROM classes WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int i = 0;
            pstmt.setInt(++i, id.intValue());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                entity = new ClassEntity();
                entity.setId(rs.getInt("id"))
                      .setName(rs.getString("name"));
            }

            pstmt.close();
            conn.close();
        } catch (Exception e) {
            Util.handleException(e);
        }

        if (entity == null) {
            throw new EntityNotFoundException(ClassEntity.class);
        }

        return entity;
    }

    public List<ClassEntity> getAll() {
        List<ClassEntity> entities = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT * FROM classes";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ClassEntity entity = new ClassEntity();
                entity.setId(rs.getInt("id"))
                      .setName(rs.getString("name"));
                entities.add(entity);
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            Util.handleException(e);
        }

        return entities;
    }

    private ClassDAO() {}

    private static final ClassDAO instance = new ClassDAO();

    public static ClassDAO getInstance() {
        return instance;
    }
}
