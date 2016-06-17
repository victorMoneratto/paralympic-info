package info.entity.key;

import java.io.Serializable;

public class Time_PartidaPK implements Serializable{
    private String partida;
    private String timeolimp;

    public Time_PartidaPK() {
    }

    public String getPartida() {

        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getTimeolimp() {
        return timeolimp;
    }

    public void setTimeolimp(String timeolimp) {
        this.timeolimp = timeolimp;
    }
}
