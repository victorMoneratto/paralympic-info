package info.entity.key;

import java.io.Serializable;

public class Atleta_PartidaPK implements Serializable {
    private String partida;
    private String atleta;

    public Atleta_PartidaPK() {
    }

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
}
