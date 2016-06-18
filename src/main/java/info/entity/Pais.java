package info.entity;

import io.datafx.crud.table.ViewColumn;

import javax.persistence.*;

@Entity
public class Pais {

    @Id
    @Column
    @ViewColumn("Nome")
    public String nome;

    @Column
    @ViewColumn("Abreviação")
    public String abreviacao;

    @Column
    @ViewColumn("Bandeira")
    public String bandeira;

    @Column
    @ViewColumn("Delegação")
    public String delegacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getDelegacao() {
        return delegacao;
    }

    public void setDelegacao(String delegacao) {
        this.delegacao = delegacao;
    }

    @Override
    public String toString() {
        return nome;
    }
}
