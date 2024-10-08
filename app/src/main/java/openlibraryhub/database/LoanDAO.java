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
import openlibraryhub.entities.LoanEntity;
import openlibraryhub.exceptions.EntityNotFoundException;
import openlibraryhub.interfaces.DAO;

public class LoanDAO implements DAO<LoanEntity>, ILoanDAO {
    public LoanEntity save(LoanEntity entity) {
        try {
            Assert.check(entity != null, Errors.NULL_LOAN);
            Assert.check(entity.getBookEntity() != null, Errors.NULL_BOOK);
            Assert.check(entity.getStudentEntity() != null, Errors.NULL_STUDENT);
            Assert.check(entity.getLoanDate() != null, Errors.NULL_LOAN_DATE);
            Assert.check(entity.getReturnDate() != null, Errors.NULL_RETURN_DATE);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

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
        } catch (Exception e) {
            Util.handleException(e);
        }

        return entity;
    }

    public LoanEntity update(LoanEntity entity) {
        try {
            Assert.check(entity != null, Errors.NULL_LOAN);
            Assert.check(entity.getId() != null, Errors.NULL_ID);
            Assert.check(entity.getBookEntity() != null, Errors.NULL_BOOK);
            Assert.check(entity.getStudentEntity() != null, Errors.NULL_STUDENT);
            Assert.check(entity.getLoanDate() != null, Errors.NULL_LOAN_DATE);
            Assert.check(entity.getReturnDate() != null, Errors.NULL_RETURN_DATE);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

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
        } catch (Exception e) {
            Util.handleException(e);
        }

        return entity;
    }

    public void delete(LoanEntity entity) {
        try {
            Assert.check(entity != null, Errors.NULL_LOAN);
            Assert.check(entity.getId() != null, Errors.NULL_ID);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "DELETE FROM loans WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, entity.getId());
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (Exception e) {
            Util.handleException(e);
        }
    }

    public LoanEntity getById(Integer id) {
        LoanEntity entity = null;

        try {
            Assert.check(id != null, Errors.NULL_ID);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT * FROM loans WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int i = 0;
            pstmt.setInt(++i, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                entity = new LoanEntity();
                entity.setId(rs.getInt("id"))
                      .setBookEntity(BookDAO.getInstance().getById(rs.getInt("book_id")))
                      .setStudentEntity(StudentDAO.getInstance().getById(rs.getInt("student_id")))
                      .setLoanDate(rs.getDate("loan_date"))
                      .setReturnDate(rs.getDate("return_date"));
            }

            pstmt.close();
            conn.close();
        } catch (Exception e) {
            Util.handleException(e);
        }

        if (entity == null) {
            throw new EntityNotFoundException(LoanEntity.class);
        }

        return entity;
    }

    public List<LoanEntity> getAll() {
        List<LoanEntity> entities = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT * FROM loans";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                LoanEntity entity = new LoanEntity();
                entity.setId(rs.getInt("id"))
                      .setBookEntity(BookDAO.getInstance().getById(rs.getInt("book_id")))
                      .setStudentEntity(StudentDAO.getInstance().getById(rs.getInt("student_id")))
                      .setLoanDate(rs.getDate("loan_date"))
                      .setReturnDate(rs.getDate("return_date"));
                entities.add(entity);
            }

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            Util.handleException(e);
        }

        return entities;
    }

    public LoanEntity getByStudentId(Integer studentId) {
        LoanEntity entity = null;

        try {
            Assert.check(studentId != null, Errors.NULL_ID);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT * FROM loans WHERE student_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int i = 0;
            pstmt.setInt(++i, studentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                entity = new LoanEntity();
                entity.setId(rs.getInt("id"))
                      .setBookEntity(BookDAO.getInstance().getById(rs.getInt("book_id")))
                      .setStudentEntity(StudentDAO.getInstance().getById(rs.getInt("student_id")))
                      .setLoanDate(rs.getDate("loan_date"))
                      .setReturnDate(rs.getDate("return_date"));
            }

            pstmt.close();
            conn.close();
        } catch (Exception e) {
            Util.handleException(e);
        }

        return entity;
    }

    public LoanEntity getByBookId(Integer bookId) {
        LoanEntity entity = null;

        try {
            Assert.check(bookId != null, Errors.NULL_BOOK);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT * FROM loans WHERE book_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int i = 0;
            pstmt.setInt(++i, bookId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                entity = new LoanEntity();
                entity.setId(rs.getInt("id"))
                      .setBookEntity(BookDAO.getInstance().getById(rs.getInt("book_id")))
                      .setStudentEntity(StudentDAO.getInstance().getById(rs.getInt("student_id")))
                      .setLoanDate(rs.getDate("loan_date"))
                      .setReturnDate(rs.getDate("return_date"));
            }

            pstmt.close();
            conn.close();
        } catch (Exception e) {
            Util.handleException(e);
        }

        return entity;
    }

    private LoanDAO() {}

    private static final LoanDAO instance = new LoanDAO();

    public static LoanDAO getInstance() {
        return instance;
    }
}
