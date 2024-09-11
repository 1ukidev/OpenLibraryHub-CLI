package openlibraryhub.screens;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import openlibraryhub.entities.BookEntity;
import openlibraryhub.entities.LoanEntity;
import openlibraryhub.exceptions.EmptyStringException;
import openlibraryhub.exceptions.EntityNotFoundException;
import openlibraryhub.interfaces.CRUDScreen;
import openlibraryhub.Console;
import openlibraryhub.Errors;
import openlibraryhub.Util;
import openlibraryhub.database.BookDAO;
import openlibraryhub.database.LoanDAO;

public class Books implements CRUDScreen {
    public void welcome() {
        boolean running = true;
        while (running) {
            Console.println("1 - Cadastrar livro");
            Console.println("2 - Atualizar livro");
            Console.println("3 - Excluir livro");
            Console.println("4 - Buscar livro");
            Console.println("5 - Listar livros");
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
            Console.print("Digite o nome do livro: ");
            String title = Console.read();
            if (title == null || title.isEmpty()) {
                throw new EmptyStringException();
            }

            Console.print("Digite o nome do autor: ");
            String author = Console.read();
            if (author == null || author.isEmpty()) {
                throw new EmptyStringException();
            }

            Console.print("Digite a seção: ");
            String section = Console.read();
            if (section == null || section.isEmpty()) {
                throw new EmptyStringException();
            }

            Console.print("Digite o número de páginas: ");
            int pages = Console.readInt();

            Console.print("Digite o ano de publicação: ");
            int year = Console.readInt();

            Console.print("Digite a quantidade em estoque: ");
            int stock = Console.readInt();

            BookEntity temp = new BookEntity(title, author, section, pages, year, stock);
            BookEntity bookEntity = BookDAO.getInstance().save(temp);

            if (bookEntity != null && bookEntity.getId() != null) {
                Console.clear();
                Console.println("Livro cadastrado com sucesso!\n");
            }
        } catch (InputMismatchException | EmptyStringException e) {
            Util.handleException(e);
        }
    }

    public void update() {
        try {
            Console.print("Digite o id do livro: ");
            int id = Console.readInt();
            BookEntity bookEntity = BookDAO.getInstance().getById(id);

            if (bookEntity == null) {
                throw new EntityNotFoundException(BookEntity.class);
            }

            Console.print("Digite o novo nome do livro: ");
            String title = Console.read();
            if (title == null || title.isEmpty()) {
                throw new EmptyStringException();
            }
            bookEntity.setTitle(title);

            Console.print("Digite o novo nome do autor: ");
            String author = Console.read();
            if (author == null || author.isEmpty()) {
                throw new EmptyStringException();
            }
            bookEntity.setAuthor(author);

            Console.print("Digite a nova seção: ");
            String section = Console.read();
            if (section == null || section.isEmpty()) {
                throw new EmptyStringException();
            }
            bookEntity.setSection(section);

            Console.print("Digite o novo número de páginas: ");
            int pages = Console.readInt();
            bookEntity.setPages(pages);

            Console.print("Digite o novo ano de publicação: ");
            int year = Console.readInt();
            bookEntity.setYear(year);

            Console.print("Digite a nova quantidade em estoque: ");
            int stock = Console.readInt();
            bookEntity.setStock(stock);

            BookEntity updatedBookEntity = BookDAO.getInstance().update(bookEntity);

            if (updatedBookEntity != null && updatedBookEntity.getId() != null) {
                Console.clear();
                Console.println("Livro atualizado com sucesso!\n");
            }
        } catch (InputMismatchException
                | EmptyStringException
                | EntityNotFoundException e) {
            Util.handleException(e);
        }
    }

    public void delete() {
        try {
            Console.print("Digite o id do livro: ");
            int id = Console.readInt();
            BookEntity bookEntity = BookDAO.getInstance().getById(id);

            if (bookEntity == null) {
                throw new EntityNotFoundException(BookEntity.class);
            }

            LoanEntity loanEntity = LoanDAO.getInstance().getByBookId(id);
            if (loanEntity != null) {
                Console.clear();
                Console.println("O livro não pode ser excluído, pois está emprestado!\n");
                return;
            }

            BookDAO.getInstance().delete(bookEntity);
            Console.println("");
        } catch (InputMismatchException | EntityNotFoundException e) {
            Util.handleException(e);
        }
    }

    public void search() {
        try {
            Console.print("Digite o id do livro: ");
            int id = Console.readInt();
            BookEntity bookEntity = BookDAO.getInstance().getById(id);

            if (bookEntity != null) {
                Console.clear();
                Console.println("Livro encontrado!\n");
                Console.println(bookEntity);
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
            books.forEach(bookEntity -> Console.println(bookEntity));
        } else {
            Console.println("Nenhum livro encontrado!\n");
        }
    }

    private Books() {}

    private static final Books instance = new Books();

    public static synchronized Books getInstance() {
        return instance;
    }
}
