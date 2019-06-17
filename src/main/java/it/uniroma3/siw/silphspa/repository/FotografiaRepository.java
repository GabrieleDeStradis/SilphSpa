package it.uniroma3.siw.silphspa.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.silphspa.model.Fotografia;

public interface FotografiaRepository extends CrudRepository<Fotografia,Long> {

	public Fotografia findByNome(String nome);

}