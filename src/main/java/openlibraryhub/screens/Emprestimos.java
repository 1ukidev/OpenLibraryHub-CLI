package openlibraryhub.screens;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import openlibraryhub.Console;
import openlibraryhub.entities.AlunoEntity;
import openlibraryhub.entities.EmprestimoEntity;
import openlibraryhub.entities.LivroEntity;
import openlibraryhub.interfaces.CRUDScreen;
import openlibraryhub.repository.AlunoRepository;
import openlibraryhub.repository.EmprestimoRepository;
import openlibraryhub.repository.LivroRepository;

@Component
public class Emprestimos implements CRUDScreen {
    @Autowired
    private EmprestimoRepository repository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Override
    public void display() {
        boolean running = true;
        while (running) {
            Console.println("1 - Cadastrar empréstimo");
            Console.println("2 - Atualizar empréstimo");
            Console.println("3 - Excluir empréstimo");
            Console.println("4 - Buscar empréstimo");
            Console.println("5 - Listar empréstimos");
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
            // 1 - Cadastrar empréstimo
            case 1:
                save();
                break;
            // 2 - Atualizar empréstimo
            case 2:
                update();
                break;
            // 3 - Excluir empréstimo
            case 3:
                delete();
                break;
            // 4 - Buscar empréstimo
            case 4:
                search();
                break;
            // 5 - Listar empréstimos
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
        Console.print("Digite o id do livro: ");
        long livroId = Console.readLong();

        Optional<LivroEntity> livro = livroRepository.findById(livroId);
        if (livro.isEmpty()) {
            Console.println("Livro não encontrado.\n");
            return;
        }

        Console.print("Digite o id do aluno: ");
        long alunoId = Console.readLong();

        Optional<AlunoEntity> aluno = alunoRepository.findById(alunoId);
        if (aluno.isEmpty()) {
            Console.println("Aluno não encontrado.\n");
            return;
        }

        Console.print("Digite a data de empréstimo: ");
        LocalDate dataEmprestimo = Console.readLocalDate();

        Console.print("Digite a data de devolução: ");
        LocalDate dataDevolucao = Console.readLocalDate();

        EmprestimoEntity emprestimo = new EmprestimoEntity(aluno.get(), livro.get(),
                                                           dataEmprestimo, dataDevolucao);

        repository.save(emprestimo);

        Console.clear();
        Console.println("Empréstimo cadastrado com sucesso!\n");
    }

    @Override
    public void update() {
        Console.print("Digite o id do empréstimo a ser atualizado: ");
        long id = Console.readLong();

        Optional<EmprestimoEntity> emprestimoOptional = repository.findById(id);
        if (emprestimoOptional.isEmpty()) {
            Console.println("Empréstimo não encontrado.\n");
            return;
        }

        EmprestimoEntity emprestimo = emprestimoOptional.get();

        Console.print("Digite o id do livro: ");
        long livroId = Console.readLong();

        Optional<LivroEntity> livro = livroRepository.findById(livroId);
        if (livro.isEmpty()) {
            Console.println("Livro não encontrado.\n");
            return;
        }

        Console.print("Digite o id do aluno: ");
        long alunoId = Console.readLong();

        Optional<AlunoEntity> aluno = alunoRepository.findById(alunoId);
        if (aluno.isEmpty()) {
            Console.println("Aluno não encontrado.\n");
            return;
        }

        Console.print("Digite a nova data de empréstimo: ");
        LocalDate dataEmprestimo = Console.readLocalDate();

        Console.print("Digite a nova data de devolução: ");
        LocalDate dataDevolucao = Console.readLocalDate();

        emprestimo.setAluno(aluno.get());
        emprestimo.setLivro(livro.get());
        emprestimo.setDataEmprestimo(dataEmprestimo);
        emprestimo.setDataDevolucao(dataDevolucao);

        repository.save(emprestimo);

        Console.clear();
        Console.println("Empréstimo atualizado com sucesso!\n");
    }

    @Override
    public void delete() {
        Console.print("Digite o id do empréstimo a ser excluído: ");
        long id = Console.readLong();

        Optional<EmprestimoEntity> emprestimoOptional = repository.findById(id);
        if (emprestimoOptional.isEmpty()) {
            Console.println("Empréstimo não encontrado.\n");
            return;
        }

        repository.delete(emprestimoOptional.get());

        Console.clear();
        Console.println("Empréstimo excluído com sucesso!\n");
    }

    @Override
    public void search() {
        Console.print("Digite o id do empréstimo a ser buscado: ");
        long id = Console.readLong();

        repository.findById(id).ifPresentOrElse(
            emprestimo -> Console.println("\n" + emprestimo),
            () -> Console.println("Nenhum empréstimo encontrado.\n")
        );
    }

    @Override
    public void list() {
        List<EmprestimoEntity> emprestimos = repository.findAll();

        if (emprestimos.isEmpty()) {
            Console.println("Nenhum empréstimo cadastrado.\n");
            return;
        }

        emprestimos.forEach(Console::println);
    }
}
