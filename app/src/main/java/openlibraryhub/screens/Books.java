package openlibraryhub.screens;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import static openlibraryhub.Console.clean;
import static openlibraryhub.Console.print;
import static openlibraryhub.Console.println;
import static openlibraryhub.Console.scanner;

import openlibraryhub.entities.BookEntity;
import openlibraryhub.exceptions.EmptyStringException;
import openlibraryhub.interfaces.CRUDScreen;
import openlibraryhub.database.BookRepository;

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
        1, () -> save(),
        2, () -> update(),
        3, () -> delete(),
        4, () -> search(),
        5, () -> list(),
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
                println("Opção inválida!\n");
            }
        } catch (InputMismatchException e) {
            clean();
            println("Opção inválida!\n");
            scanner.next();
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
    
            BookEntity bookEntity = BookRepository.getInstance().save(new BookEntity(title, author,
                                                                                     section, pages,
                                                                                     year, stock));
    
            if (bookEntity != null && bookEntity.getId() != null) {
                clean();
                println("Livro cadastrado com sucesso!\n");
            } else {
                println("Falha ao cadastrar o livro.\n");
            }
        } catch (InputMismatchException e) {
            clean();
            println("Entrada inválida. Por favor, tente novamente.\n");
            scanner.next();
        } catch (EmptyStringException e) {
            clean();
            println("Entrada vazia detectada. Por favor, tente novamente.\n");
        }
    }

    public void update() {
        try {
            scanner.nextLine();
            print("Digite o id do livro: ");
            int id = scanner.nextInt();
            BookEntity bookEntity = BookRepository.getInstance().getById(id);
    
            if (bookEntity != null) {
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
    
                BookEntity updatedBookEntity = BookRepository.getInstance().update(bookEntity);
    
                if (updatedBookEntity != null && updatedBookEntity.getId() != null) {
                    clean();
                    println("Livro atualizado com sucesso!\n");
                } else {
                    println("Falha ao atualizar o livro!\n");
                }
            } else {
                clean();
                println("Livro não encontrado!\n");
            }
        } catch (InputMismatchException e) {
            clean();
            println("Entrada inválida. Por favor, tente novamente.\n");
            scanner.next();
        } catch (EmptyStringException e) {
            clean();
            println("Entrada vazia detectada. Por favor, tente novamente.\n");
        }
    }

    public void delete() {
        try {
            print("Digite o id do livro: ");
            int id = scanner.nextInt();
            BookEntity bookEntity = BookRepository.getInstance().getById(id);
    
            if (bookEntity != null) {
                BookRepository.getInstance().delete(bookEntity);
                println("");
            } else {
                clean();
                println("Livro não encontrado!\n");
            }
        } catch (InputMismatchException e) {
            clean();
            println("Entrada inválida. Por favor, tente novamente.\n");
            scanner.next();
        }
    }

    public void search() {
        try {
            print("Digite o id do livro: ");
            int id = scanner.nextInt();
            BookEntity bookEntity = BookRepository.getInstance().getById(id);
    
            if (bookEntity != null) {
                clean();
                println("Livro encontrado!\n");
                println(bookEntity.toString());
            } else {
                clean();
                println("Livro não encontrado!\n");
            }
        } catch (InputMismatchException e) {
            clean();
            println("Entrada inválida. Por favor, tente novamente.\n");
            scanner.next();
        }
    }

    public void list() {
        List<BookEntity> books = BookRepository.getInstance().getAll();
        if (!books.isEmpty()) {
            books.forEach(bookEntity -> println(bookEntity.toString()));
        } else {
            println("Nenhum livro cadastrado!\n");
        }
    }

    private Books() {};

    private static final Books instance = new Books();

    public static synchronized Books getInstance() {
        return instance;
    }
}
