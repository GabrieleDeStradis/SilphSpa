package it.uniroma3.siw.silphspa;

import java.io.File;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SilphSpaApplication {

	public static void main(String[] args) {
		/* all'avvio viene creata la cartella dove verra' gestita la galleria di immagini*/
		/*System.getProperty("user.dir")+"/src/main/resources/static/*/
		try {
			String creation_path = (new File(".").getCanonicalPath())+"/src/main/resources/static/images/";
			new File(creation_path).mkdir();
			System.out.println("PATH DI CREAZIONE:\n"+creation_path);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		SpringApplication.run(SilphSpaApplication.class, args);
	}

}
