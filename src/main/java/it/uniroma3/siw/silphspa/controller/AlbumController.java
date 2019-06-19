package it.uniroma3.siw.silphspa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.silphspa.model.Album;
import it.uniroma3.siw.silphspa.services.AlbumService;
import it.uniroma3.siw.silphspa.services.FotografiaService;
import it.uniroma3.siw.silphspa.services.FotografoService;

@Controller
public class AlbumController {
	
	@Autowired
	private AlbumService albumService;
	@Autowired
	private FotografiaService fotografiaService;
	@Autowired
	private FotografiaController fotografiaController;
	@Autowired
	private FotografoService fotografoService;

	@RequestMapping(value = "/album/{id}", method = RequestMethod.GET)
	public String getAlbum(@PathVariable("id") Long id, Model model) {
		if (id != null) {
			Album album = this.albumService.cercaPerId(id);
			List<String> fotografie_paths = fotografiaController.getPaths(this.fotografiaService.cercaPerAlbum(album));
			model.addAttribute("fotografie_paths",fotografie_paths);
			model.addAttribute("album", album);
			return "album";
		} else
			return getAlbums(model);
	}
	
	@RequestMapping(value="/albumsPerFotografo/{id_fotografo}",method=RequestMethod.GET)
	public String getAlbumPerFotografo(@PathVariable("id_fotografo")Long id_fotografo, Model model) {
		if (id_fotografo!=null) {
			model.addAttribute("albums",this.albumService.cercaPerFotografo(this.fotografoService.cercaPerId(id_fotografo)));
			return "albums";
		}
		else
			return getAlbums(model);
	}
	
	@RequestMapping(value = "/albums", method = RequestMethod.GET)
	public String getAlbums(Model model) {
		model.addAttribute("albums", this.albumService.tutti());
		return "albums";
	}

}
