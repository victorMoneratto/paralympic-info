package info.entity;

import io.datafx.crud.table.ViewColumn;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Modalidade {

    @Id
    @ViewColumn("Nome")
    public String nome;

    @ViewColumn("Gênero")
    public String genero;

    @ViewColumn("Unidade de Pontuação")
    public String unidadepontuacao;

    @ViewColumn("Descrição")
    public String descricao;

    @ViewColumn("Tipo")
    public String tipo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getUnidadepontuacao() {
        return unidadepontuacao;
    }

    public void setUnidadepontuacao(String unidadepontuacao) {
        this.unidadepontuacao = unidadepontuacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return nome;
    }
}
