package info.entity;

import info.entity.key.Atleta_Time_InfoPK;
import io.datafx.crud.table.ViewColumn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(Atleta_Time_InfoPK.class)
public class Atleta_Time_Info {
    @Id
//    @ViewColumn("Atleta")
    public Integer atleta;

    @Id
    @Column(name = "TimeOlimp")
//    @ViewColumn("Time")
    public Integer timeolimp;

    @ViewColumn("Atleta")
    public String nomeAtleta;

//    @ViewColumn("Time")
    public String nometime;

//    @ViewColumn("Delegação")
    public String delegacao;

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

    public Integer getTimeolimp() {
        return timeolimp;
    }

    public void setTimeolimp(Integer timeolimp) {
        this.timeolimp = timeolimp;
    }

    public String getNomeAtleta() {
        return nomeAtleta;
    }

    public void setNomeAtleta(String nomeAtleta) {
        this.nomeAtleta = nomeAtleta;
    }

    public String getNometime() {
        return nometime;
    }

    public void setNometime(String nometime) {
        this.nometime = nometime;
    }

    public String getDelegacao() {
        return delegacao;
    }

    public void setDelegacao(String delegacao) {
        this.delegacao = delegacao;
    }
}
