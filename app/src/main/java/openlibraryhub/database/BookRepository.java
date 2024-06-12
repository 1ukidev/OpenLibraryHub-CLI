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
import openlibraryhub.entities.BookEntity;

public class BookRepository {
    public static BookEntity getById(int id) {
        BookEntity bookEntity = null;    

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
                bookEntity = new BookEntity();
                bookEntity.setId(rs.getInt("id"));
                bookEntity.setTitle(rs.getString("title"));
                bookEntity.setAuthor(rs.getString("author"));
                bookEntity.setSection(rs.getString("section"));
                bookEntity.setPages(rs.getInt("pages"));
                bookEntity.setYear(rs.getInt("year"));
                bookEntity.setStock(rs.getInt("stock"));
            }

            pstmt.close();
            conn.close();
        } catch (SQLException | IllegalArgumentException e) {
            clean();
            e.printStackTrace();
            println("");
        }

        return bookEntity;
    }

    public static BookEntity save(BookEntity bookEntity) {
        try {
            Assert.notNull(bookEntity, "O livro não pode ser nulo!");
            Assert.notEmpty(bookEntity.getTitle(), "Título inválido!");
            Assert.notEmpty(bookEntity.getAuthor(), "Autor inválido!");
            Assert.notEmpty(bookEntity.getSection(), "Seção inválida!");
            Assert.notNull(bookEntity.getPages(), "Quantidade de páginas inválidas!");
            Assert.notNull(bookEntity.getYear(), "Ano inválido!");
            Assert.notNull(bookEntity.getStock(), "Quantidade em estoque inválido!");

            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "INSERT INTO books (title, author, section, pages, year, stock) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int i = 0;
            pstmt.setString(++i, bookEntity.getTitle());
            pstmt.setString(++i, bookEntity.getAuthor());
            pstmt.setString(++i, bookEntity.getSection());
            pstmt.setInt(++i, bookEntity.getPages());
            pstmt.setInt(++i, bookEntity.getYear());
            pstmt.setInt(++i, bookEntity.getStock());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                bookEntity.setId(rs.getInt(1));
            }

            pstmt.close();
            conn.close();
        } catch (SQLException | IllegalArgumentException e) {
            clean();
            e.printStackTrace();
            println("");
        }

        return bookEntity;
    }

    public static void update(BookEntity book) {
        // TODO
    }

    public static void delete(BookEntity book) {
        // TODO
    }
}
