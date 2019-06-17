package it.uniroma3.siw.silphspa.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.silphspa.model.Funzionario;

/*
 * è un componente della nostra applicazione
 */
@Component
public class DBPopulation implements ApplicationRunner{

	@Autowired
	private FunzionarioRepository funzionarioRepository;

	public void run(ApplicationArguments args) throws Exception {
		this.addAll();
	}
	
	
	private void addAll() throws InterruptedException {
		
		Funzionario funzionario1;
		Funzionario funzionario2;
		
		funzionario1 = funzionarioSet("Valerio", "Rossi", "valerossi@gmail.com","1234");
		funzionario2 = funzionarioSet("Gabriele", "Bianchi", "gabribianchi@gmail.com","4321");
		
		/*salvo i funzionari nel DB*/
		this.funzionarioRepository.save(funzionario1);
		this.funzionarioRepository.save(funzionario2);
		
		/*seguiranno salvataggi di foto,fotografi e album per testare la piattaforma*/
		
		
	}
	
	
	private Funzionario funzionarioSet(String nome, String cognome, String email, String password) {
		Funzionario f = new Funzionario();
		f.setNome(nome);
		f.setCognome(cognome);
		f.setEmail(email);
		f.setPassword(password);
		return f;
	}
	
}
