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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.silphspa.model.Album;
import it.uniroma3.siw.silphspa.model.Fotografia;
import it.uniroma3.siw.silphspa.services.AlbumService;
import it.uniroma3.siw.silphspa.services.FotografiaService;

@Controller
public class FotografiaController {

	@Autowired
	private FotografiaService fotografiaService;
	@Autowired
	private AlbumService albumService;

	/* path della directory per la gestione della galleria di immagini */
	/*System.getProperty("user.dir")+"/src/main/resources/static/*/
	protected static String download_path = 
			it.uniroma3.siw.silphspa.SilphSpaApplication.application_pathToStaticFolder+"/downloads_silph/";
	
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
			Fotografia savedFoto = this.fotografiaService.inserisci(foto);
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
		String file_name = foto.getId().toString()+"_"+foto.getNome();
		File file = new File(download_path+file_name);
		if (!file.exists()) { //se il file immagine non esiste lo creo
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
		else //altrimenti ritorno il path al file gia' esistente
			return ("/downloads_silph/"+file_name);
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
	public String visualizzaGalleriaFotografie(Model model) {
		/* recupero tutti gli oggetti Fotografia salvati nel db */
		List<Fotografia> fotografie = this.fotografiaService.tutte();/* riempio la directory creando i relativi file .jpg
		 * inoltre creo una lista con tutti i percorsi relativi ai files creati */
		List<String> files_galleria = new LinkedList<>();
		String temp_filepath = "";
		for (Fotografia f : fotografie) {
			temp_filepath = downloadMethod(f);
			if (temp_filepath==null) {
				model.addAttribute("erroreIO", "non riesco a scaricare il file "+f.getId()+"_"+f.getNome());
				return "myErrorPage";
			}
			files_galleria.add(temp_filepath);
		}
		model.addAttribute("files_galleria", files_galleria);
		return "gallery";
	}
	
	@RequestMapping(value="/fotografieDaAlbum/{id_album}",method=RequestMethod.GET)
	public String visualizzFotografieAlbum(@PathVariable("id_album") Long id_album, Model model) {
		Album album = this.albumService.cercaPerId(id_album);
		model.addAttribute("fotografie",this.fotografiaService.cercaPerAlbum(album));
		model.addAttribute("album",album);
		
		return "fotografieAlbum";
	}
	
}
