package info.entity.key;

import java.io.Serializable;

/**
 * Created by Seduq on 20/06/2016.
 */
public class Participante_PartidaPK implements Serializable {
    private Integer partida;
    private Integer participante;

    public Participante_PartidaPK() {
    }

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
}
