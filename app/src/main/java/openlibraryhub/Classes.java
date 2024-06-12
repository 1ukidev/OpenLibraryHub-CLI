package openlibraryhub;

import java.util.InputMismatchException;
import java.util.Map;

import static openlibraryhub.Console.clean;
import static openlibraryhub.Console.print;
import static openlibraryhub.Console.println;
import static openlibraryhub.Console.scanner;

import openlibraryhub.database.ClassRepository;
import openlibraryhub.entities.ClassEntity;
import openlibraryhub.interfaces.CRUDScreen;

public class Classes implements CRUDScreen {
    public void welcome() {
        boolean running = true;
        while (running) {
            println("1 - Cadastrar turma");
            println("2 - Listar turmas");
            println("3 - Atualizar turma");
            println("4 - Excluir turma");
            println("5 - Voltar");
            print("--> ");

            running = handleOption();
        }
    }

    private final Map<Integer, Runnable> options = Map.of(
        1, () -> Classes.getInstance().create(),
        2, () -> println("TODO\n"),
        3, () -> println("TODO\n"),
        4, () -> println("TODO\n"),
        5, () -> println("Voltando ao menu principal...\n")
    );

    public boolean handleOption() {
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

    public void create() {
        try {
            scanner.nextLine();
            print("Digite o nome da turma: ");
            String name = scanner.nextLine();

            ClassEntity classEntity = ClassRepository.getInstance().save(new ClassEntity(name));

            if (classEntity != null && classEntity.getId() != null) {
                clean();
                println("Turma cadastrada com sucesso!\n");
            } else {
                println("Falha ao cadastrar turma!\n");
            }
        } catch (InputMismatchException e) {
            clean();
            println("Entrada inválida. Por favor, tente novamente.\n");
            scanner.next();
        }
    }

    private Classes() {}

    private static final Classes instance = new Classes();

    public static synchronized Classes getInstance() {
        return instance;
    }
}
