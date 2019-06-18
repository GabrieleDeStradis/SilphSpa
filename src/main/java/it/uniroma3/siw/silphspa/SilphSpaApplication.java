package it.uniroma3.siw.silphspa;

import java.io.File;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SilphSpaApplication {
	
	public static String application_pathToStaticFolder;

	public static void main(String[] args) {
		/* all'avvio viene creata la cartella dove verra' gestita la galleria di immagini*/
		/*System.getProperty("user.dir")+"/src/main/resources/static/*/
		try {
			application_pathToStaticFolder = new File(".").getCanonicalPath()+"/src/main/resources/static";
			/* creo la cartella application/src/main/resources/static/downloads_silph/ */
			new File(application_pathToStaticFolder+"/downloads_silph/").mkdir();
			System.out.println("PATH ALLA CARTELLA STATIC:\n"+application_pathToStaticFolder);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		SpringApplication.run(SilphSpaApplication.class, args);
	}

}
