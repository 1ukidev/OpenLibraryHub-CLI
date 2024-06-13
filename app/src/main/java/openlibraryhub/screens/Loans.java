package openlibraryhub.screens;

import java.util.InputMismatchException;
import java.util.Map;

import static openlibraryhub.Console.clean;
import static openlibraryhub.Console.print;
import static openlibraryhub.Console.println;
import static openlibraryhub.Console.scanner;

import openlibraryhub.interfaces.CRUDScreen;
import openlibraryhub.Util;

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
                println("Opção inválida!\n");
            }
        } catch (InputMismatchException e) {
            Util.handleException(e);
        }
        return true;
    }

    public void save() {
        // TODO
        println("TODO\n");
    }

    public void update() {
        // TODO
        println("TODO\n");
    }

    public void delete() {
        // TODO
        println("TODO\n");
    }

    public void search() {
        // TODO
        println("TODO\n");
    }

    public void list() {
        // TODO
        println("TODO\n");
    }

    private Loans() {}

    private static final Loans instance = new Loans();

    public static synchronized Loans getInstance() {
        return instance;
    }
}
