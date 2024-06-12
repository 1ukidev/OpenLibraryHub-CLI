package openlibraryhub;

import java.util.InputMismatchException;
import java.util.Map;

import static openlibraryhub.Console.clean;
import static openlibraryhub.Console.print;
import static openlibraryhub.Console.println;
import static openlibraryhub.Console.scanner;

import openlibraryhub.database.ClassRepository;
import openlibraryhub.database.StudentRepository;
import openlibraryhub.entities.ClassEntity;
import openlibraryhub.entities.StudentEntity;
import openlibraryhub.interfaces.CRUDScreen;

public class Students implements CRUDScreen {
    public void welcome() {
        boolean running = true;
        while (running) {
            println("1 - Cadastrar aluno");
            println("2 - Listar alunos");
            println("3 - Atualizar aluno");
            println("4 - Excluir aluno");
            println("5 - Voltar");
            print("--> ");

            running = handleOption();
        }
    }

    private final Map<Integer, Runnable> options = Map.of(
        1, () -> Students.getInstance().create(),
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
            print("Digite o nome do estudante: ");
            String name = scanner.nextLine();

            print("Digite o id da turma: ");
            int classId = scanner.nextInt();
            ClassEntity classEntity = ClassRepository.getInstance().getById(classId);

            StudentEntity studentEntity = StudentRepository.getInstance().save(new StudentEntity(name, classEntity));

            if (studentEntity != null && studentEntity.getId() != null) {
                clean();
                println("Estudante cadastrado com sucesso!\n");
            } else {
                println("Falha ao cadastrar estudante.\n");
            }
        } catch (InputMismatchException e) {
            clean();
            println("Entrada inválida. Por favor, tente novamente.\n");
            scanner.next();
        }
    }

    private Students() {}

    private static final Students instance = new Students();

    public static synchronized Students getInstance() {
        return instance;
    }
}
