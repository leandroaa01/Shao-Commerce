package projeto.shao.commerce.shaocommerce.models;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Por favor, forneça um Nome.")
    private String nome;

    @NotBlank(message = "Por favor, forneça uma Descrição.")
    private String descricao;

    @NotNull(message = "Por favor, forneça um Preço.")
    private double preco;

    @NotBlank(message = "Por favor, selecione uma Categoria.")
    private String categoria;

    @NotNull(message = "Por favor, forneça uma Data.")
    @FutureOrPresent(message = "A Data de Venda deve ser no presente ou no futuro.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVenda;
    
    @NotNull(message = "Por favor, forneça um Horário.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horario;

    private String nomeImg;

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    

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

    public String caminhoImg() {
        return getNomeImg();
    }

    @Override
    public String toString() {
        return "Produto [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", preco=" + preco + ", categoria="
                + categoria + ", dataVenda=" + dataVenda + ", horario=" + horario + ", nomeImg=" + nomeImg
                + ", comerciante=" + comerciante + "]";
    }

    public Produto() {
    }
    

}
