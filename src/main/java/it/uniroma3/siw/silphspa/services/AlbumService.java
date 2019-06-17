package it.uniroma3.siw.silphspa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.silphspa.model.Album;
import it.uniroma3.siw.silphspa.repository.AlbumRepository;

@Service
public class AlbumService {
	
	@Autowired
	private AlbumRepository albumRepository;
	
	@Transactional
	public Album cercaPerNome(String nome) {
		return this.albumRepository.findByNome(nome);
	}
	
	@Transactional
	public Album inserisci(Album target) {
		return this.albumRepository.save(target);
	}
	
	@Transactional
	public Album albumPerId(Long id) {
		return this.albumRepository.findById(id).get();
	}
	
	@Transactional
	public List<Album> tutti() {
		return (List<Album>)this.albumRepository.findAll();
	}

}
