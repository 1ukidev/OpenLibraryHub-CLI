package openlibraryhub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import openlibraryhub.screens.Home;

@SpringBootApplication
public class App {
	public static void main(String[] args) {
		Console.println("Iniciando aplicação...");
		SpringApplication.run(App.class, args);
	}
}

@Component
class MyCommandLineRunner implements CommandLineRunner {
	@Autowired
	private Home home;

	@Override
    public void run(String... args) {
		Console.clear();
		home.display();
    }
}
