package it.uniroma3.siw.silphspa.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.silphspa.model.Fotografo;
import it.uniroma3.siw.silphspa.repository.FotografoRepository;

@Service
public class FotografoService {
	
	@Autowired
	FotografoRepository fotografoRepository;
	
	@Transactional
	public Fotografo inserisci(Fotografo fotografo) {
		return this.fotografoRepository.save(fotografo);
	}
	
	@Transactional
	public List<Fotografo> tutti() {
		return (List<Fotografo>)this.fotografoRepository.findAll();
	}
	
	@Transactional
	public Fotografo fotografoPerId(Long id) {
		return this.fotografoRepository.findById(id).get();
	}

}
