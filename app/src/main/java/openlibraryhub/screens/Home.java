package openlibraryhub.screens;

import java.util.InputMismatchException;
import java.util.Map;

import openlibraryhub.Console;
import openlibraryhub.Errors;
import openlibraryhub.Util;
import openlibraryhub.interfaces.Screen;

public class Home implements Screen {
    public void welcome() {
        boolean running = true;
        while (running) {
            Console.println("Bem-vindo ao OpenLibraryHub!\n");
            Console.println(Util.greet() + '\n');
            Console.println("O que deseja fazer?");
            Console.println("1 - Livros");
            Console.println("2 - Turmas");
            Console.println("3 - Alunos");
            Console.println("4 - Empréstimo");
            Console.println("5 - Outros");
            Console.println("6 - Sair");
            Console.print("--> ");

            running = handleOption();
        }
    }

    private final Map<Integer, Runnable> options = Map.of(
        1, () -> Books.getInstance().welcome(),
        2, () -> Classes.getInstance().welcome(),
        3, () -> Students.getInstance().welcome(),
        4, () -> Loans.getInstance().welcome(),
        5, () -> Others.getInstance().welcome(),
        6, () -> {
            Console.println("Até mais!");
            System.exit(0);
        }
    );

    public boolean handleOption() {
        try {
            int opcao = Console.readInt();
            Console.clear();
            Runnable action = options.get(opcao);

            if (action != null) {
                action.run();
            } else {
                Console.println(Errors.INVALID_OPTION_MESSAGE);
            }
        } catch (InputMismatchException e) {
            Util.handleException(e);
        }
        return true;
    }

    private Home() {}

    private static final Home instance = new Home();

    public static Home getInstance() {
        return instance;
    }
}
