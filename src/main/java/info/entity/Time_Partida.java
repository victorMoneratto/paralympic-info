package info.entity;

import info.entity.key.Time_PartidaPK;
import io.datafx.crud.table.ViewColumn;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(Time_PartidaPK.class)
public class Time_Partida {

    @Id
    @ViewColumn("Partida")
    public Integer partida;

    @Id
    @ViewColumn("Time")
    public Integer timeolimp;

    @ViewColumn("Classificação")
    public Integer classificacao;

    @ViewColumn("Pontuação")
    public Double pontuacao;

    public Integer getPartida() {
        return partida;
    }

    public void setPartida(Integer partida) {
        this.partida = partida;
    }

    public Integer getTime() {
        return timeolimp;
    }

    public void setTime(Integer timeolimp) {
        this.timeolimp = timeolimp;
    }

    public Integer getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Integer classificacao) {
        this.classificacao = classificacao;
    }

    public Double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }
}
