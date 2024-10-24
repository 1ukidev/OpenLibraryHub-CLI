package openlibraryhub.screens;

import java.util.InputMismatchException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import openlibraryhub.Console;
import openlibraryhub.Errors;
import openlibraryhub.Util;
import openlibraryhub.database.BookDAO;
import openlibraryhub.database.LoanDAO;
import openlibraryhub.database.StudentDAO;
import openlibraryhub.entities.BookEntity;
import openlibraryhub.entities.LoanEntity;
import openlibraryhub.entities.StudentEntity;
import openlibraryhub.interfaces.CRUDScreen;

public class Loans implements CRUDScreen {
    public void welcome() {
        boolean running = true;
        while (running) {
            Console.println("1 - Cadastrar empréstimo");
            Console.println("2 - Atualizar empréstimo");
            Console.println("3 - Excluir empréstimo");
            Console.println("4 - Buscar empréstimo");
            Console.println("5 - Listar empréstimos");
            Console.println("6 - Voltar");
            Console.print("--> ");

            running = handleOption();
        }
    }

    private final Map<Integer, Runnable> options = Map.of(
        1, this::save,
        2, this::update,
        3, this::delete,
        4, this::search,
        5, this::list,
        6, () -> Console.println("Voltando ao menu principal...\n")
    );

    public boolean handleOption() {
        try {
            int opcao = Console.readInt();
            Console.clear();
            Runnable action = options.get(opcao);

            if (action != null) {
                action.run();
                if (opcao == 6) {
                    return false;
                }
            } else {
                Console.println(Errors.INVALID_OPTION_MESSAGE);
            }
        } catch (InputMismatchException e) {
            Util.handleException(e);
        }
        return true;
    }

    public void save() {
        try {
            Console.print("Digite o id do livro: ");
            int bookId = Console.readInt();
            BookEntity bookEntity = BookDAO.getInstance().getById(bookId);

            Console.print("Digite o id do estudante: ");
            int studentId = Console.readInt();
            StudentEntity studentEntity = StudentDAO.getInstance().getById(studentId);

            Console.print("Digite a data de empréstimo (dd/mm/aaaa): ");
            Date loanDate = Console.readDate();

            Console.print("Digite a data de devolução (dd/mm/aaaa): ");
            Date returnDate = Console.readDate();

            LoanEntity temp = new LoanEntity(bookEntity, studentEntity, loanDate, returnDate);
            LoanEntity loanEntity = LoanDAO.getInstance().save(temp);

            if (loanEntity != null && loanEntity.getId() != null) {
                Console.clear();
                Console.println("Empréstimo cadastrado com sucesso!\n");
            }
        } catch (Exception e) {
            Util.handleException(e);
        }
    }

    public void update() {
        try {
            Console.print("Digite o id do empréstimo: ");
            int id = Console.readInt();
            LoanEntity loanEntity = LoanDAO.getInstance().getById(id);

            Console.print("Digite o id do livro: ");
            int bookId = Console.readInt();
            BookEntity bookEntity = BookDAO.getInstance().getById(bookId);
            loanEntity.setBookEntity(bookEntity);

            Console.print("Digite o id do estudante: ");
            int studentId = Console.readInt();
            StudentEntity studentEntity = StudentDAO.getInstance().getById(studentId);
            loanEntity.setStudentEntity(studentEntity);

            Console.print("Digite a data de empréstimo (dd/mm/aaaa): ");
            Date loanDate = Console.readDate();
            loanEntity.setLoanDate(loanDate);

            Console.print("Digite a data de devolução (dd/mm/aaaa): ");
            Date returnDate = Console.readDate();
            loanEntity.setReturnDate(returnDate);

            LoanEntity updatedLoanEntity = LoanDAO.getInstance().update(loanEntity);

            if (updatedLoanEntity != null && updatedLoanEntity.getId() != null) {
                Console.clear();
                Console.println("Empréstimo atualizado com sucesso!\n");
            }
        } catch (Exception e) {
            Util.handleException(e);
        }
    }

    public void delete() {
        try {
            Console.print("Digite o id do empréstimo: ");
            int id = Console.readInt();
            LoanEntity loanEntity = LoanDAO.getInstance().getById(id);

            if (loanEntity != null) {
                LoanDAO.getInstance().delete(loanEntity);
                Console.println("");
            }
        } catch (Exception e) {
            Util.handleException(e);
        }
    }

    public void search() {
        try {
            Console.print("Digite o id do empréstimo: ");
            int id = Console.readInt();
            LoanEntity loanEntity = LoanDAO.getInstance().getById(id);

            if (loanEntity != null) {
                Console.clear();
                Console.println("Empréstimo encontrado!\n");
                Console.println(loanEntity);
            }
        } catch (Exception e) {
            Util.handleException(e);
        }
    }

    public void list() {
        List<LoanEntity> loans = LoanDAO.getInstance().getAll();
        if (!loans.isEmpty()) {
            loans.forEach(loanEntity -> Console.println(loanEntity));
        } else {
            Console.println("Nenhum empréstimo encontrado!\n");
        }
    }

    private Loans() {}

    private static final Loans instance = new Loans();

    public static Loans getInstance() {
        return instance;
    }
}
