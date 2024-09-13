package openlibraryhub.database;

import openlibraryhub.entities.LoanEntity;

public interface ILoanDAO {
    /**
     * Retrieves a loan by the student's id.
     * 
     * @param studentId
     * @return the loan with the given student id, or null if it does not exist.
     */
    LoanEntity getByStudentId(Integer studentId);

    /**
     * Retrieves a loan by the book's id.
     * 
     * @param bookId
     * @return the loan with the given book id, or null if it does not exist.
     */
    LoanEntity getByBookId(Integer bookId);
}
