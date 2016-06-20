package info.entity;

import io.datafx.crud.table.ViewColumn;

import javax.persistence.*;

@Table(name = "TimeOlimpico")
@Entity
public class Time {
    @Id
    @ViewColumn("ID")
    @GeneratedValue
    public Integer identificador;

    @ViewColumn("Nome")
    public String nome;

    @ViewColumn("Delegação")
    public String delegacao;

    @ViewColumn("Modalidade")
    public String modalidade;

    @ViewColumn("Categoria")
    public String categoria;

    @ViewColumn("Gênero")
    public String genero;

    @ViewColumn("Medalha")
    public String medalhaGanha;

    @ViewColumn("Comprometimento")
    @Column(name = "GrauDeComprometimento")
    private String Comprometimento;

    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDelegacao() {
        return delegacao;
    }

    public void setDelegacao(String delegacao) {
        this.delegacao = delegacao;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getMedalha() {
        return medalhaGanha;
    }

    public void setMedalha(String medalhaganha) {
        this.medalhaGanha = medalhaganha;
    }

    public String getComprometimento() {
        return Comprometimento;
    }

    public void setComprometimento(String graudecomprometimento) {
        this.Comprometimento = graudecomprometimento;
    }

    @Override
    public String toString() {
        return nome;
    }
}
