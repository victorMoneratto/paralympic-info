package info.entity.key;

import java.io.Serializable;

public class Atleta_Time_InfoPK implements Serializable{
    private Integer atleta;
    private Integer timeolimp;

    public Atleta_Time_InfoPK() {
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
