package openlibraryhub.screens;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import static openlibraryhub.Console.clean;
import static openlibraryhub.Console.print;
import static openlibraryhub.Console.println;
import static openlibraryhub.Console.scanner;

import openlibraryhub.interfaces.CRUDScreen;
import openlibraryhub.Errors;
import openlibraryhub.Util;
import openlibraryhub.database.BookRepository;
import openlibraryhub.database.LoanRepository;
import openlibraryhub.database.StudentRepository;
import openlibraryhub.entities.BookEntity;
import openlibraryhub.entities.LoanEntity;
import openlibraryhub.entities.StudentEntity;
import openlibraryhub.exceptions.EmptyStringException;
import openlibraryhub.exceptions.EntityNotFoundException;
import openlibraryhub.exceptions.IllegalDateException;

public class Loans implements CRUDScreen {
    public void welcome() {
        boolean running = true;
        while (running) {
            println("1 - Cadastrar empréstimo");
            println("2 - Atualizar empréstimo");
            println("3 - Excluir empréstimo");
            println("4 - Buscar empréstimo");
            println("5 - Listar empréstimos");
            println("6 - Voltar");
            print("--> ");

            running = handleOption();
        }
    }

    private final Map<Integer, Runnable> options = Map.of(
        1, this::save,
        2, this::update,
        3, this::delete,
        4, this::search,
        5, this::list,
        6, () -> println("Voltando ao menu principal...\n")
    );

    public boolean handleOption() {
        try {
            int opcao = scanner.nextInt();
            clean();
            Runnable action = options.get(opcao);

            if (action != null) {
                action.run();
                if (opcao == 6) {
                    return false;
                }
            } else {
                println(Errors.INVALID_OPTION_MESSAGE);
            }
        } catch (InputMismatchException e) {
            Util.handleException(e);
        }
        return true;
    }

    public void save() {
        try {
            scanner.nextLine();
            print("Digite o id do livro: ");
            int bookId = scanner.nextInt();
            BookEntity bookEntity = BookRepository.getInstance().getById(bookId);
            if (bookEntity == null) {
                throw new EntityNotFoundException(BookEntity.class);
            }

            print("Digite o id do estudante: ");
            int studentId = scanner.nextInt();
            StudentEntity studentEntity = StudentRepository.getInstance().getById(studentId);
            if (studentEntity == null) {
                throw new EntityNotFoundException(StudentEntity.class);
            }

            scanner.nextLine();
            print("Digite a data de empréstimo (dd/mm/aaaa): ");
            String loanDate = scanner.nextLine();
            if (loanDate == null || loanDate.isEmpty()) {
                throw new EmptyStringException();
            }
            if (!Util.isDate(loanDate)) {
                throw new IllegalDateException("Data inválida!");
            }
            Date convertedLoanDate = Util.convertStringToDate(loanDate);

            print("Digite a data de devolução (dd/mm/aaaa): ");
            String returnDate = scanner.nextLine();
            if (returnDate == null || returnDate.isEmpty()) {
                throw new EmptyStringException();
            }
            if (!Util.isDate(returnDate)) {
                throw new IllegalDateException("Data inválida!");
            }
            Date convertedReturnDate = Util.convertStringToDate(returnDate);

            LoanEntity loanEntity = LoanRepository.getInstance().save(new LoanEntity(bookEntity, studentEntity,
                                                                                     convertedLoanDate, convertedReturnDate));

            if (loanEntity != null && loanEntity.getId() != null) {
                clean();
                println("Empréstimo cadastrado com sucesso!\n");
            }
        } catch (InputMismatchException
                | EntityNotFoundException
                | IllegalDateException
                | EmptyStringException e) {
            Util.handleException(e);
        }
    }

    public void update() {
        try {
            scanner.nextLine();
            print("Digite o id do empréstimo: ");
            int id = scanner.nextInt();
            LoanEntity loanEntity = LoanRepository.getInstance().getById(id);

            if (loanEntity == null) {
                throw new EntityNotFoundException(LoanEntity.class);
            }

            print("Digite o id do livro: ");
            int bookId = scanner.nextInt();
            BookEntity bookEntity = BookRepository.getInstance().getById(bookId);
            if (bookEntity == null) {
                throw new EntityNotFoundException(BookEntity.class);
            }

            print("Digite o id do estudante: ");
            int studentId = scanner.nextInt();
            StudentEntity studentEntity = StudentRepository.getInstance().getById(studentId);
            if (studentEntity == null) {
                throw new EntityNotFoundException(StudentEntity.class);
            }

            scanner.nextLine();
            print("Digite a data de empréstimo (dd/mm/aaaa): ");
            String loanDate = scanner.nextLine();
            if (loanDate == null || loanDate.isEmpty()) {
                throw new EmptyStringException();
            }
            if (!Util.isDate(loanDate)) {
                throw new IllegalDateException("Data inválida!");
            }
            Date convertedLoanDate = Util.convertStringToDate(loanDate);

            print("Digite a data de devolução (dd/mm/aaaa): ");
            String returnDate = scanner.nextLine();
            if (returnDate == null || returnDate.isEmpty()) {
                throw new EmptyStringException();
            }
            if (!Util.isDate(returnDate)) {
                throw new IllegalDateException("Data inválida!");
            }
            Date convertedReturnDate = Util.convertStringToDate(returnDate);

            loanEntity.setBookEntity(bookEntity);
            loanEntity.setStudentEntity(studentEntity);
            loanEntity.setLoanDate(convertedLoanDate);
            loanEntity.setReturnDate(convertedReturnDate);

            LoanEntity updatedLoanEntity = LoanRepository.getInstance().update(loanEntity);

            if (updatedLoanEntity != null && updatedLoanEntity.getId() != null) {
                clean();
                println("Empréstimo atualizado com sucesso!\n");
            }
        } catch (InputMismatchException
                | EntityNotFoundException
                | IllegalDateException
                | EmptyStringException e) {
            Util.handleException(e);
        }
    }

    public void delete() {
        try {
            print("Digite o id do empréstimo: ");
            int id = scanner.nextInt();
            LoanEntity loanEntity = LoanRepository.getInstance().getById(id);

            if (loanEntity != null) {
                LoanRepository.getInstance().delete(loanEntity);
                println("");
            } else {
                throw new EntityNotFoundException(LoanEntity.class);
            }
        } catch (InputMismatchException | EntityNotFoundException e) {
            Util.handleException(e);
        }
    }

    public void search() {
        try {
            print("Digite o id do empréstimo: ");
            int id = scanner.nextInt();
            LoanEntity loanEntity = LoanRepository.getInstance().getById(id);

            if (loanEntity != null) {
                clean();
                println("Empréstimo encontrado!\n");
                println(loanEntity.toString());
            } else {
                throw new EntityNotFoundException(LoanEntity.class);
            }
        } catch (InputMismatchException | EntityNotFoundException e) {
            Util.handleException(e);
        }
    }

    public void list() {
        List<LoanEntity> loans = LoanRepository.getInstance().getAll();
        if (!loans.isEmpty()) {
            loans.forEach(loanEntity -> println(loanEntity.toString()));
        } else {
            println("Nenhuma LoanEntity encontrado!\n");
        }
    }

    private Loans() {}

    private static final Loans instance = new Loans();

    public static synchronized Loans getInstance() {
        return instance;
    }
}
