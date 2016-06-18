package info.entity;

import io.datafx.crud.table.ViewColumn;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Medalha {

    @Id
    @ViewColumn("ID")
    public String uuid;

    @ViewColumn("Time")
    public Integer time;

    @ViewColumn("Atleta")
    public Integer atleta;

    @ViewColumn("Medalha")
    public String Medalha;

    @Id
    @ViewColumn("Modalidade")
    public String Modalidade;

    @Id
    @ViewColumn("Ganhador")
    public String Nome;

    public Medalha() {
    }

    public String getUuid() {
        return uuid;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getAtleta() {
        return atleta;
    }

    public void setAtleta(Integer atleta) {
        this.atleta = atleta;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getMedalha() {
        return Medalha;
    }

    public void setMedalha(String medalha) {
        Medalha = medalha;
    }

    public String getModalidade() {
        return Modalidade;
    }

    public void setModalidade(String modalidade) {
        Modalidade = modalidade;
    }
}
