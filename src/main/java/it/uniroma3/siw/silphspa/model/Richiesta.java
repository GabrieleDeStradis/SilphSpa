package it.uniroma3.siw.silphspa.model;

import java.util.*;
import javax.persistence.*;

@Entity
public class Richiesta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String descrizione;
	@ManyToMany
	private List<Fotografia> foto;
	@ManyToOne
	private Utente utente;
	
	private Richiesta() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Fotografia> getFoto() {
		return foto;
	}

	public void setFoto(List<Fotografia> foto) {
		this.foto = foto;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	@Override
	public String toString() {
		StringBuilder descrizione = new StringBuilder("L'utente "+this.utente.getNome()+" "+this.utente.getCognome()+
				", ha richiesto l'accesso alle seguenti foto:\n");
		
		for(Fotografia f : foto) {
			descrizione.append(f.getNome());
		}
		
		return descrizione.toString();
	}
	
	
	
	

}
