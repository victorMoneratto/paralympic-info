package info.entity.key;

import java.io.Serializable;

public class Time_PartidaPK implements Serializable{
    private Integer partida;
    private Integer timeolimp;

    public Time_PartidaPK() {
    }

    public Integer getPartida() {

        return partida;
    }

    public void setPartida(Integer partida) {
        this.partida = partida;
    }

    public Integer getTimeolimp() {
        return timeolimp;
    }

    public void setTimeolimp(Integer timeolimp) {
        this.timeolimp = timeolimp;
    }
}
