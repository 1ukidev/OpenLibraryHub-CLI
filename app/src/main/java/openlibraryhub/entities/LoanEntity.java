package openlibraryhub.entities;

import java.util.Date;

import openlibraryhub.annotations.EntityName;

@EntityName("Empréstimo")
public class LoanEntity extends Entity {
    private Integer id;
    private BookEntity bookEntity;
    private StudentEntity studentEntity;
    private Date loanDate;
    private Date returnDate;

    public LoanEntity() {}

    public LoanEntity(BookEntity bookEntity, StudentEntity studentEntity,
                      Date loanDate, Date returnDate) {
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
        String id = getId() != null ? getId().toString() : "N/A";
        String bookTitle = getBookEntity() != null ? getBookEntity().getTitle() : "N/A";
        String studentName = getStudentEntity() != null ? getStudentEntity().getName() : "N/A";
        String loanDate = getLoanDate() != null ? getLoanDate().toString() : "N/A";
        String returnDate = getReturnDate() != null ? getReturnDate().toString() : "N/A";
        return String.format(
            "Id: %s\nLivro: %s\nEstudante: %s\nData de empréstimo: %s\nData de devolução: %s\n",
            id, bookTitle, studentName, loanDate, returnDate);
    }
}
