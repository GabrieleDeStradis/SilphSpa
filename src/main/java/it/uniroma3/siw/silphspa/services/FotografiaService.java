package it.uniroma3.siw.silphspa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.silphspa.model.Fotografia;
import it.uniroma3.siw.silphspa.repository.FotografiaRepository;

@Service
public class FotografiaService {

	@Autowired
	private FotografiaRepository fotografiaRepository;
	
	@Transactional
	public Fotografia cercaPerNome(String nome) {
		return this.fotografiaRepository.findByNome(nome);
	}
	
	@Transactional
	public Fotografia salva(Fotografia target) {
		return this.fotografiaRepository.save(target);
	}
	
}
