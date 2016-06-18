package info.entity.key;

import java.io.Serializable;

public class Atleta_PartidaPK implements Serializable {
    private Integer partida;
    private Integer atleta;

    public Atleta_PartidaPK() {
    }

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
}
