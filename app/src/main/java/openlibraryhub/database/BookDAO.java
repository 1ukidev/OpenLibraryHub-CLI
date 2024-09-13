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
import openlibraryhub.entities.BookEntity;
import openlibraryhub.exceptions.EntityNotFoundException;
import openlibraryhub.interfaces.DAO;

public class BookDAO implements DAO<BookEntity>, IBookDAO {
    public BookEntity save(BookEntity entity) {
        try {
            Assert.check(entity != null, Errors.NULL_BOOK);
            Assert.check(entity.getTitle() != null, Errors.NULL_TITLE);
            Assert.check(entity.getAuthor() != null, Errors.NULL_AUTHOR);
            Assert.check(entity.getSection() != null, Errors.NULL_SECTION);
            Assert.check(entity.getPages() != null, Errors.NULL_PAGES);
            Assert.check(entity.getYear() != null, Errors.NULL_YEAR);
            Assert.check(entity.getStock() != null, Errors.NULL_STOCK);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

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
        } catch (Exception e) {
            Util.handleException(e);
        }

        return entity;
    }

    public BookEntity update(BookEntity entity) {
        try {
            Assert.check(entity != null, Errors.NULL_BOOK);
            Assert.check(entity.getId() != null, Errors.NULL_ID);
            Assert.check(entity.getTitle() != null, Errors.NULL_TITLE);
            Assert.check(entity.getAuthor() != null, Errors.NULL_AUTHOR);
            Assert.check(entity.getSection() != null, Errors.NULL_SECTION);
            Assert.check(entity.getPages() != null, Errors.NULL_PAGES);
            Assert.check(entity.getYear() != null, Errors.NULL_YEAR);
            Assert.check(entity.getStock() != null, Errors.NULL_STOCK);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

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
        } catch (Exception e) {
            Util.handleException(e);
        }

        return entity;
    }

    public void delete(BookEntity entity) {
        try {
            Assert.check(entity != null, Errors.NULL_BOOK);
            Assert.check(entity.getId() != null, Errors.NULL_ID);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "DELETE FROM books WHERE id = ?";
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

    public BookEntity getById(Integer id) {
        BookEntity entity = null;    

        try {
            Assert.check(id != null, Errors.NULL_ID);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT * FROM books WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int i = 0;
            pstmt.setInt(++i, id.intValue());
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
        } catch (Exception e) {
            Util.handleException(e);
        }

        if (entity == null) {
            throw new EntityNotFoundException(BookEntity.class);
        }

        return entity;
    }

    public List<BookEntity> getAll() {
        List<BookEntity> entities = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

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

    public static BookDAO getInstance() {
        return instance;
    }
}
