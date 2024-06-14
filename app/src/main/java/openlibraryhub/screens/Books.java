package openlibraryhub.screens;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import static openlibraryhub.Console.clean;
import static openlibraryhub.Console.print;
import static openlibraryhub.Console.println;
import static openlibraryhub.Console.scanner;

import openlibraryhub.entities.BookEntity;
import openlibraryhub.entities.LoanEntity;
import openlibraryhub.exceptions.EmptyStringException;
import openlibraryhub.exceptions.EntityNotFoundException;
import openlibraryhub.interfaces.CRUDScreen;
import openlibraryhub.Errors;
import openlibraryhub.Util;
import openlibraryhub.database.BookDAO;
import openlibraryhub.database.LoanDAO;

public class Books implements CRUDScreen {
    public void welcome() {
        boolean running = true;
        while (running) {
            println("1 - Cadastrar livro");
            println("2 - Atualizar livro");
            println("3 - Excluir livro");
            println("4 - Buscar livro");
            println("5 - Listar livros");
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
            print("Digite o nome do livro: ");
            String title = scanner.nextLine();
            if (title == null || title.isEmpty()) {
                throw new EmptyStringException();
            }

            print("Digite o nome do autor: ");
            String author = scanner.nextLine();
            if (author == null || author.isEmpty()) {
                throw new EmptyStringException();
            }

            print("Digite a seção: ");
            String section = scanner.nextLine();
            if (section == null || section.isEmpty()) {
                throw new EmptyStringException();
            }

            print("Digite o número de páginas: ");
            int pages = scanner.nextInt();

            print("Digite o ano de publicação: ");
            int year = scanner.nextInt();

            print("Digite a quantidade em estoque: ");
            int stock = scanner.nextInt();

            BookEntity bookEntity = BookDAO.getInstance().save(new BookEntity(title, author,
                                                                              section, pages,
                                                                              year, stock));

            if (bookEntity != null && bookEntity.getId() != null) {
                clean();
                println("Livro cadastrado com sucesso!\n");
            }
        } catch (InputMismatchException | EmptyStringException e) {
            Util.handleException(e);
        }
    }

    public void update() {
        try {
            scanner.nextLine();
            print("Digite o id do livro: ");
            int id = scanner.nextInt();
            BookEntity bookEntity = BookDAO.getInstance().getById(id);

            if (bookEntity == null) {
                throw new EntityNotFoundException(BookEntity.class);
            }

            scanner.nextLine();
            print("Digite o novo nome do livro: ");
            String title = scanner.nextLine();
            if (title == null || title.isEmpty()) {
                throw new EmptyStringException();
            }
            bookEntity.setTitle(title);

            print("Digite o novo nome do autor: ");
            String author = scanner.nextLine();
            if (author == null || author.isEmpty()) {
                throw new EmptyStringException();
            }
            bookEntity.setAuthor(author);

            print("Digite a nova seção: ");
            String section = scanner.nextLine();
            if (section == null || section.isEmpty()) {
                throw new EmptyStringException();
            }
            bookEntity.setSection(section);

            print("Digite o novo número de páginas: ");
            int pages = scanner.nextInt();
            bookEntity.setPages(pages);

            print("Digite o novo ano de publicação: ");
            int year = scanner.nextInt();
            bookEntity.setYear(year);

            print("Digite a nova quantidade em estoque: ");
            int stock = scanner.nextInt();
            bookEntity.setStock(stock);

            BookEntity updatedBookEntity = BookDAO.getInstance().update(bookEntity);

            if (updatedBookEntity != null && updatedBookEntity.getId() != null) {
                clean();
                println("Livro atualizado com sucesso!\n");
            }
        } catch (InputMismatchException
                | EmptyStringException
                | EntityNotFoundException e) {
            Util.handleException(e);
        }
    }

    public void delete() {
        try {
            print("Digite o id do livro: ");
            int id = scanner.nextInt();
            BookEntity bookEntity = BookDAO.getInstance().getById(id);

            if (bookEntity == null) {
                throw new EntityNotFoundException(BookEntity.class);
            }

            LoanEntity loanEntity = LoanDAO.getInstance().getByBookId(id);
            if (loanEntity != null) {
                clean();
                println("O livro não pode ser excluído, pois está emprestado!\n");
                return;
            }

            BookDAO.getInstance().delete(bookEntity);
            println("");
        } catch (InputMismatchException | EntityNotFoundException e) {
            Util.handleException(e);
        }
    }

    public void search() {
        try {
            print("Digite o id do livro: ");
            int id = scanner.nextInt();
            BookEntity bookEntity = BookDAO.getInstance().getById(id);

            if (bookEntity != null) {
                clean();
                println("Livro encontrado!\n");
                println(bookEntity.toString());
            } else {
                throw new EntityNotFoundException(BookEntity.class);
            }
        } catch (InputMismatchException | EntityNotFoundException e) {
            Util.handleException(e);
        }
    }

    public void list() {
        List<BookEntity> books = BookDAO.getInstance().getAll();
        if (!books.isEmpty()) {
            books.forEach(bookEntity -> println(bookEntity.toString()));
        } else {
            println("Nenhum livro encontrado!\n");
        }
    }

    private Books() {}

    private static final Books instance = new Books();

    public static synchronized Books getInstance() {
        return instance;
    }
}
