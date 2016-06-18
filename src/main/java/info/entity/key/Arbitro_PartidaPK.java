package info.entity.key;

import java.io.Serializable;

public class Arbitro_PartidaPK implements Serializable{
    private Integer partida;
    private Integer arbitro;

    public Arbitro_PartidaPK() {
    }

    public Integer getPartida() {
        return partida;
    }

    public void setPartida(Integer partida) {
        this.partida = partida;
    }

    public Integer getArbitro() {
        return arbitro;
    }

    public void setArbitro(Integer arbitro) {
        this.arbitro = arbitro;
    }
}
