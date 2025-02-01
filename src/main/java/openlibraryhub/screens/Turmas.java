package openlibraryhub.screens;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import openlibraryhub.Console;
import openlibraryhub.entities.TurmaEntity;
import openlibraryhub.interfaces.CRUDScreen;
import openlibraryhub.repository.TurmaRepository;

@Component
public class Turmas implements CRUDScreen {
    @Autowired
    private TurmaRepository repository;

    @Override
    public void display() {
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

    @Override
    public boolean handleOption() {
        int option = Console.readInt();
        Console.clear();

        switch (option) {
            // 1 - Cadastrar turma
            case 1:
                save();
                break;
            // 2 - Atualizar turma
            case 2:
                update();
                break;
            // 3 - Excluir turma
            case 3:
                delete();
                break;
            // 4 - Buscar turma
            case 4:
                search();
                break;
            // 5 - Listar turmas
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
        Console.print("Digite o nome da turma: ");
        String nome = Console.readString();

        repository.save(new TurmaEntity(nome));

        Console.clear();
        Console.println("Turma cadastrada com sucesso!\n");
    }

    @Override
    public void update() {
        Console.print("Digite o id da turma a ser atualizada: ");
        long id = Console.readLong();

        Optional<TurmaEntity> turmOptional = repository.findById(id);
        if (!turmOptional.isPresent()) {
            Console.println("Turma não encontrada.\n");
            return;
        }

        TurmaEntity turma = turmOptional.get();

        Console.print("Digite o novo nome da turma: ");
        String nome = Console.readString();

        turma.setNome(nome);

        repository.save(turma);

        Console.clear();
        Console.println("Turma atualizada com sucesso!\n");
    }

    @Override
    public void delete() {
        Console.print("Digite o id da turma a ser excluída: ");
        long id = Console.readLong();

        Optional<TurmaEntity> turmaOptional = repository.findById(id);
        if (!turmaOptional.isPresent()) {
            Console.println("Turma não encontrada.\n");
            return;
        }

        repository.delete(turmaOptional.get());

        Console.clear();
        Console.println("Turma excluída com sucesso!\n");
    }

    @Override
    public void search() {
        Console.print("Digite o id da turma a ser buscada: ");
        long id = Console.readLong();

        repository.findById(id).ifPresentOrElse(
            turma -> Console.println("\n" + turma),
            () -> Console.println("Turma não encontrada.\n")
        );
    }

    @Override
    public void list() {
        List<TurmaEntity> turmas = repository.findAll();

        if (turmas.isEmpty()) {
            Console.println("Nenhuma turma cadastrada.\n");
            return;
        }

        turmas.forEach(Console::println);
    }
}
