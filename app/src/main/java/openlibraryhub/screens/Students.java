package openlibraryhub.screens;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import openlibraryhub.Console;
import openlibraryhub.Errors;
import openlibraryhub.Util;
import openlibraryhub.database.ClassDAO;
import openlibraryhub.database.LoanDAO;
import openlibraryhub.database.StudentDAO;
import openlibraryhub.entities.ClassEntity;
import openlibraryhub.entities.LoanEntity;
import openlibraryhub.entities.StudentEntity;
import openlibraryhub.interfaces.CRUDScreen;

public class Students implements CRUDScreen {
    public void welcome() {
        boolean running = true;
        while (running) {
            Console.println("1 - Cadastrar aluno");
            Console.println("2 - Atualizar aluno");
            Console.println("3 - Excluir aluno");
            Console.println("4 - Buscar aluno");
            Console.println("5 - Listar alunos");
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
            Console.print("Digite o nome do estudante: ");
            String name = Console.read();

            Console.print("Digite o id da turma: ");
            int classId = Console.readInt();
            ClassEntity classEntity = ClassDAO.getInstance().getById(classId);

            StudentEntity studentEntity = StudentDAO.getInstance().save(new StudentEntity(name, classEntity));

            if (studentEntity != null && studentEntity.getId() != null) {
                Console.clear();
                Console.println("Estudante cadastrado com sucesso!\n");
            }
        } catch (Exception e) {
            Util.handleException(e);
        }
    }

    public void update() {
        try {
            Console.print("Digite o id do estudante: ");
            int id = Console.readInt();
            StudentEntity studentEntity = StudentDAO.getInstance().getById(id);

            Console.print("Digite o novo nome do estudante: ");
            String name = Console.read();
            studentEntity.setName(name);

            Console.print("Digite o novo id da turma: ");
            int classId = Console.readInt();
            ClassEntity classEntity = ClassDAO.getInstance().getById(classId);
            studentEntity.setClassEntity(classEntity);

            StudentEntity updatedStudentEntity = StudentDAO.getInstance().update(studentEntity);

            if (updatedStudentEntity != null && updatedStudentEntity.getId() != null) {
                Console.clear();
                Console.println("Estudante atualizado com sucesso!\n");
            }
        } catch (Exception e) {
            Util.handleException(e);
        }
    }

    public void delete() {
        try {
            Console.print("Digite o id do estudante: ");
            int id = Console.readInt();
            StudentEntity studentEntity = StudentDAO.getInstance().getById(id);

            LoanEntity loanEntity = LoanDAO.getInstance().getByStudentId(id);
            if (loanEntity != null) {
                Console.clear();
                Console.println("O estudante possui empréstimos pendentes e não pode ser excluído!\n");
                return;
            }

            StudentDAO.getInstance().delete(studentEntity);
            Console.println("");
        } catch (Exception e) {
            Util.handleException(e);
        }
    }

    public void search() {
        try {
            Console.print("Digite o id do estudante: ");
            int id = Console.readInt();
            StudentEntity studentEntity = StudentDAO.getInstance().getById(id);

            if (studentEntity != null) {
                Console.clear();
                Console.println("Estudante encontrado!\n");
                Console.println(studentEntity);
            }
        } catch (Exception e) {
            Util.handleException(e);
        }
    }

    public void list() {
        List<StudentEntity> students = StudentDAO.getInstance().getAll();
        if (!students.isEmpty()) {
            students.forEach(studentEntity -> Console.println(studentEntity));
        } else {
            Console.println("Nenhum estudante encontrado!\n");
        }
    }

    private Students() {}

    private static final Students instance = new Students();

    public static Students getInstance() {
        return instance;
    }
}
