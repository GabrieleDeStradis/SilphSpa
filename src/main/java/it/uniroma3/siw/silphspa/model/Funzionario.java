package it.uniroma3.siw.silphspa.model;

import javax.persistence.*;

@Entity
@NamedQuery(name="findAllFunzionari", query="SELECT f FROM Funzionario f")
public class Funzionario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String cognome;
	
	public Funzionario(String nome, String cognome) {
		this.nome = nome;
		this.cognome = cognome;
	}
	
	public Funzionario() {
		
	}
	
	
	/* getters - setters */
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return this.cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	
	/* equals - hashCode - toString */
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funzionario other = (Funzionario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Funzionario [id = " + id + ", nome = " + nome + ", cognome = " + cognome + "]";
	}
	
}