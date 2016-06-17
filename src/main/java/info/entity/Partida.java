package info.entity;

import io.datafx.crud.table.ViewColumn;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Partida {
    @Id
    @ViewColumn("ID")
    private String identificador;

    @ViewColumn("Data/Hora")
    private java.sql.Date datahora;

    @ViewColumn("Local")
    private String nomelocal;

    @ViewColumn("Completada")
    private String completada;

    @ViewColumn("Observação")
    private String observacao;

    @ViewColumn("Tipo")
    private String tipo;

    @ViewColumn("Modalidade")
    private String modalidade;

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public java.sql.Date getDatahora() {
        return datahora;
    }

    public void setDatahora(java.sql.Date datahora) {
        this.datahora = datahora;
    }

    public String getNomelocal() {
        return nomelocal;
    }

    public void setNomelocal(String nomelocal) {
        this.nomelocal = nomelocal;
    }

    public String getCompletada() {
        return completada;
    }

    public void setCompletada(String completada) {
        this.completada = completada;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String Observacao) {
        this.observacao = Observacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }
}
