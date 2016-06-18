package info.entity;

import io.datafx.crud.table.ViewColumn;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Atleta {
    @Id
    @ViewColumn("ID")
    public Integer identificador;

    @ViewColumn("Nome")
    public String nome;

    @ViewColumn("Delegação")
    public String delegacao;

    @ViewColumn("Data de Nascimento")
    public java.sql.Date dataNascimento;

    @ViewColumn("Altura")
    public Double altura;

    @ViewColumn("Peso")
    public Double peso;

    @ViewColumn("Gênero")
    public String genero;

    @ViewColumn("Foto")
    public String foto;

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

    public java.sql.Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(java.sql.Date datanascimento) {
        this.dataNascimento = datanascimento;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
