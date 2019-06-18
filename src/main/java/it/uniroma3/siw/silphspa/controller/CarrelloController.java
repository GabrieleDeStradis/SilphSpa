package it.uniroma3.siw.silphspa.controller;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CarrelloController {
	
	/* TODO QUESTA CLASSE DEVE ANCORA ESSERE COMPLETATA */

	/**
	 * Questo metodo gestisce il path della fotografia desiderata 
	 * - il carrello dovra' memorizzare le informazioni necessarie per poter generare la richiesta 
	 * - ATTENZIONE questo metodo e' incompleto, ma non si puo' fare a meno del codice gia' scritto
	 * @param fotoPath - il percorso della fotografia desiderata
	 * @param files_galleria - una lista con tutti i percorsi delle fotografie visualizzate nella galleria
	 * @param model
	 * @return ancora la vista per la galleria
	 */
	@RequestMapping(value="/aggiungiAlCarrello",method=RequestMethod.GET)
	public String aggiungiFotoAlCarrello(@RequestParam("fotoPath") String fotoPath, Model model) {
		System.out.println("Il fotoPath e' -> "+fotoPath);
		Long fotoId = extractIdFromPath(fotoPath);
		System.out.println("L'id estratto e' -> "+fotoId.toString());
		
		//TODO aggiungere la logica per il carrello
		//     (consiglio di memorizzare sia l'id che il path della foto per visualizzarla facilmente nella richiesta)
		
		/* ricostruisco la galleria e torno alla relativa pagina html
		 * l'annotazione @PathVariable per la lista dei file paths non riesce a lavorare con le liste */
		String[] downloaded_files = new File(FotografiaController.download_path).list();
		List<String> files_galleria = new LinkedList<>();
		for (String s : downloaded_files ) {
			files_galleria.add("/downloads_silph/"+s);
		}
		model.addAttribute("files_galleria", files_galleria);
		return "gallery";
	}

	/**
	 * Questo metodo estrae l'id della fotografia dal percorso del relativo file
	 * @param path - il percorso del file
	 * @return (Long) id
	 */
	private Long extractIdFromPath(String path) {
		/*char[] name_file = path.substring(18).toCharArray();
		System.out.println("name_file = "+name_file);
		char[] final_id = "";
		int i = 0;
		for (char c : name_file) {
			if (!(c>='0'&&c<='9'))
				break;
			else {
				final_id.;
			}
		}
		System.out.println("final_id is "+final_id);
		return Long.parseLong(final_id);*/
		return 0L;
	}
	
	@RequestMapping(value="/mostraCarrello",method=RequestMethod.GET)
	public String mostraCarrello() {
		//TODO ancora da implementare in base a come sara' modellato il carrello
		return "myErrorPage";
	}
}
