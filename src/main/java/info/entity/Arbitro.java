package info.entity;

import io.datafx.crud.table.ViewColumn;

import javax.persistence.*;

@Entity
public class Arbitro extends AbstractEntity {
    @Id
    @Column
    @ViewColumn("ID")
    private String identificador;

    @Column
    @ViewColumn("Nome")
    private String nome;

    @Column
    @ViewColumn("País")
    private String pais;

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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public String getFormattedPrimaryKey() {
        return identificador;
    }
}
