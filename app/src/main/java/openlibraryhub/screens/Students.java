package openlibraryhub.screens;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import static openlibraryhub.Console.clean;
import static openlibraryhub.Console.print;
import static openlibraryhub.Console.println;
import static openlibraryhub.Console.scanner;

import openlibraryhub.database.ClassRepository;
import openlibraryhub.database.StudentRepository;
import openlibraryhub.entities.ClassEntity;
import openlibraryhub.entities.StudentEntity;
import openlibraryhub.exceptions.EmptyStringException;
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
            print("Digite o nome do estudante: ");
            String name = scanner.nextLine();
            if (name == null || name.isEmpty()) {
                throw new EmptyStringException();
            }

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
        } catch (EmptyStringException e) {
            clean();
            println("Entrada vazia detectada. Por favor, tente novamente.\n");
        }
    }

    public void update() {
        try {
            scanner.nextLine();
            print("Digite o id do estudante: ");
            int id = scanner.nextInt();
            StudentEntity studentEntity = StudentRepository.getInstance().getById(id);

            if (studentEntity != null) {
                scanner.nextLine();
                print("Digite o novo nome do estudante: ");
                String name = scanner.nextLine();
                studentEntity.setName(name);
                if (name == null || name.isEmpty()) {
                    throw new EmptyStringException();
                }

                print("Digite o novo id da turma: ");
                int classId = scanner.nextInt();
                ClassEntity classEntity = ClassRepository.getInstance().getById(classId);
                studentEntity.setClassEntity(classEntity);

                StudentEntity updatedStudentEntity = StudentRepository.getInstance().update(studentEntity);

                if (updatedStudentEntity != null && updatedStudentEntity.getId() != null) {
                    clean();
                    println("Estudante atualizado com sucesso!\n");
                } else {
                    println("Falha ao atualizar estudante.\n");
                }
            } else {
                println("Estudante não encontrado.\n");
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
            scanner.nextLine();
            print("Digite o id do estudante: ");
            int id = scanner.nextInt();
            StudentEntity studentEntity = StudentRepository.getInstance().getById(id);

            if (studentEntity != null) {
                StudentRepository.getInstance().delete(studentEntity);
                println("");
            } else {
                clean();
                println("Estudante não encontrado.\n");
            }
        } catch (InputMismatchException e) {
            clean();
            println("Entrada inválida. Por favor, tente novamente.\n");
            scanner.next();
        }
    }

    public void search() {
        try {
            print("Digite o id do estudante: ");
            int id = scanner.nextInt();
            StudentEntity studentEntity = StudentRepository.getInstance().getById(id);

            if (studentEntity != null) {
                clean();
                println("Estudante encontrado!\n");
                println(studentEntity.toString());
            } else {
                clean();
                println("Estudante não encontrado!\n");
            }
        } catch (InputMismatchException e) {
            clean();
            println("Entrada inválida. Por favor, tente novamente.\n");
            scanner.next();
        }
    }

    public void list() {
        List<StudentEntity> students = StudentRepository.getInstance().getAll();
        if (!students.isEmpty()) {
            students.forEach(studentEntity -> println(studentEntity.toString()));
        } else {
            println("Nenhum estudante cadastrado.\n");
        }
    }

    private Students() {}

    private static final Students instance = new Students();

    public static synchronized Students getInstance() {
        return instance;
    }
}
