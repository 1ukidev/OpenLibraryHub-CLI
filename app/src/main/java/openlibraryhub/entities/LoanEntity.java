package openlibraryhub.entities;

import java.util.Date;

public class LoanEntity extends Entity {
    private Integer id;
    private BookEntity bookEntity;
    private StudentEntity studentEntity;
    private Date loanDate;
    private Date returnDate;

    public LoanEntity() {}

    public LoanEntity(BookEntity bookEntity, StudentEntity studentEntity, Date loanDate, Date returnDate) {
        this.bookEntity = bookEntity;
        this.studentEntity = studentEntity;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public Integer getId() {
        return id;
    }

    public LoanEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public BookEntity getBookEntity() {
        return bookEntity;
    }

    public LoanEntity setBookEntity(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
        return this;
    }

    public StudentEntity getStudentEntity() {
        return studentEntity;
    }

    public LoanEntity setStudentEntity(StudentEntity studentEntity) {
        this.studentEntity = studentEntity;
        return this;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public LoanEntity setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
        return this;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public LoanEntity setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public String toString() {
        return "ID: " + id + "\n" +
               "Livro: " + bookEntity.getTitle() + "\n" +
               "Estudante: " + studentEntity.getName() + "\n" +
               "Data de empréstimo: " + loanDate + "\n" +
               "Data de devolução: " + returnDate + "\n";
    }
}
