package it.uniroma3.siw.silphspa;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SilphSpaApplication {

	public static void main(String[] args) {
		/* all'avvio viene creata la cartella dove verra' gestita la galleria di immagini*/
		/*System.getProperty("user.dir")+"/src/main/resources/static/*/
		new File("/downloads_silph/").mkdir();
		SpringApplication.run(SilphSpaApplication.class, args);
	}

}
