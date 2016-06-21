package info.view.details;

import info.entity.Atleta;
import info.entity.Medalha;
import info.entity.Modalidade;
import info.entity.Time;
import io.datafx.controller.FXMLController;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import javax.persistence.Query;
import java.awt.event.MouseAdapter;

@FXMLController(value = "medalha-details.fxml", title = "Medalha")
public class MedalhaDetails extends AbstractDetails<Medalha> {

    @FXML
    ComboBox<Modalidade> modalidade;

    @FXML
    ComboBox<String> medalha;

    @FXML
    ComboBox ganhador;

    @Override
    public void onInit() {
        modalidade.getItems().setAll(data.select(Modalidade.class));
        medalha.getItems().setAll("OURO", "PRATA", "BRONZE");

        modalidade.getSelectionModel().selectedItemProperty().addListener(this::ganhadorEnabled);

//        if (!insertingNew) {
//            ganhadorEnabled(null, null, true);
//        }

        super.onInit();
    }

    @Override
    protected void setFormEnabled(boolean enabled) {
//        super.setFormEnabled(enabled);
    }

    @SuppressWarnings({"unchecked"})
    private void ganhadorEnabled(ObservableValue<? extends Modalidade> observable, Modalidade oldValue, Modalidade newValue) {
        if (newValue != null) {
            model.tipo = newValue.tipo;
            if (newValue.getTipo().equalsIgnoreCase("individual")) {
                Query q = data.createNativeSelect(
                        "SELECT A.* FROM Atleta A JOIN Atleta_Modalidade M " +
                                "ON A.Identificador = M.Atleta Where M.Modalidade = \'" + newValue.getNome() + "\'",
                        Atleta.class);
                ganhador.getItems().setAll(q.getResultList());
            } else {
                Query q = data.createNativeSelect(
                        "SELECT * FROM TimeOlimpico Where Modalidade = \'" + newValue.getNome() + "\'",
                        Time.class);
                ganhador.getItems().setAll(q.getResultList());
            }
        } else {
            ganhador.getSelectionModel().clearSelection();
            ganhador.getItems().clear();
        }
    }

    @Override
    protected void onInsert() {
        Query q = null;
        if (model.isIndividual()) {
            q = data.createNativeUpdate(
                    "INSERT INTO MEDALHA(Atleta, Medalha, Modalidade) VALUES (?, ?, ?)");
            q.setParameter(1, model.getAtleta());
            q.setParameter(2, model.getMedalha());
            q.setParameter(3, model.getModalidade());
        } else {
            q = data.createNativeUpdate(
                    "INSERT INTO MEDALHA(Time, Medalha, Modalidade) VALUES (?, ?, ?)");
            q.setParameter(1, model.getTime());
            q.setParameter(2, model.getMedalha());
            q.setParameter(3, model.getModalidade());
        }

        int inserted = 0;
        try {
            data.getTransaction().begin();
            inserted = q.executeUpdate();
        }finally {
            if (inserted > 0) {
                data.getTransaction().commit();
            } else {
                data.getTransaction().rollback();
            }
        }
//        super.onInsert();
    }

    @Override
    public void modelToForm() {
        selectInCombo(modalidade, m -> m.getNome().equals(model.getModalidade()));
        selectInCombo(medalha, s -> s.equals(model.getMedalha()));

        if (modalidade.getValue().getTipo().equalsIgnoreCase("individual")) {
            selectInCombo(ganhador, g -> ((Atleta) g).identificador.equals(model.getAtleta()));
        } else {
            selectInCombo(ganhador, g -> ((Time) g).identificador.equals(model.getTime()));
        }
    }

    @Override
    public void formToModel() {
        model.setModalidade(modalidade.getValue().getNome());
        model.setMedalha(medalha.getValue());

        if (modalidade.getValue().getTipo().equalsIgnoreCase("individual")) {
            model.setAtleta(((Atleta) ganhador.getValue()).getIdentificador());
            model.setTime(null);
        } else {
            model.setTime(((Time) ganhador.getValue()).getIdentificador());
            model.setAtleta(null);
        }
    }

    @Override
    protected Class<Medalha> getModelClass() {
        return Medalha.class;
    }
}
