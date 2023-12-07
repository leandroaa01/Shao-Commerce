package projeto.shao.commerce.shaocommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import projeto.shao.commerce.Enums.Perfil;

@Entity
public class Cliente {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @NotBlank(message = "Por favor, forneça um nome.")
	private String nome;

	
	@Email(message = "O e-mail deve ser válido")
	private String email;

	@Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    @NotBlank
	private String senha;

    private Perfil perfil;


    

    public Cliente(Long id, @NotBlank(message = "Por favor, forneça um nome.") String nome,
            @Email(message = "O e-mail deve ser válido") String email,
            @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres") @NotBlank String senha, Perfil perfil) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
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

   

    @Override
    public String toString() {
        return "Cliente [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", perfil=" + perfil
                + "]";
    }

    public Cliente() {
    }

}
