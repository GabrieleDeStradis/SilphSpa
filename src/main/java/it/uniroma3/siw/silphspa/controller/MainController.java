package it.uniroma3.siw.silphspa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.silphspa.model.SearchQuery;

@Controller
public class MainController {

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
	public String searchMethod() {
		//TODO 
		return null;
	}
}
