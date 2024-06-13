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

    public void setId(Integer id) {
        this.id = id;
    }

    public BookEntity getBookEntity() {
        return bookEntity;
    }

    public void setBookEntity(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
    }

    public StudentEntity getStudentEntity() {
        return studentEntity;
    }

    public void setStudentEntity(StudentEntity studentEntity) {
        this.studentEntity = studentEntity;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String toString() {
        return "ID: " + id + "\n" +
               "Livro: " + bookEntity.getTitle() + "\n" +
               "Estudante: " + studentEntity.getName() + "\n" +
               "Data de empréstimo: " + loanDate + "\n" +
               "Data de devolução: " + returnDate + "\n";
    }
}
