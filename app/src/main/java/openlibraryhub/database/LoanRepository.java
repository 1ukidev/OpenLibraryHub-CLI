package openlibraryhub.database;

import java.util.List;

import openlibraryhub.entities.LoanEntity;
import openlibraryhub.interfaces.CRUDRepository;

public class LoanRepository implements CRUDRepository<LoanEntity> {
    public LoanEntity save(LoanEntity entity) {
        // TODO
        return null;
    }

    public LoanEntity update(LoanEntity entity) {
        // TODO
        return null;
    }

    public void delete(LoanEntity entity) {
        // TODO
    }

    public LoanEntity getById(int id) {
        // TODO
        return null;
    }

    public List<LoanEntity> getAll() {
        // TODO
        return null;
    }

    private LoanRepository() {}

    private static final LoanRepository instance = new LoanRepository();

    public static synchronized LoanRepository getInstance() {
        return instance;
    }
}
