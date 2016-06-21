package info.view;

import info.entity.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class PartidaInfo {
    public Integer partida;
    public Modalidade modalidade;
    public LocalDate data;
    public String hora;
    public Local local;
    public boolean completada;

    public enum ParticipanteType
    {
        Atleta,
        Time
    }
    public ParticipanteType type;
    public ArrayList<Participante_Partida> participantes;

    public PartidaInfo() {
        participantes = new ArrayList<>();
    }
}
