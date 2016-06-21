package info.entity;

import io.datafx.crud.table.ViewColumn;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Medalha {

    @Id
    @ViewColumn("ID")
    @GeneratedValue
    public String uuid;

    @ViewColumn("Time")
    public Integer time;

    @ViewColumn("Atleta")
    public Integer atleta;

    @ViewColumn("Medalha")
    public String medalha;

    @ViewColumn("Modalidade")
    public String modalidade;

    @ViewColumn("Ganhador")
    public String nome;

    @ViewColumn("Tipo de Modalidade")
    public String tipo;

    public Medalha() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMedalha() {
        return medalha;
    }

    public void setMedalha(String medalha) {
        this.medalha = medalha;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isIndividual() {
        return tipo.equalsIgnoreCase("Individual");
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
