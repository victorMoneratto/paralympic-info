package info.entity;

import info.entity.key.Time_PartidaPK;
import io.datafx.crud.table.ViewColumn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(Time_PartidaPK.class)
public class Time_Partida {

    @Id
    @ViewColumn("Partida")
    private String partida;

    @Id
    @ViewColumn("Time")
    private String timeolimp;

    @ViewColumn("Classificação")
    private String classificacao;

    @ViewColumn("Pontuação")
    private Double pontuacao;

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getTime() {
        return timeolimp;
    }

    public void setTime(String timeolimp) {
        this.timeolimp = timeolimp;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public Double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }
}
