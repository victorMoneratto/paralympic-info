package info.view;

import info.entity.Atleta_Partida;
import info.entity.Time_Partida;

import java.util.ArrayList;

/**
 * Created by Seduq on 20/06/2016.
 */
public class PartidaInfo {
    public boolean isNew;
    public enum ParticipanteType
    {
        Atleta,
        Time
    }
    public ParticipanteType type;
    public ArrayList<Atleta_Partida> atletas;
    public ArrayList<Time_Partida> times;

    public PartidaInfo(boolean isNew) {
        this.isNew = false;
        atletas = new ArrayList<>();
        times = new ArrayList<>();
    }
}
