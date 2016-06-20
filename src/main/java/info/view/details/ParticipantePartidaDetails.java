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

import java.time.LocalDate;


@FXMLController(value = "participante-partida-details.fxml", title = "Participante")
public class ParticipantePartidaDetails extends AbstractDetails<Participante_Partida> {

    @FXML
    public ComboBox<Object> participante;

    @FXML
    public TextField pontuacao;

    @FXML
    public TextField classificacao;

    private PartidaInfo info;

    @Override
    public void onInit(){
        info = context.getRegisteredObject(PartidaInfo.class);
        switch (info.type) {
            case Atleta:
                participante.getItems().setAll(data.select(Atleta.class));
                break;
            case Time:
                participante.getItems().setAll(data.select(Time.class));
                break;
        }
        super.onInit();
    }

    @Override
    public void formToModel() {
        switch (info.type) {
            case Atleta:
                model.setParticipante(((Atleta)participante.getSelectionModel().getSelectedItem()).getIdentificador());
                break;
            case Time:
                model.setParticipante(((Time)participante.getSelectionModel().getSelectedItem()).getIdentificador());
                break;
        }
        model.setPontuacao(Double.parseDouble(pontuacao.getText()));
        model.setClassificacao(Integer.parseInt(classificacao.getText()));
    }

    @Override
    public void modelToForm() {
        switch (info.type) {
            case Atleta:
                selectInCombo(participante, s -> ((Atleta)s).getIdentificador().equals(model.getParticipante()));
                break;
            case Time:
                selectInCombo(participante, s -> ((Time)s).getIdentificador().equals(model.getParticipante()));
                break;
        }
        pontuacao.setText(model.getPontuacao().toString());
        classificacao.setText(model.getClassificacao().toString());
    }

    @Override
    protected void onInsert() {
        if(!info.isNew)
            super.onInsert();
    }

    @Override
    protected void onUpdate() {
        if(!info.isNew)
            super.onUpdate();
    }

    @Override
    protected Class<Participante_Partida> getModelClass() {
        return Participante_Partida.class;
    }
}
