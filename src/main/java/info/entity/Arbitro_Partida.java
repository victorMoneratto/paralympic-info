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
    private String partida;

    @Id
    @ViewColumn("Arbitro")
    private String arbitro;

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getArbitro() {
        return arbitro;
    }

    public void setArbitro(String arbitro) {
        this.arbitro = arbitro;
    }
}
