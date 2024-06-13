package openlibraryhub.screens;

import java.util.InputMismatchException;
import java.util.Map;

import static openlibraryhub.Console.clean;
import static openlibraryhub.Console.print;
import static openlibraryhub.Console.println;
import static openlibraryhub.Console.scanner;

import openlibraryhub.database.ClassRepository;
import openlibraryhub.entities.ClassEntity;
import openlibraryhub.exceptions.EmptyStringException;
import openlibraryhub.interfaces.CRUDScreen;

public class Classes implements CRUDScreen {
    public void welcome() {
        boolean running = true;
        while (running) {
            println("1 - Cadastrar turma");
            println("2 - Atualizar turma");
            println("3 - Excluir turma");
            println("4 - Buscar turma");
            println("5 - Listar turmas");
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
            print("Digite o nome da turma: ");
            String name = scanner.nextLine();
            if (name == null || name.isEmpty()) {
                throw new EmptyStringException();
            }

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
        } catch (EmptyStringException e) {
            clean();
            println("Entrada vazia detectada. Por favor, tente novamente.\n");
        }
    }

    public void update() {
        try {
            print("Digite o id da turma: ");
            int id = scanner.nextInt();
            ClassEntity classEntity = ClassRepository.getInstance().getById(id);

            if (classEntity != null) {
                scanner.nextLine();
                print("Digite o novo nome da turma: ");
                String name = scanner.nextLine();
                classEntity.setName(name);
                if (name == null || name.isEmpty()) {
                    throw new EmptyStringException();
                }

                ClassEntity updatedClassEntity = ClassRepository.getInstance().update(classEntity);

                if (updatedClassEntity != null && updatedClassEntity.getId() != null) {
                    clean();
                    println("Turma atualizada com sucesso!\n");
                } else {
                    println("Falha ao atualizar turma!\n");
                }
            } else {
                clean();
                println("Turma não encontrada!\n");
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
            print("Digite o id da turma: ");
            int id = scanner.nextInt();
            ClassEntity classEntity = ClassRepository.getInstance().getById(id);

            if (classEntity != null) {
                ClassRepository.getInstance().delete(classEntity);
            } else {
                clean();
                println("Turma não encontrada!\n");
            }
        } catch (InputMismatchException e) {
            clean();
            println("Entrada inválida. Por favor, tente novamente.\n");
            scanner.next();
        }
    }

    public void search() {
        try {
            print("Digite o id da turma: ");
            int id = scanner.nextInt();
            ClassEntity classEntity = ClassRepository.getInstance().getById(id);

            if (classEntity != null) {
                clean();
                println("Turma encontrada!\n");
                println(classEntity.toString());
            } else {
                clean();
                println("Turma não encontrada!\n");
            }
        } catch (InputMismatchException e) {
            clean();
            println("Entrada inválida. Por favor, tente novamente.\n");
            scanner.next();
        }
    }

    public void list() {
        ClassRepository.getInstance().getAll().forEach(classEntity -> println(classEntity.toString()));
    }

    private Classes() {}

    private static final Classes instance = new Classes();

    public static synchronized Classes getInstance() {
        return instance;
    }
}
