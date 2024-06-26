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
import openlibraryhub.entities.BookEntity;
import openlibraryhub.exceptions.IllegalObjectException;
import openlibraryhub.interfaces.DAO;

public class BookDAO implements DAO<BookEntity> {
    public BookEntity save(BookEntity entity) {
        try {
            Assert.notNull(entity, "O livro não pode ser nulo!");
            Assert.notEmpty(entity.getTitle(), "Título inválido!");
            Assert.notEmpty(entity.getAuthor(), "Autor inválido!");
            Assert.notEmpty(entity.getSection(), "Seção inválida!");
            Assert.notNull(entity.getPages(), "Quantidade de páginas inválidas!");
            Assert.notNull(entity.getYear(), "Ano inválido!");
            Assert.notNull(entity.getStock(), "Quantidade em estoque inválido!");

            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "INSERT INTO books (title, author, section, pages, year, stock) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int i = 0;
            pstmt.setString(++i, entity.getTitle());
            pstmt.setString(++i, entity.getAuthor());
            pstmt.setString(++i, entity.getSection());
            pstmt.setInt(++i, entity.getPages());
            pstmt.setInt(++i, entity.getYear());
            pstmt.setInt(++i, entity.getStock());
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

    public BookEntity update(BookEntity entity) {
        try {
            Assert.notNull(entity, "O livro não pode ser nulo!");
            Assert.notNull(entity.getId(), "ID inválido!");
            Assert.notEmpty(entity.getTitle(), "Título inválido!");
            Assert.notEmpty(entity.getAuthor(), "Autor inválido!");
            Assert.notEmpty(entity.getSection(), "Seção inválida!");
            Assert.notNull(entity.getPages(), "Quantidade de páginas inválidas!");
            Assert.notNull(entity.getYear(), "Ano inválido!");
            Assert.notNull(entity.getStock(), "Quantidade em estoque inválido!");

            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "UPDATE books SET title = ?, author = ?, section = ?, pages = ?, year = ?, stock = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int i = 0;
            pstmt.setString(++i, entity.getTitle());
            pstmt.setString(++i, entity.getAuthor());
            pstmt.setString(++i, entity.getSection());
            pstmt.setInt(++i, entity.getPages());
            pstmt.setInt(++i, entity.getYear());
            pstmt.setInt(++i, entity.getStock());
            pstmt.setInt(++i, entity.getId());
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (SQLException | IllegalObjectException e) {
            Util.handleException(e);
        }

        return entity;
    }

    public void delete(BookEntity entity) {
        try {
            Assert.notNull(entity, "O livro não pode ser nulo!");
            Assert.notNull(entity.getId(), "ID inválido!");

            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "DELETE FROM books WHERE id = ?";
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

    public BookEntity getById(int id) {
        BookEntity entity = null;    

        try {
            Assert.notNull(id, "ID inválido!");

            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "SELECT * FROM books WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int i = 0;
            pstmt.setInt(++i, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                entity = new BookEntity();
                entity.setId(rs.getInt("id"))
                      .setTitle(rs.getString("title"))
                      .setAuthor(rs.getString("author"))
                      .setSection(rs.getString("section"))
                      .setPages(rs.getInt("pages"))
                      .setYear(rs.getInt("year"))
                      .setStock(rs.getInt("stock"));
            }

            pstmt.close();
            conn.close();
        } catch (SQLException | IllegalObjectException e) {
            Util.handleException(e);
        }

        return entity;
    }

    public List<BookEntity> getAll() {
        List<BookEntity> entities = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "SELECT * FROM books";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                BookEntity entity = new BookEntity();
                entity.setId(rs.getInt("id"))
                      .setTitle(rs.getString("title"))
                      .setAuthor(rs.getString("author"))
                      .setSection(rs.getString("section"))
                      .setPages(rs.getInt("pages"))
                      .setYear(rs.getInt("year"))
                      .setStock(rs.getInt("stock"));
                entities.add(entity);
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            Util.handleException(e);
        }
        
        return entities;
    }

    private BookDAO() {}

    private static final BookDAO instance = new BookDAO();

    public static synchronized BookDAO getInstance() {
        return instance;
    }
}
