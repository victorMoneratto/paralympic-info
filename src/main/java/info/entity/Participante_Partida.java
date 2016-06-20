package info.entity;

import info.entity.key.Atleta_PartidaPK;
import io.datafx.crud.table.ViewColumn;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * Created by Seduq on 20/06/2016.
 */

@Entity
@IdClass(Atleta_PartidaPK.class)
public class Participante_Partida {
    @Id
    @ViewColumn("Partida")
    public Integer partida;

    @Id
    @ViewColumn("Participante")
    public Integer participante;

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

    public Integer getParticipante() {
        return participante;
    }

    public void setParticipante(Integer participante) {
        this.participante = participante;
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
