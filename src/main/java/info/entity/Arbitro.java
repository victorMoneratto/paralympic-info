package info.entity;

import io.datafx.crud.table.ViewColumn;

import javax.persistence.*;

@Entity
public class Arbitro {
    @Id
    @Column
    @ViewColumn("ID")
    public Integer identificador;

    @Column
    @ViewColumn("Nome")
    public String nome;

    @Column
    @ViewColumn("País")
    public String pais;

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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return nome;
    }
}
