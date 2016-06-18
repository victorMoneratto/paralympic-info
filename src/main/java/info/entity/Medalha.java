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
    public String medalha;

    @Id
    @ViewColumn("Modalidade")
    public String modalidade;

    @Id
    @ViewColumn("Ganhador")
    public String nome;

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
        return nome;
    }

    public void setNome(String nome) { this.nome = nome; }

    public String getMedalha() { return medalha; }

    public void setMedalha(String medalha) { this.medalha = medalha; }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }
}
