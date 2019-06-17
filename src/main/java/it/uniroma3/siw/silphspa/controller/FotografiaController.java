package it.uniroma3.siw.silphspa.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.silphspa.model.Fotografia;
import it.uniroma3.siw.silphspa.model.Fotografo;
import it.uniroma3.siw.silphspa.services.FotografiaService;

@Controller
public class FotografiaController {

	@Autowired
	private FotografiaService fotoService;

	/* path della directory per la gestione della galleria di immagini */
	private static String download_path = System.getProperty("user.dir")+"/src/main/resources/static/downloads_silph/";
	
	/**
	 * Questo metodo gestisce il caricamento sul database di un oggetto fotografia 
	 * @param file - questo file sara' scelto dall'utente durante l'uso della webapp
	 * @param foto - l'oggetto fotografia in salvataggio
	 * @param model
	 * @return la prossima vista
	 */
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String uploadMethod(@RequestParam("image_file")MultipartFile file,
			@ModelAttribute("fotografia")Fotografia foto, Model model) {
		try {
			foto.setImmagine(file.getBytes());
			Fotografia savedFoto = this.fotoService.inserisci(foto);
			model.addAttribute("foto", savedFoto);
			if (downloadMethod(savedFoto)!=null)
				return "fotografiaSalvata";
			else {
				model.addAttribute("erroreIO", "non sono riuscito a creare il file dall'array di byte");
				return "myErrorPage";
			}
		} catch (IOException e) {
			e.printStackTrace();
			model.addAttribute("erroreIO", "non sono riuscito a creare l'array di byte dal file");
			return "myErrorPage";
		}
	}

	/**
	 * Questo metodo prende un oggetto Fotografia e ne crea il relativo file .jpg nella directory destinata alla gallery
	 * @param foto - oggetto di tipo Fotografia
	 * @return la stringa al percorso da usare nelle pagine html, null altrimenti
	 */
	protected static String downloadMethod(Fotografia foto) {
		String file_name = foto.getId().toString()+"_"+foto.getNome()+".jpg";
		File file = new File(download_path+file_name);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(foto.getImmagine());
			fos.close();
			return "/downloads_silph/"+file_name;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Questo metodo resetta la directory che gestisce la gallery e tutto il suo contenuto (recursive)
	 * @param path - il percorso alla directory destinata
	 * @return true se la cancellazione e' andata a buon fine, false altrimenti
	 */
	private static boolean deleteDirectory(File path) {
		if (path.exists() ) {
			File[] files = path.listFiles();
			for( File f : files) {
				if (f.isDirectory())
					deleteDirectory(f);
				else
					f.delete();
			}
		}
		return path.delete();
	}
	
	@RequestMapping(value="/addFotografia", method=RequestMethod.GET)
	public String aggiungiFotografia(Model model) {
		model.addAttribute("fotografia", new Fotografia());
		return "fotografiaForm";
	}
	
	/**
	 * Questo metodo gestisce la visualizzazione della galleria con tutte le immagini salvate nel db
	 * @param model
	 * @return la prossima vista
	 */
	@RequestMapping(value="/gallery", method=RequestMethod.GET)
	public String visualizzaFotografie(Model model) {
		/* recupero tutti gli oggetti Fotografia salvati nel db */
		List<Fotografia> fotografie = this.fotoService.tutte();
		/* creo la directory per le immagini da visualizzare */
		File file = new File(download_path);
		if (!deleteDirectory(file)) { //cancello la cartella e tutto il suo contenuto se esistono
			model.addAttribute("erroreIO", "non riesco ad eliminare la cartella downloads_silph");
			return "myErrorPage";
		}
		if (!file.mkdir()) { //ricreo la cartella vuota
			model.addAttribute("erroreIO", "non riesco a creare la cartella downloads_silph");
			return "myErrorPage";
		}
		/* riempio la directory creando i relativi file .jpg
		 * inoltre creo una lista con tutti i percorsi relativi ai files creati */
		List<String> files = new LinkedList<>();
		String temp_filepath = null;
		for (Fotografia f : fotografie) {
			temp_filepath = downloadMethod(f);
			if (temp_filepath==null) {
				model.addAttribute("erroreIO", "non riesco a scaricare il file "+f.getId()+"_"+f.getNome());
				return "myErrorPage";
			}
			//files.add("/downloads_silph/"+f.getId().toString()+"_"+f.getNome()+".jpg");
			System.out.println(temp_filepath);
			files.add(temp_filepath);
		}
		model.addAttribute("files_scaricati", files);
		return "gallery";
	}
	
}
