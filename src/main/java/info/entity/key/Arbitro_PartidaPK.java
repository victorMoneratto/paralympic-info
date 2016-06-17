package info.entity.key;

import java.io.Serializable;

public class Arbitro_PartidaPK implements Serializable{
    private String partida;
    private String arbitro;

    public Arbitro_PartidaPK() {
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getArbitro() {
        return arbitro;
    }

    public void setArbitro(String arbitro) {
        this.arbitro = arbitro;
    }
}
