package info.entity;

import info.entity.key.Arbitro_PartidaPK;
import io.datafx.crud.table.ViewColumn;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(Arbitro_PartidaPK.class)
public class Arbitro_Partida {
    @Id
    @ViewColumn("Partida")
    public Integer partida;

    @Id
    @ViewColumn("Arbitro")
    public Integer arbitro;

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
