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
import openlibraryhub.entities.ClassEntity;

public class ClassRepository {
    public static ClassEntity getById(int id) {
        ClassEntity classEntity = null;

        try {
            Assert.notNull(id, "ID inválido!");

            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "SELECT * FROM classes WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int i = 0;
            pstmt.setInt(++i, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                classEntity = new ClassEntity();
                classEntity.setId(rs.getInt("id"));
                classEntity.setName(rs.getString("name"));
            }

            pstmt.close();
            conn.close();
        } catch (SQLException | IllegalArgumentException e) {
            clean();
            e.printStackTrace();
            println("");
        }

        return classEntity;
    }

    public static ClassEntity save(ClassEntity classEntity) {
        try {
            Assert.notNull(classEntity, "A turma não pode ser nula!");
            Assert.notEmpty(classEntity.getName(), "Nome inválido!");

            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "INSERT INTO classes (name) VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int i = 0;
            pstmt.setString(++i, classEntity.getName());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                classEntity.setId(rs.getInt(1));
            }

            pstmt.close();
            conn.close();
        } catch (SQLException | IllegalArgumentException e) {
            clean();
            e.printStackTrace();
            println("");
        }

        return classEntity;
    }

    public static void update(ClassEntity t) {
        // TODO
    }

    public static void delete(ClassEntity t) {
        // TODO
    }
}
