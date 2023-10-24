package projeto.shao.commerce.shaocommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Comerciante {

	
	
	@Override
	public String toString() {
		return "Comerciante [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", nomeImagem="
				+ NomeImg +", numWhats="+numWhats  +"]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String nome;
	private String email;
	private String senha;
	private String numWhats;


	public String getNumWhats() {
		return numWhats;
	}

	public void setNumWhats(String numWhats) {
		this.numWhats = numWhats;
	}

	private String NomeImg;

	

	public String getNomeImagem() {
		return NomeImg;
	}

	public void setNomeImagem(String nomeImagem) {
		this.NomeImg = nomeImagem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
