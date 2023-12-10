package projeto.shao.commerce.shaocommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import projeto.shao.commerce.shaocommerce.Enums.Perfil;

@Entity
public class Comerciante {

	
	
	
	@Override
	public String toString() {
		return "Comerciante [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", numWhats="
				+ numWhats + ", nomeImg=" + nomeImg + ", perfil=" + perfil + "]";
	}

	

	public Comerciante(Long id, @NotBlank(message = "Por favor, forneça um nome.") String nome,
			@Email(message = "O e-mail deve ser válido") String email,
			@Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres") String senha,
			@Pattern(regexp = "^[0-9]+$", message = "O número do WhatsApp deve conter apenas dígitos") @Size(min = 11, max = 11, message = "O número do WhatsApp deve ter exatamente 11 dígitos") String numWhats,
			String nomeImg, Perfil perfil) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.numWhats = numWhats;
		this.nomeImg = nomeImg;
		this.perfil = perfil;
	}



	public Comerciante() {
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Por favor, forneça um nome.")
	private String nome;

	
	@Email(message = "O e-mail deve ser válido")
	private String email;

	

	@Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
	private String senha;

	
	@Pattern(regexp = "^[0-9]+$", message = "O número do WhatsApp deve conter apenas dígitos")
	@Size(min = 11, max = 11,  message = "O número do WhatsApp deve ter exatamente 11 dígitos")
	private String numWhats;

	private String nomeImg;

	 private Perfil perfil;

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

	public String caminhoImg(){
		return getNomeImg();
	}

	public String getNomeImg() {
		return nomeImg;
	}

	public void setNomeImg(String nomeImg) {
		this.nomeImg = nomeImg;
	}

	public String getNumWhats() {
		return numWhats;
	}

	public void setNumWhats(String numWhats) {
		this.numWhats = numWhats;
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
