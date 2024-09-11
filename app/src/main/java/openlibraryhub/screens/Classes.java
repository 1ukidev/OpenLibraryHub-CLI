package openlibraryhub.screens;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import openlibraryhub.Console;
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
            Console.println("1 - Cadastrar turma");
            Console.println("2 - Atualizar turma");
            Console.println("3 - Excluir turma");
            Console.println("4 - Buscar turma");
            Console.println("5 - Listar turmas");
            Console.println("6 - Voltar");
            Console.print("--> ");

            running = handleOption();
        }
    }

    private final Map<Integer, Runnable> options = Map.of(
        1, this::save,
        2, this::update,
        3, this::delete,
        4, this::search,
        5, this::list,
        6, () -> Console.println("Voltando ao menu principal...\n")
    );

    public boolean handleOption() {
        try {
            int opcao = Console.readInt();
            Console.clear();
            Runnable action = options.get(opcao);

            if (action != null) {
                action.run();
                if (opcao == 6) {
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

    public void save() {
        try {
            Console.print("Digite o nome da turma: ");
            String name = Console.read();
            if (name == null || name.isEmpty()) {
                throw new EmptyStringException();
            }

            ClassEntity classEntity = ClassDAO.getInstance().save(new ClassEntity(name));

            if (classEntity != null && classEntity.getId() != null) {
                Console.clear();
                Console.println("Turma cadastrada com sucesso!\n");
            }
        } catch (InputMismatchException | EmptyStringException e) {
            Util.handleException(e);
        }
    }

    public void update() {
        try {
            Console.print("Digite o id da turma: ");
            int id = Console.readInt();
            ClassEntity classEntity = ClassDAO.getInstance().getById(id);

            if (classEntity == null) {
                throw new EntityNotFoundException(ClassEntity.class);
            }

            Console.print("Digite o novo nome da turma: ");
            String name = Console.read();
            if (name == null || name.isEmpty()) {
                throw new EmptyStringException();
            }
            classEntity.setName(name);

            ClassEntity updatedClassEntity = ClassDAO.getInstance().update(classEntity);

            if (updatedClassEntity != null && updatedClassEntity.getId() != null) {
                Console.clear();
                Console.println("Turma atualizada com sucesso!\n");
            }
        } catch (InputMismatchException
                | EmptyStringException
                | EntityNotFoundException e) {
            Util.handleException(e);
        }
    }

    public void delete() {
        try {
            Console.print("Digite o id da turma: ");
            int id = Console.readInt();
            ClassEntity classEntity = ClassDAO.getInstance().getById(id);

            if (classEntity == null) {
                throw new EntityNotFoundException(ClassEntity.class);
            }

            List<StudentEntity> students = StudentDAO.getInstance().getByClassId(id);
            if (students != null && !students.isEmpty()) {
                Console.clear();
                Console.println("Não é possível excluir uma turma com alunos matriculados!\n");
                return;
            }

            ClassDAO.getInstance().delete(classEntity);
            Console.println("");
        } catch (InputMismatchException | EntityNotFoundException e) {
            Util.handleException(e);
        }
    }

    public void search() {
        try {
            Console.print("Digite o id da turma: ");
            int id = Console.readInt();
            ClassEntity classEntity = ClassDAO.getInstance().getById(id);

            if (classEntity != null) {
                Console.clear();
                Console.println("Turma encontrada!\n");
                Console.println(classEntity);
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
            classes.forEach(classEntity -> Console.println(classEntity));
        } else {
            Console.println("Nenhuma turma encontrada!\n");
        }
    }

    private Classes() {}

    private static final Classes instance = new Classes();

    public static synchronized Classes getInstance() {
        return instance;
    }
}
