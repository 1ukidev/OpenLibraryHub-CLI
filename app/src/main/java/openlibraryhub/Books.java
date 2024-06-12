package openlibraryhub;

import java.util.InputMismatchException;
import java.util.Map;

import static openlibraryhub.Console.clean;
import static openlibraryhub.Console.print;
import static openlibraryhub.Console.println;
import static openlibraryhub.Console.scanner;

import openlibraryhub.entities.BookEntity;
import openlibraryhub.database.BookRepository;

public class Books {
    public static void welcome() {
        boolean running = true;
        while (running) {
            println("1 - Adicionar Livro");
            println("2 - Listar Livros");
            println("3 - Buscar Livro");
            println("4 - Remover Livro");
            println("5 - Voltar");
            print("--> ");

            running = handleOption();
        }
    }

    private static final Map<Integer, Runnable> options = Map.of(
        1, Books::create,
        2, () -> println("TODO\n"),
        3, () -> println("TODO\n"),
        4, () -> println("TODO\n"),
        5, () -> println("Voltando ao menu principal...\n")
    );

    public static boolean handleOption() {
        try {
            int opcao = scanner.nextInt();
            clean();
            Runnable action = options.get(opcao);
            if (action != null) {
                action.run();
                if (opcao == 5) {
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

    public static void create() {
        try {
            scanner.nextLine();
            print("Digite o nome do livro: ");
            String title = scanner.nextLine();
    
            print("Digite o nome do autor: ");
            String author = scanner.nextLine();
    
            print("Digite a seção: ");
            String section = scanner.nextLine();
    
            print("Digite o número de páginas: ");
            int pages = scanner.nextInt();
    
            print("Digite o ano de publicação: ");
            int year = scanner.nextInt();
    
            print("Digite a quantidade em estoque: ");
            int stock = scanner.nextInt();
    
            BookEntity bookEntity = BookRepository.save(new BookEntity(title, author,
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
        }
    }
}
