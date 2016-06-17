package info.entity;

import io.datafx.crud.table.ViewColumn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "TimeOlimpico")
@Entity
public class Time {
    @Id
    @ViewColumn("ID")
    private String identificador;

    @ViewColumn("Nome")
    private String nome;

    @ViewColumn("Delegação")
    private String delegacao;

    @ViewColumn("Modalidade")
    private String modalidade;

    @ViewColumn("Categoria")
    private String categoria;

    @ViewColumn("Gênero")
    private String genero;

    @ViewColumn("Medalha")
    private String medalhaGanha;

    @ViewColumn("Comprometimento")
    @Column(name = "GrauDeComprometimento")
    private String Comprometimento;

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
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
}
