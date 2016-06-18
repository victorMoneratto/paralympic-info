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
    public Integer atleta;

    @Id
    @Column(name = "TimeOlimp")
    @ViewColumn("Time")
    public Integer timeolimp;

    public Integer getAtleta() {
        return atleta;
    }

    public void setAtleta(Integer atleta) {
        this.atleta = atleta;
    }

    public Integer getTime() {
        return timeolimp;
    }

    public void setTime(Integer timeolimp) {
        this.timeolimp = timeolimp;
    }
}
