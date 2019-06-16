package it.uniroma3.siw.silphspa.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import it.uniroma3.siw.silphspa.services.FotografiaService;

@Controller
public class FotografiaController {

	@Autowired
	private FotografiaService fotoService;

	/* path della directory per la gestione della galleria di immagini */
	private String download_path = System.getProperty("user.dir")+"/src/main/resources/static/downloads_silph/";
	
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
			if (downloadMethod(savedFoto))
				return "fotografia";
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
	 * @return true se la creazione e' andata a buon fine, false altrimenti
	 */
	private boolean downloadMethod(Fotografia foto) {
		File file = new File(download_path+foto.getId().toString()+"_"+foto.getNome()+".jpg");
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(foto.getImmagine());
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
		/* riempio la directory creando i relativi file .jpg */
		for (Fotografia f : fotografie) {
			if (!downloadMethod(f)) {
				model.addAttribute("erroreIO", "non riesco a scaricare il file "+f.getId()+"_"+f.getNome());
				return "myErrorPage";
			}
		}
		return "gallery";
	}
}
