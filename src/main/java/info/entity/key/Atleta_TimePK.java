package info.entity.key;

import java.io.Serializable;

public class Atleta_TimePK implements Serializable{
    private String atleta;
    private String timeolimp;

    public Atleta_TimePK() {
    }

    public String getAtleta() {
        return atleta;
    }

    public void setAtleta(String atleta) {
        this.atleta = atleta;
    }

    public String getTimeolimp() {
        return timeolimp;
    }

    public void setTimeolimp(String timeolimp) {
        this.timeolimp = timeolimp;
    }
}
