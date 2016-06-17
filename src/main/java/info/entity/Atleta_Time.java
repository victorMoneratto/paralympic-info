package info.entity;

import info.entity.key.Atleta_TimePK;
import io.datafx.crud.table.ViewColumn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(Atleta_TimePK.class)
public class Atleta_Time {
    @Id
    @ViewColumn("Atleta")
    private String atleta;

    @Id
    @Column(name = "TimeOlimp")
    @ViewColumn("Time")
    private String timeolimp;

    public String getAtleta() {
        return atleta;
    }

    public void setAtleta(String atleta) {
        this.atleta = atleta;
    }

    public String getTime() {
        return timeolimp;
    }

    public void setTime(String timeolimp) {
        this.timeolimp = timeolimp;
    }
}
