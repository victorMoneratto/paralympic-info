package info.entity;

import io.datafx.crud.table.ViewColumn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Delegacao {
    @Id
    @Column
    @ViewColumn("Nome")
    public String nome;

    public Delegacao() {
    }

    public Delegacao(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
