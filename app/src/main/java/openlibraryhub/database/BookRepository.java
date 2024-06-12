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
import openlibraryhub.interfaces.CRUDRepository;

public class BookRepository implements CRUDRepository<BookEntity> {
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
                entity.setId(rs.getInt("id"));
                entity.setTitle(rs.getString("title"));
                entity.setAuthor(rs.getString("author"));
                entity.setSection(rs.getString("section"));
                entity.setPages(rs.getInt("pages"));
                entity.setYear(rs.getInt("year"));
                entity.setStock(rs.getInt("stock"));
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
        } catch (SQLException | IllegalArgumentException e) {
            clean();
            e.printStackTrace();
            println("");
        }

        return entity;
    }

    public BookEntity update(BookEntity book) {
        // TODO
        return null;
    }

    public void delete(BookEntity book) {
        // TODO
    }

    private BookRepository() {}

    private static final BookRepository instance = new BookRepository();

    public static synchronized BookRepository getInstance() {
        return instance;
    }
}
