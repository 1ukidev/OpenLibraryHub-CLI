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
import openlibraryhub.entities.LoanEntity;
import openlibraryhub.exceptions.IllegalObjectException;
import openlibraryhub.interfaces.CRUDRepository;

public class LoanRepository implements CRUDRepository<LoanEntity> {
    public LoanEntity save(LoanEntity entity) {
        try {
            Assert.notNull(entity, "O empréstimo não pode ser nulo!");
            Assert.notNull(entity.getBookEntity(), "Livro inválido!");
            Assert.notNull(entity.getStudentEntity(), "Estudante inválido!");
            Assert.notNull(entity.getLoanDate(), "Data de empréstimo inválida!");
            Assert.notNull(entity.getReturnDate(), "Data de devolução inválida!");

            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "INSERT INTO loans (book_id, student_id, loan_date, return_date) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int i = 0;
            pstmt.setInt(++i, entity.getBookEntity().getId());
            pstmt.setInt(++i, entity.getStudentEntity().getId());
            pstmt.setDate(++i, new java.sql.Date(entity.getLoanDate().getTime()));
            pstmt.setDate(++i, new java.sql.Date(entity.getReturnDate().getTime()));
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

    public LoanEntity update(LoanEntity entity) {
        try {
            Assert.notNull(entity, "O empréstimo não pode ser nulo!");
            Assert.notNull(entity.getId(), "ID inválido!");
            Assert.notNull(entity.getBookEntity(), "Livro inválido!");
            Assert.notNull(entity.getStudentEntity(), "Estudante inválido!");
            Assert.notNull(entity.getLoanDate(), "Data de empréstimo inválida!");
            Assert.notNull(entity.getReturnDate(), "Data de devolução inválida!");

            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "UPDATE loans SET book_id = ?, student_id = ?, loan_date = ?, return_date = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int i = 0;
            pstmt.setInt(++i, entity.getBookEntity().getId());
            pstmt.setInt(++i, entity.getStudentEntity().getId());
            pstmt.setDate(++i, new java.sql.Date(entity.getLoanDate().getTime()));
            pstmt.setDate(++i, new java.sql.Date(entity.getReturnDate().getTime()));
            pstmt.setInt(++i, entity.getId());
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (SQLException | IllegalObjectException e) {
            Util.handleException(e);
        }
        return entity;
    }

    public void delete(LoanEntity entity) {
        try {
            Assert.notNull(entity, "O empréstimo não pode ser nulo!");
            Assert.notNull(entity.getId(), "ID inválido!");

            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "DELETE FROM loans WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, entity.getId());
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (SQLException | IllegalObjectException e) {
            Util.handleException(e);
        }
    }

    public LoanEntity getById(int id) {
        LoanEntity entity = null;

        try {
            Assert.notNull(id, "ID inválido!");

            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "SELECT * FROM loans WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int i = 0;
            pstmt.setInt(++i, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                entity = new LoanEntity();
                entity.setId(rs.getInt("id"));
                entity.setBookEntity(BookRepository.getInstance().getById(rs.getInt("book_id")));
                entity.setStudentEntity(StudentRepository.getInstance().getById(rs.getInt("student_id")));
                entity.setLoanDate(rs.getDate("loan_date"));
                entity.setReturnDate(rs.getDate("return_date"));
            }

            pstmt.close();
            conn.close();
        } catch (SQLException | IllegalObjectException e) {
            Util.handleException(e);
        }

        return entity;
    }

    public List<LoanEntity> getAll() {
        List<LoanEntity> entities = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(Constants.DB_URL + "/" + Constants.DB_SCHEMA,
                                                          Constants.DB_USER, Constants.DB_PASSWORD);

            String sql = "SELECT * FROM loans";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                LoanEntity entity = new LoanEntity();
                entity.setId(rs.getInt("id"));
                entity.setBookEntity(BookRepository.getInstance().getById(rs.getInt("book_id")));
                entity.setStudentEntity(StudentRepository.getInstance().getById(rs.getInt("student_id")));
                entity.setLoanDate(rs.getDate("loan_date"));
                entity.setReturnDate(rs.getDate("return_date"));
                entities.add(entity);
            }

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            Util.handleException(e);
        }

        return entities;
    }

    private LoanRepository() {}

    private static final LoanRepository instance = new LoanRepository();

    public static synchronized LoanRepository getInstance() {
        return instance;
    }
}
