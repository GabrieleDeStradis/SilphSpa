package it.uniroma3.siw.silphspa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import it.uniroma3.siw.silphspa.services.FotografiaService;
import it.uniroma3.siw.silphspa.services.ShoppingCartService;

@Controller
public class ShoppingCartController {

	private final ShoppingCartService shoppingCartService;

	private final FotografiaService fotografiaService;

	@Autowired
	public ShoppingCartController(ShoppingCartService shoppingCartService, FotografiaService fotografiaService) {
		this.shoppingCartService = shoppingCartService;
		this.fotografiaService = fotografiaService;
	}

	@GetMapping("/shoppingCart")
	public ModelAndView shoppingCart() {
		ModelAndView modelAndView = new ModelAndView("/shoppingCart");
		modelAndView.addObject("fotografie", shoppingCartService.getFotografieNelCarrello());
		return modelAndView;
	}

	@GetMapping("/shoppingCart/addFotografia/{fotografiaId}")
	public ModelAndView aggiungiFotografiaAlCarrello(@PathVariable("fotoId") Long fotoId) {
		if (this.shoppingCartService.getFotografieNelCarrello().contains(this.fotografiaService.
				cercaPerId(fotoId))) {
			this.shoppingCartService.aggiungiFotografia(this.fotografiaService.cercaPerId(fotoId));
		}
			return shoppingCart();
	}

	@GetMapping("/shoppingCart/removeProduct/{productId}")
	public ModelAndView reimuoviFotografiaDalCarrello(@PathVariable("fotografiaId") Long fotoId) {
		if (this.shoppingCartService.getFotografieNelCarrello().contains(this.fotografiaService.
				cercaPerId(fotoId))) {
			this.shoppingCartService.rimuoviFotografia(this.fotografiaService.cercaPerId(fotoId));
		}
			return shoppingCart();
	}

	@GetMapping("/shoppingCart/checkout")
	public ModelAndView checkout() {

		return shoppingCart();
	}

}