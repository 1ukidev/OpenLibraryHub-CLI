package openlibraryhub;

import static java.lang.System.exit;

import java.util.InputMismatchException;
import java.util.Map;

import static openlibraryhub.Console.clean;
import static openlibraryhub.Console.print;
import static openlibraryhub.Console.println;
import static openlibraryhub.Console.scanner;
import static openlibraryhub.Util.greet;

import openlibraryhub.interfaces.Screen;

public class Home implements Screen {
    public void welcome() {
        boolean running = true;
        while (running) {
            println("Bem-vindo ao OpenLibraryHub!\n");
            println(greet() + "\n");
            println("O que deseja fazer?");
            println("1 - Livros");
            println("2 - Turmas");
            println("3 - Alunos");
            println("4 - Outros");
            println("5 - Sair");
            print("--> ");

            running = handleOption();
        }
    }

    private final Map<Integer, Runnable> options = Map.of(
        1, () -> Books.getInstance().welcome(),
        2, () -> Classes.getInstance().welcome(),
        3, () -> Students.getInstance().welcome(),
        4, () -> Others.getInstance().welcome(),
        5, () -> {
            println("Até mais!");
            exit(0);
        }
    );

    public boolean handleOption() {
        try {
            int opcao = scanner.nextInt();
            clean();
            Runnable action = options.get(opcao);
            if (action != null) {
                action.run();
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

    private Home() {}

    private static final Home instance = new Home();

    public static synchronized Home getInstance() {
        return instance;
    }
}
