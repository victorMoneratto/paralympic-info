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
    private String partida;

    @Id
    @ViewColumn("Atleta")
    private String atleta;

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

    public String getAtleta() {
        return atleta;
    }

    public void setAtleta(String atleta) {
        this.atleta = atleta;
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
