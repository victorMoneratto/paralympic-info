package info.entity;

import io.datafx.crud.table.ViewColumn;

import javax.persistence.*;

@Entity
public class Pais extends AbstractEntity {

    @Id
    @Column
    @ViewColumn("Nome")
    private String nome;

    @Column
    @ViewColumn("Abreviação")
    private String abreviacao;

    @Column
    @ViewColumn("Bandeira")
    private String bandeira;

//    @JoinColumn(name = "Delegacao")
//    @ViewColumn("Delegação")
//    @ManyToOne
//    private Delegacao delegacao;

    @Column
    @ViewColumn("Delegação")
    private String delegacao;

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
    public String getFormattedPrimaryKey() {
        return nome;
    }
}
