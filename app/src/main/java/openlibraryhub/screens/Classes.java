package openlibraryhub.screens;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import static openlibraryhub.Console.clean;
import static openlibraryhub.Console.print;
import static openlibraryhub.Console.println;
import static openlibraryhub.Console.scanner;

import openlibraryhub.Util;
import openlibraryhub.database.ClassRepository;
import openlibraryhub.entities.ClassEntity;
import openlibraryhub.exceptions.EmptyStringException;
import openlibraryhub.exceptions.EntityNotFoundException;
import openlibraryhub.exceptions.FailedSaveException;
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
                throw new FailedSaveException(ClassEntity.class);
            }
        } catch (InputMismatchException
                | EmptyStringException
                | FailedSaveException e) {
            Util.handleException(e);
        }
    }

    public void update() {
        try {
            print("Digite o id da turma: ");
            int id = scanner.nextInt();
            ClassEntity classEntity = ClassRepository.getInstance().getById(id);

            if (classEntity == null) {
                throw new EntityNotFoundException(ClassEntity.class);
            }

            scanner.nextLine();
            print("Digite o novo nome da turma: ");
            String name = scanner.nextLine();
            if (name == null || name.isEmpty()) {
                throw new EmptyStringException();
            }
            classEntity.setName(name);

            ClassEntity updatedClassEntity = ClassRepository.getInstance().update(classEntity);

            if (updatedClassEntity != null && updatedClassEntity.getId() != null) {
                clean();
                println("Turma atualizada com sucesso!\n");
            } else {
                throw new FailedSaveException(ClassEntity.class);
            }
        } catch (InputMismatchException
                | EmptyStringException
                | EntityNotFoundException
                | FailedSaveException e) {
            Util.handleException(e);
        }
    }

    public void delete() {
        try {
            print("Digite o id da turma: ");
            int id = scanner.nextInt();
            ClassEntity classEntity = ClassRepository.getInstance().getById(id);

            if (classEntity != null) {
                ClassRepository.getInstance().delete(classEntity);
                println("");
            } else {
                throw new EntityNotFoundException(ClassEntity.class);
            }
        } catch (InputMismatchException | EntityNotFoundException e) {
            Util.handleException(e);
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
                throw new EntityNotFoundException(ClassEntity.class);
            }
        } catch (InputMismatchException | EntityNotFoundException e) {
            Util.handleException(e);
        }
    }

    public void list() {
        List<ClassEntity> classes = ClassRepository.getInstance().getAll();
        if (!classes.isEmpty()) {
            classes.forEach(classEntity -> println(classEntity.toString()));
        } else {
            println("Nenhuma ClassEntity encontrada!\n");
        }
    }

    private Classes() {}

    private static final Classes instance = new Classes();

    public static synchronized Classes getInstance() {
        return instance;
    }
}
