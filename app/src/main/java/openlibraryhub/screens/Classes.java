package openlibraryhub.screens;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import static openlibraryhub.Console.clean;
import static openlibraryhub.Console.print;
import static openlibraryhub.Console.println;
import static openlibraryhub.Console.scanner;

import openlibraryhub.Errors;
import openlibraryhub.Util;
import openlibraryhub.database.ClassDAO;
import openlibraryhub.database.StudentDAO;
import openlibraryhub.entities.ClassEntity;
import openlibraryhub.entities.StudentEntity;
import openlibraryhub.exceptions.EmptyStringException;
import openlibraryhub.exceptions.EntityNotFoundException;
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
                println(Errors.INVALID_OPTION_MESSAGE);
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

            ClassEntity classEntity = ClassDAO.getInstance().save(new ClassEntity(name));

            if (classEntity != null && classEntity.getId() != null) {
                clean();
                println("Turma cadastrada com sucesso!\n");
            }
        } catch (InputMismatchException | EmptyStringException e) {
            Util.handleException(e);
        }
    }

    public void update() {
        try {
            print("Digite o id da turma: ");
            int id = scanner.nextInt();
            ClassEntity classEntity = ClassDAO.getInstance().getById(id);

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

            ClassEntity updatedClassEntity = ClassDAO.getInstance().update(classEntity);

            if (updatedClassEntity != null && updatedClassEntity.getId() != null) {
                clean();
                println("Turma atualizada com sucesso!\n");
            }
        } catch (InputMismatchException
                | EmptyStringException
                | EntityNotFoundException e) {
            Util.handleException(e);
        }
    }

    public void delete() {
        try {
            print("Digite o id da turma: ");
            int id = scanner.nextInt();
            ClassEntity classEntity = ClassDAO.getInstance().getById(id);

            if (classEntity == null) {
                throw new EntityNotFoundException(ClassEntity.class);
            }

            List<StudentEntity> students = StudentDAO.getInstance().getByClassId(id);
            if (students != null && !students.isEmpty()) {
                clean();
                println("Não é possível excluir uma turma com alunos matriculados!\n");
                return;
            }

            ClassDAO.getInstance().delete(classEntity);
            println("");
        } catch (InputMismatchException | EntityNotFoundException e) {
            Util.handleException(e);
        }
    }

    public void search() {
        try {
            print("Digite o id da turma: ");
            int id = scanner.nextInt();
            ClassEntity classEntity = ClassDAO.getInstance().getById(id);

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
        List<ClassEntity> classes = ClassDAO.getInstance().getAll();
        if (!classes.isEmpty()) {
            classes.forEach(classEntity -> println(classEntity.toString()));
        } else {
            println("Nenhuma turma encontrada!\n");
        }
    }

    private Classes() {}

    private static final Classes instance = new Classes();

    public static synchronized Classes getInstance() {
        return instance;
    }
}
