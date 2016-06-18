package info.entity;

import info.entity.key.Atleta_PartidaPK;
import io.datafx.crud.table.ViewColumn;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(Atleta_PartidaPK.class)
public class Atleta_Partida {

    @Id
    @ViewColumn("Partida")
    public Integer partida;

    @Id
    @ViewColumn("Atleta")
    public Integer atleta;

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

    public Integer getAtleta() {
        return atleta;
    }

    public void setAtleta(Integer atleta) {
        this.atleta = atleta;
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
