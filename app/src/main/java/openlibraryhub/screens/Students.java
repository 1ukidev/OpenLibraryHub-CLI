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

public class Students implements CRUDScreen {
    public void welcome() {
        boolean running = true;
        while (running) {
            println("1 - Cadastrar aluno");
            println("2 - Atualizar aluno");
            println("3 - Excluir aluno");
            println("4 - Buscar aluno");
            println("5 - Listar alunos");
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
            print("Digite o nome do estudante: ");
            String name = scanner.nextLine();
            if (name == null || name.isEmpty()) {
                throw new EmptyStringException();
            }

            print("Digite o id da turma: ");
            int classId = scanner.nextInt();
            ClassEntity classEntity = ClassDAO.getInstance().getById(classId);
            if (classEntity == null) {
                throw new EntityNotFoundException(ClassEntity.class);
            }

            StudentEntity studentEntity = StudentDAO.getInstance().save(new StudentEntity(name, classEntity));

            if (studentEntity != null && studentEntity.getId() != null) {
                clean();
                println("Estudante cadastrado com sucesso!\n");
            }
        } catch (InputMismatchException
                | EmptyStringException
                | EntityNotFoundException e) {
            Util.handleException(e);
        }
    }

    public void update() {
        try {
            scanner.nextLine();
            print("Digite o id do estudante: ");
            int id = scanner.nextInt();
            StudentEntity studentEntity = StudentDAO.getInstance().getById(id);

            if (studentEntity == null) {
                throw new EntityNotFoundException(StudentEntity.class);
            }

            scanner.nextLine();
            print("Digite o novo nome do estudante: ");
            String name = scanner.nextLine();
            if (name == null || name.isEmpty()) {
                throw new EmptyStringException();
            }
            studentEntity.setName(name);

            print("Digite o novo id da turma: ");
            int classId = scanner.nextInt();
            ClassEntity classEntity = ClassDAO.getInstance().getById(classId);
            if (classEntity == null) {
                throw new EntityNotFoundException(ClassEntity.class);
            }
            studentEntity.setClassEntity(classEntity);

            StudentEntity updatedStudentEntity = StudentDAO.getInstance().update(studentEntity);

            if (updatedStudentEntity != null && updatedStudentEntity.getId() != null) {
                clean();
                println("Estudante atualizado com sucesso!\n");
            }
        } catch (InputMismatchException
                | EmptyStringException
                | EntityNotFoundException e) {
            Util.handleException(e);
        }
    }

    public void delete() {
        try {
            scanner.nextLine();
            print("Digite o id do estudante: ");
            int id = scanner.nextInt();
            StudentEntity studentEntity = StudentDAO.getInstance().getById(id);

            if (studentEntity != null) {
                StudentDAO.getInstance().delete(studentEntity);
                println("");
            } else {
                throw new EntityNotFoundException(StudentEntity.class);
            }
        } catch (InputMismatchException | EntityNotFoundException e) {
            Util.handleException(e);
        }
    }

    public void search() {
        try {
            print("Digite o id do estudante: ");
            int id = scanner.nextInt();
            StudentEntity studentEntity = StudentDAO.getInstance().getById(id);

            if (studentEntity != null) {
                clean();
                println("Estudante encontrado!\n");
                println(studentEntity.toString());
            } else {
                throw new EntityNotFoundException(StudentEntity.class);
            }
        } catch (InputMismatchException | EntityNotFoundException e) {
            Util.handleException(e);
        }
    }

    public void list() {
        List<StudentEntity> students = StudentDAO.getInstance().getAll();
        if (!students.isEmpty()) {
            students.forEach(studentEntity -> println(studentEntity.toString()));
        } else {
            println("Nenhuma StudentEntity encontrada!\n");
        }
    }

    private Students() {}

    private static final Students instance = new Students();

    public static synchronized Students getInstance() {
        return instance;
    }
}
