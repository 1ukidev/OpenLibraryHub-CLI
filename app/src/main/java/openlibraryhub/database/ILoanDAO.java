package openlibraryhub.database;

import openlibraryhub.entities.LoanEntity;

public interface ILoanDAO {
    /**
     * Retrieves a loan by the student's id.
     * 
     * @param studentId
     */
    LoanEntity getByStudentId(Integer studentId);

    /**
     * Retrieves a loan by the book's id.
     * 
     * @param bookId
     */
    LoanEntity getByBookId(Integer bookId);
}
