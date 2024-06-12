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
import openlibraryhub.interfaces.CRUDRepository;

public class ClassRepository implements CRUDRepository<ClassEntity> {
    public ClassEntity getById(int id) {
        ClassEntity entity = null;

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
                entity = new ClassEntity();
                entity.setId(rs.getInt("id"));
                entity.setName(rs.getString("name"));
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

    public ClassEntity save(ClassEntity entity) {
        try {
            Assert.notNull(entity, "A turma não pode ser nula!");
            Assert.notEmpty(entity.getName(), "Nome inválido!");

            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

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
        } catch (SQLException | IllegalArgumentException e) {
            clean();
            e.printStackTrace();
            println("");
        }

        return entity;
    }

    public ClassEntity update(ClassEntity t) {
        // TODO
        return null;
    }

    public void delete(ClassEntity t) {
        // TODO
    }

    private ClassRepository() {}

    private static final ClassRepository instance = new ClassRepository();

    public static synchronized ClassRepository getInstance() {
        return instance;
    }
}
