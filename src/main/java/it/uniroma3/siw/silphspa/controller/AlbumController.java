package it.uniroma3.siw.silphspa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import it.uniroma3.siw.silphspa.services.AlbumService;

@Controller
public class AlbumController {
	
	@Autowired
	private AlbumService albumService;

	@RequestMapping(value = "/album/{id}", method = RequestMethod.GET)
	public String getAlbum(@PathVariable("id") Long id, Model model) {

		if (id != null) {

			model.addAttribute("album", this.albumService.albumPerId(id));
			return "album.html";

		} else {
			return getAlbums(model);
		}
	}
	
	@RequestMapping(value = "/albums", method = RequestMethod.GET)
	public String getAlbums(Model model) {
		model.addAttribute("albums", this.albumService.tutti());
		return "albums";
	}

}
