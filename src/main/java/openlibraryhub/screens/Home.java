package openlibraryhub.screens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import openlibraryhub.Console;
import openlibraryhub.Util;
import openlibraryhub.interfaces.Screen;

@Component
public class Home implements Screen {
    @Autowired
    private Livros livros;

    @Autowired
    private Turmas turmas;

    @Autowired
    private Alunos alunos;

    @Autowired
    private Emprestimos emprestimos;

    @Autowired
    private Outros outros;

    @Override
    public void display() {
        boolean running = true;
        while (running) {
            Console.println("Bem-vindo ao OpenLibraryHub!\n");
            Console.println(Util.greet() + '\n');
            Console.println("O que deseja fazer?");
            Console.println("1 - Livros");
            Console.println("2 - Turmas");
            Console.println("3 - Alunos");
            Console.println("4 - Empréstimos");
            Console.println("5 - Outros");
            Console.println("6 - Sair");
            Console.print("--> ");

            running = handleOption();
        }
    }

    @Override
    public boolean handleOption() {
        int option = Console.readInt();
        Console.clear();

        switch (option) {
            // 1 - Livros
            case 1:
                livros.display();
                break;
            // 2 - Turmas
            case 2:
                turmas.display();
                break;
            // 3 - Alunos
            case 3:
                alunos.display();
                break;
            // 4 - Empréstimos
            case 4:
                emprestimos.display();
                break;
            // 5 - Outros
            case 5:
                outros.display();
                break;
            // 6 - Sair
            case 6:
                Console.println("Até mais!");
                System.exit(0);
            default:
                Console.println("Opção inválida.\n");
                break;
        }

        return true;
    }
}
