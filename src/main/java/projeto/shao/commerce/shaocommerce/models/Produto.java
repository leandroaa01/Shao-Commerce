package projeto.shao.commerce.shaocommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private double preco;
    private String categoria;
    private String horarioVenda;
    private String nomeImg;

    @ManyToOne
    private Comerciante comerciante;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public String getHorarioVenda() {
        return horarioVenda;
    }
    public void setHorarioVenda(String horarioVenda) {
        this.horarioVenda = horarioVenda;
    }
    public String getNomeImg() {
        return nomeImg;
    }
    public void setNomeImg(String nomeImg) {
        this.nomeImg = nomeImg;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Comerciante getComerciante() {
        return comerciante;
    }
    public void setComerciante(Comerciante comerciante) {
        this.comerciante = comerciante;
    }
    @Override
    public String toString() {
        return "Produto [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", preco=" + preco + ", categoria="
                + categoria + ", horarioVenda=" + horarioVenda + ", nomeImg=" + nomeImg + ", comerciante=" + comerciante
                + "]";
    }
    public String caminhoImg(){
		return getNomeImg();
	}
    

}
