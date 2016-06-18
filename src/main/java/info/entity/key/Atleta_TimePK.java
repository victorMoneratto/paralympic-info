package info.entity.key;

import java.io.Serializable;

public class Atleta_TimePK implements Serializable{
    private Integer atleta;
    private Integer timeolimp;

    public Atleta_TimePK() {
    }

    public Integer getAtleta() {
        return atleta;
    }

    public void setAtleta(Integer atleta) {
        this.atleta = atleta;
    }

    public Integer getTimeolimp() {
        return timeolimp;
    }

    public void setTimeolimp(Integer timeolimp) {
        this.timeolimp = timeolimp;
    }
}
