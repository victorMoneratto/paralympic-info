package info.entity;

import io.datafx.crud.table.ViewColumn;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Partida {
    @Id
    @ViewColumn("ID")
    @GeneratedValue
    public Integer identificador;

    @ViewColumn("Data/Hora")
    public Timestamp datahora;

    @ViewColumn("Local")
    public String nomelocal;

    @ViewColumn("Completada")
    public Boolean completada;

    @ViewColumn("Observação")
    public String observacao;

    @ViewColumn("Modalidade")
    public String modalidade;

    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }

    public Timestamp getDatahora() {
        return datahora;
    }

    public void setDatahora(Timestamp datahora) {
        this.datahora = datahora;
    }

    public String getNomelocal() {
        return nomelocal;
    }

    public void setNomelocal(String nomelocal) {
        this.nomelocal = nomelocal;
    }

    public Boolean getCompletada() {
        return completada;
    }

    public void setCompletada(Boolean completada) {
        this.completada = completada;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String Observacao) {
        this.observacao = Observacao;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }
}
