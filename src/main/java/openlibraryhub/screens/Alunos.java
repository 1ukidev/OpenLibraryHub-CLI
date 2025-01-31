package openlibraryhub.screens;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import openlibraryhub.Console;
import openlibraryhub.entities.AlunoEntity;
import openlibraryhub.entities.TurmaEntity;
import openlibraryhub.interfaces.CRUDScreen;
import openlibraryhub.repository.AlunoRepository;
import openlibraryhub.repository.TurmaRepository;

@Component
public class Alunos implements CRUDScreen {
    @Autowired
    private AlunoRepository repository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Override
    public void display() {
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

    @Override
    public boolean handleOption() {
        int option = Console.readInt();
        Console.clear();

        switch (option) {
            // 1 - Cadastrar aluno
            case 1:
                save();
                break;
            // 2 - Atualizar aluno
            case 2:
                update();
                break;
            // 3 - Excluir aluno
            case 3:
                delete();
                break;
            // 4 - Buscar aluno
            case 4:
                search();
                break;
            // 5 - Listar alunos
            case 5:
                list();
                break;
            // 6 - Voltar
            case 6:
                return false;
            default:
                Console.println("Opção inválida.\n");
                break;
        }

        return true;
    }

    @Override
    public void save() {
        Console.print("Digite o nome do aluno: ");
        String nome = Console.readString();

        Console.print("Digite o id da turma: ");
        Long turmaId = Console.readLong();

        Optional<TurmaEntity> turma = turmaRepository.findById(turmaId);
        if (!turma.isPresent()) {
            Console.println("Turma não encontrada.\n");
            return;
        }

        AlunoEntity aluno = new AlunoEntity(nome, turma.get());

        repository.save(aluno);

        Console.clear();
        Console.println("Aluno cadastrado com sucesso!\n");
    }

    @Override
    public void update() {
        Console.print("Digite o id do aluno a ser atualizado: ");
        long id = Console.readLong();

        Optional<AlunoEntity> alunoOptional = repository.findById(id);
        if (!alunoOptional.isPresent()) {
            Console.println("Aluno não encontrado.\n");
            return;
        }

        AlunoEntity aluno = alunoOptional.get();

        Console.print("Digite o nome do aluno: ");
        String nome = Console.readString();

        Console.print("Digite o id da turma: ");
        Long turmaId = Console.readLong();

        Optional<TurmaEntity> turma = turmaRepository.findById(turmaId);
        if (!turma.isPresent()) {
            Console.println("Turma não encontrada.\n");
            return;
        }

        aluno.setNome(nome);
        aluno.setTurma(turma.get());

        repository.save(aluno);

        Console.clear();
        Console.println("Aluno atualizado com sucesso!\n");
    }

    @Override
    public void delete() {
        Console.print("Digite o id do aluno a ser excluído: ");
        long id = Console.readLong();

        Optional<AlunoEntity> alunoOptional = repository.findById(id);
        if (!alunoOptional.isPresent()) {
            Console.println("Aluno não encontrado.\n");
            return;
        }

        repository.delete(alunoOptional.get());

        Console.clear();
        Console.println("Aluno excluído com sucesso!\n");
    }

    @Override
    public void search() {
        Console.print("Digite o id do aluno a ser buscado: ");
        long id = Console.readLong();

        repository.findById(id).ifPresentOrElse(
            aluno -> Console.println("\n" + aluno),
            () -> Console.println("Aluno não encontrado.\n")
        );
    }

    @Override
    public void list() {
        List<AlunoEntity> alunos = repository.findAll();

        if (alunos.isEmpty()) {
            Console.println("Nenhum aluno cadastrado.\n");
            return;
        }

        alunos.forEach(Console::println);
    }
}
