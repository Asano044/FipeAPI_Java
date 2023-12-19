package br.com.casasbahia.consultador;

import br.com.casasbahia.consultador.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsultadorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ConsultadorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
