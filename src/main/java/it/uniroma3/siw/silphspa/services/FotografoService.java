package it.uniroma3.siw.silphspa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.silphspa.model.Fotografo;
import it.uniroma3.siw.silphspa.repository.FotografoRepository;

@Service
public class FotografoService {
	
	@Autowired
	private FotografoRepository fotografoRepository;
	
	@Transactional
	public Fotografo cercaPerNome(String nome) {
		return this.fotografoRepository.findByNome(nome);
	}

	@Transactional
	public Fotografo salva(Fotografo target) {
		return this.fotografoRepository.save(target);
	}

}
