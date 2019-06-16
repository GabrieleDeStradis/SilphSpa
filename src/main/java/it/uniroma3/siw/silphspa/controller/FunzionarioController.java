package it.uniroma3.siw.silphspa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FunzionarioController {
	
	@RequestMapping("/indiceFotografo")
	public String fotografo() {
		return "fotografoIndex.html";
		
	}

}
