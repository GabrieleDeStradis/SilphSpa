package it.uniroma3.siw.silphspa.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.silphspa.model.Fotografia;
import it.uniroma3.siw.silphspa.model.SearchQuery;
import it.uniroma3.siw.silphspa.services.AlbumService;
import it.uniroma3.siw.silphspa.services.FotografiaService;
import it.uniroma3.siw.silphspa.services.FotografoService;
import it.uniroma3.siw.silphspa.services.SearchQueryValidator;

@Controller
public class MainController {

	@Autowired
	private SearchQueryValidator searchQueryValidator;
	@Autowired
	private FotografiaService fotografiaService;
	@Autowired
	private FotografoService fotografoService;
	@Autowired
	private AlbumService albumService;

	@RequestMapping(value="/about",method=RequestMethod.GET)
	public String mostraInformazioni() {
		return "aboutPage";
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String homePage(Model model) {		
		model.addAttribute("search_query", new SearchQuery());
		return "index";
	}
	
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public String searchMethod(@Valid @ModelAttribute("search_query") SearchQuery searchQuery,
			Model model, BindingResult bindingResult) {
		String nextPage = "index";
		this.searchQueryValidator.validate(searchQuery,bindingResult);
		if (!bindingResult.hasErrors()) {
			/* eseguo un controllo sul tipo di ricerca */
			if (searchQuery.getType().equals("Fotografia")) { //ricerca per Fotografia
				Fotografia fotografia_trovata = this.fotografiaService.cercaPerNome(searchQuery.getQuery());
				model.addAttribute("fotografia", fotografia_trovata);
				model.addAttribute("fotoPath", FotografiaController.downloadMethod(fotografia_trovata));
				nextPage = "fotografia";
			}
			else if (searchQuery.getType().equals("Album")) { //ricerca per Album
				//TODO
				model.addAttribute("album", this.albumService.cercaPerNome(searchQuery.getQuery()));
				nextPage = "album";
			}
			else { //ricerca per Fotografo
				//TODO
				model.addAttribute("fotografo",this.fotografoService.cercaPerNome(searchQuery.getQuery()));
				nextPage = "fotografo";
			}
		}
		return nextPage;
	}
}
