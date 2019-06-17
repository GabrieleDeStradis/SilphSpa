package it.uniroma3.siw.silphspa.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.mock.*;
import org.springframework.mock.web.MockMultipartFile;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import java.lang.Object;

import it.uniroma3.siw.silphspa.model.Album;
import it.uniroma3.siw.silphspa.model.Fotografia;
import it.uniroma3.siw.silphspa.model.Fotografo;
import it.uniroma3.siw.silphspa.model.Funzionario;

/*
 * è un componente della nostra applicazione
 */
@Component
public class DBPopulation implements ApplicationRunner{

	@Autowired
	private AlbumRepository albumRepository;
	@Autowired
	private FotografoRepository fotografoRepository;
	@Autowired
	private FotografiaRepository fotografiaRepository;
	@Autowired
	private FunzionarioRepository funzionarioRepository;

	public void run(ApplicationArguments args) throws Exception {
		this.deleteAll();
		this.addAll();
	}
	
	private void deleteAll() {
		albumRepository.deleteAll();
		fotografoRepository.deleteAll();
		fotografiaRepository.deleteAll();
	}
	
	
	private void addAll() throws InterruptedException, IOException {
		
		Fotografia f1;
		File file1;
		
		Funzionario funzionario1;
		Funzionario funzionario2;
		
		funzionario1 = funzionarioSet("Valerio", "Rossi", "valerossi@gmail.com","1234");
		funzionario2 = funzionarioSet("Gabriele", "Bianchi", "gabribianchi@gmail.com","4321");
		
		/*salvo i funzionari nel DB*/
		this.funzionarioRepository.save(funzionario1);
		this.funzionarioRepository.save(funzionario2);
		
		/*seguiranno salvataggi di foto,fotografi e album per testare la piattaforma*/
		f1 = new Fotografia();
		
		file1 = new File("/Users/valerio/Downloads/alberi-bosco2.jpg");
	    FileInputStream input = new FileInputStream(file1);
	    MultipartFile multipartFile = new MockMultipartFile("file",
	            file1.getName(), "text/plain", IOUtils.toByteArray(input));
	    
	    f1.setImmagine(multipartFile.getBytes());
	    f1.setNome("una foto");
	    
	    this.fotografiaRepository.save(f1);
		
		
		
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
