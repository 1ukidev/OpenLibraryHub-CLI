package openlibraryhub.screens;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import openlibraryhub.Console;
import openlibraryhub.entities.LivroEntity;
import openlibraryhub.interfaces.CRUDScreen;
import openlibraryhub.repository.LivroRepository;

@Component
public class Livros implements CRUDScreen {
    @Autowired
    private LivroRepository repository;

    @Override
    public void display() {
        boolean running = true;
        while (running) {
            Console.println("1 - Cadastrar livro");
            Console.println("2 - Atualizar livro");
            Console.println("3 - Excluir livro");
            Console.println("4 - Buscar livro");
            Console.println("5 - Listar livros");
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
            // 1 - Cadastrar livro
            case 1:
                save();
                break;
            // 2 - Atualizar livro
            case 2:
                update();
                break;
            // 3 - Excluir livro
            case 3:
                delete();
                break;
            // 4 - Buscar livro
            case 4:
                search();
                break;
            // 5 - Listar livros
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
        Console.print("Digite o nome do livro: ");
        String titulo = Console.readString();

        Console.print("Digite o autor do livro: ");
        String autor = Console.readString();

        Console.print("Digite a seção: ");
        String secao = Console.readString();

        Console.print("Digite o número de páginas: ");
        int paginas = Console.readInt();

        Console.print("Digite o ano de publicação: ");
        int ano = Console.readInt();

        Console.print("Digite a quantida em estoque: ");
        int estoque = Console.readInt();

        repository.save(new LivroEntity(titulo, autor, secao,
                                        paginas, ano, estoque));

        Console.clear();
        Console.println("Livro cadastrado com sucesso!\n");
    }

    @Override
    public void update() {
        Console.print("Digite o id do livro a ser atualizado: ");
        long id = Console.readLong();

        Optional<LivroEntity> livroOptional = repository.findById(id);
        if (!livroOptional.isPresent()) {
            Console.println("Livro não encontrado.\n");
            return;
        }

        LivroEntity livro = livroOptional.get();

        Console.print("Digite o novo nome do livro: ");
        String titulo = Console.readString();

        Console.print("Digite o novo autor do livro: ");
        String autor = Console.readString();

        Console.print("Digite a nova seção: ");
        String secao = Console.readString();

        Console.print("Digite o novo número de páginas: ");
        int paginas = Console.readInt();

        Console.print("Digite o novo ano de publicação: ");
        int ano = Console.readInt();

        Console.print("Digite a nova quantida em estoque: ");
        int estoque = Console.readInt();

        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setSecao(secao);
        livro.setPaginas(paginas);
        livro.setAno(ano);
        livro.setEstoque(estoque);

        repository.save(livro);

        Console.clear();
        Console.println("Livro atualizado com sucesso!\n");
    }

    @Override
    public void delete() {
        Console.print("Digite o id do livro a ser excluído: ");
        long id = Console.readLong();

        Optional<LivroEntity> livroOptional = repository.findById(id);
        if (!livroOptional.isPresent()) {
            Console.println("Livro não encontrado.\n");
            return;
        }

        repository.delete(livroOptional.get());

        Console.clear();
        Console.println("Livro excluído com sucesso!\n");
    }

    @Override
    public void search() {
        Console.print("Digite o id do livro a ser buscado: ");
        long id = Console.readLong();

        repository.findById(id).ifPresentOrElse(
            livro -> Console.println("\n" + livro),
            () -> Console.println("Nenhum livro encontrado.\n")
        );
    }

    @Override
    public void list() {
        List<LivroEntity> livros = repository.findAll();

        if (livros.isEmpty()) {
            Console.println("Nenhum livro cadastrado.\n");
            return;
        }

        livros.forEach(Console::println);
    }
}
