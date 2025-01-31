package openlibraryhub.screens;

import org.springframework.stereotype.Component;

import openlibraryhub.Console;
import openlibraryhub.interfaces.Screen;

@Component
public class Outros implements Screen {
    @Override
    public void display() {
        boolean running = true;
        while (running) {
            Console.println("1 - Sobre");
            Console.println("2 - Sair");
            Console.print("--> ");

            running = handleOption();
        }
    }

    @Override
    public boolean handleOption() {
        int option = Console.readInt();
        Console.clear();

        switch (option) {
            // 1 - Sobre
            case 1:
                about();
                break;
            // 2 - Sair
            case 2:
                return false;
            default:
                Console.println("Opção inválida.\n");
                break;
        }

        return true;
    }

    private void about() {
        Console.println("OpenLibraryHub é um sistema de gerenciamento de bibliotecas.");
        Console.println("Desenvolvido por: 1ukidev");
        Console.println("GitHub: https://github.com/1ukidev/OpenLibraryHub-CLI");
        Console.println("Licença: GPL-3.0\n");
    }
}
