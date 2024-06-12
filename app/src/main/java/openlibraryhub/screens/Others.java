package openlibraryhub.screens;

import java.util.InputMismatchException;
import java.util.Map;

import static openlibraryhub.Console.clean;
import static openlibraryhub.Console.print;
import static openlibraryhub.Console.println;
import static openlibraryhub.Console.scanner;

import openlibraryhub.Constants;
import openlibraryhub.interfaces.Screen;

public class Others implements Screen {
    public void welcome() {
        boolean running = true;
        while (running) {
            println("1 - Sobre");
            println("2 - Sair");
            print("--> ");

            running = handleOption();
        }
    }

    private final Map<Integer, Runnable> options = Map.of(
        1, Others::about,
        2, () -> println("Voltando ao menu principal...\n")
    );

    public boolean handleOption() {
        try {
            int opcao = scanner.nextInt();
            clean();
            Runnable action = options.get(opcao);
            if (action != null) {
                action.run();
                if (opcao == 2) {
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

    private static void about() {
        println("OpenLibraryHub é um sistema de gerenciamento de bibliotecas.");
        println("Desenvolvido por: 1ukidev");
        println("GitHub: https://github.com/1ukidev");
        println("Versão: " + Constants.VERSION + "\n");
    }

    private Others() {}

    private static final Others instance = new Others();

    public static synchronized Others getInstance() {
        return instance;
    }
}
