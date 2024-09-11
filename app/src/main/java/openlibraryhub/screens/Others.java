package openlibraryhub.screens;

import java.util.InputMismatchException;
import java.util.Map;

import openlibraryhub.Console;
import openlibraryhub.Constants;
import openlibraryhub.Errors;
import openlibraryhub.Util;
import openlibraryhub.interfaces.Screen;

public class Others implements Screen {
    public void welcome() {
        boolean running = true;
        while (running) {
            Console.println("1 - Sobre");
            Console.println("2 - Sair");
            Console.print("--> ");

            running = handleOption();
        }
    }

    private final Map<Integer, Runnable> options = Map.of(
        1, this::about,
        2, () -> Console.println("Voltando ao menu principal...\n")
    );

    public boolean handleOption() {
        try {
            int opcao = Console.readInt();
            Console.clear();
            Runnable action = options.get(opcao);

            if (action != null) {
                action.run();
                if (opcao == 2) {
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

    private void about() {
        Console.println("OpenLibraryHub é um sistema de gerenciamento de bibliotecas.");
        Console.println("Desenvolvido por: 1ukidev");
        Console.println("GitHub: https://github.com/1ukidev/OpenLibraryHub-CLI");
        Console.println("Versão: " + Constants.VERSION + "\n");
    }

    private Others() {}

    private static final Others instance = new Others();

    public static synchronized Others getInstance() {
        return instance;
    }
}
