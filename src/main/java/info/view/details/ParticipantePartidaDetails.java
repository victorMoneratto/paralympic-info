package info.view.details;

import info.entity.*;
import info.view.PartidaInfo;
import io.datafx.controller.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@FXMLController(value = "participante-partida-details.fxml", title = "Participante")
public class ParticipantePartidaDetails extends AbstractDetails<Participante_Partida> {

    @FXML
    public ComboBox<Participante_Partida> participante;

    @FXML
    public TextField pontuacao;

    @FXML
    public TextField classificacao;

    private PartidaInfo info;

    @SuppressWarnings("unchecked")
    @Override
    public void onInit(){
        info = context.getRegisteredObject(PartidaInfo.class);
        List<Participante_Partida> participantes = null;
        switch (info.type) {
            case Atleta:
                participantes =(List<Participante_Partida>)
                        data.createNativeSelect("SELECT uuid_generate_v4() AS uuid, Atl.Identificador AS Atleta, Atl.Nome as Nome, NULL AS Time, Atl.Delegacao AS Delegacao, NULL AS Classificacao, NULL AS Pontuacao\n" +
                                "    FROM Atleta AS Atl JOIN Atleta_Modalidade AS Mod ON Mod.Atleta = Atl.Identificador WHERE Mod.Modalidade = ?", Participante_Partida.class).setParameter(1, info.modalidade.getNome()).getResultList();
                break;
            case Time:
                participantes =(List<Participante_Partida>)
                        data.createNativeSelect("SELECT uuid_generate_v4() AS uuid, NULL AS Atleta, Tim.Nome as Nome, Tim.Identificador AS Time, Tim.Delegacao AS Delegacao, NULL AS Classificacao, NULL AS Pontuacao\n" +
                                "    FROM TimeOlimpico AS Tim WHERE Tim.Modalidade = ?", Participante_Partida.class).setParameter(1, info.modalidade.getNome()).getResultList();
                break;
        }
        participantes = participantes.stream().
                filter(part -> !info.participantes.contains(part)).
                collect(Collectors.toList());
        if(!insertingNew)
            participantes.add(model);
        participante.getItems().setAll(participantes);
        super.onInit();
    }

    @Override
    public void formToModel() {
        switch (info.type) {
            case Atleta:
                model.setAtleta(participante.getSelectionModel().getSelectedItem().getAtleta());
                break;
            case Time:
                model.setTime(participante.getSelectionModel().getSelectedItem().getTime());
                break;
        }
        model.setPartida(info.partida);
        model.setPontuacao(Double.parseDouble(pontuacao.getText()));
        model.setClassificacao(Integer.parseInt(classificacao.getText()));
    }

    @Override
    public void modelToForm() {
        switch (info.type) {
            case Atleta:
                selectInCombo(participante, s -> s.getAtleta().equals(model.getAtleta()));
                break;
            case Time:
                selectInCombo(participante, s -> s.getTime().equals(model.getTime()));
                break;
        }
        pontuacao.setText(model.getPontuacao().toString());
        classificacao.setText(model.getClassificacao().toString());
    }

    @Override
    protected void setFormEnabled(boolean enabled) {
        super.setFormEnabled(enabled);
        if(model.getTime() != null || model.getAtleta() != null)
            participante.setDisable(true);
    }

    @Override
    protected void onInsert() {
        if(info.partida == null) {
            switch (info.type) {
                case Atleta:
                    Atleta atleta = (Atleta)data.createNativeSelect("SELECT * FROM Atleta WHERE Identificador = ?", Atleta.class).setParameter(1, model.getAtleta()).getSingleResult();
                    model.setDelegacao(atleta.getDelegacao());
                    model.setNome(atleta.getNome());
                    break;
                case Time:
                    Time time = (Time)data.createNativeSelect("SELECT * FROM TimeOlimpico WHERE Identificador = ?", Time.class).setParameter(1, model.getTime()).getSingleResult();
                    model.setDelegacao(time.getDelegacao());
                    model.setNome(time.getNome());
                    break;
            }
            info.participantes.add(model);
        }
        else
        {

            Query query = null;
            switch (info.type) {
                case Atleta:
                    query = data.createNativeUpdate("INSERT INTO Participante_Partida(partida, atleta, classificacao, pontuacao) VALUES(?,?,?,?)");
                    query.setParameter(2, model.getAtleta());
                    break;
                case Time:
                    query = data.createNativeUpdate("INSERT INTO Participante_Partida(partida, time, classificacao, pontuacao) VALUES(?,?,?,?)");
                    query.setParameter(2, model.getTime());
                    break;
            }
            query.setParameter(1, info.partida);
            query.setParameter(3, model.getClassificacao());
            query.setParameter(4, model.getPontuacao());
            int inserted = 0;
            try {
                data.getTransaction().begin();
                inserted = query.executeUpdate();
            } finally {
                if (inserted == 1) {
                    data.getTransaction().commit();
                    info.participantes.add(model);
                } else {
                    //TODO alert we couldn't insert row
                    data.getTransaction().rollback();
                }
            }
        }
    }

    @Override
    protected void onUpdate() {
        if(info.partida != null)
            super.onUpdate();
    }

    @Override
    protected Class<Participante_Partida> getModelClass() {
        return Participante_Partida.class;
    }
}
