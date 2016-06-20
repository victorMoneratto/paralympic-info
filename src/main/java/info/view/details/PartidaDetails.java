package info.view.details;

import info.entity.*;
import info.view.PartidaInfo;
import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.util.VetoException;
import io.datafx.crud.table.TableColumnFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@FXMLController(value = "partida-details.fxml", title = "Partida")
public class PartidaDetails extends AbstractDetails<Partida> {

    @FXML
    @ActionTrigger("add")
    public Button add;

    @FXML
    public DatePicker datePicker;

    @FXML
    public TextField timeField;

    @FXML
    public ComboBox<Local> local;

    @FXML
    public CheckBox completada;

    @FXML
    public ComboBox<String> tipo;

    @FXML
    public ComboBox<Modalidade> modalidade;

    @FXML
    public TextField observacao;

    @FXML
    public TableView participantes;

    private PartidaInfo info;

    @Override
    public void onInit() {
        tipo.getItems().addAll("INDIVIDUAL", "TIME");
        // Force type so we can select one or another
        tipo.setValue("INDIVIDUAL");
        modalidade.getItems().setAll(data.select(Modalidade.class, "UPPER(tipo) = \'INDIVIDUAL\'"));
        local.getItems().addAll(data.select(Local.class));

        info = new PartidaInfo(insertingNew);

        tipo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null || newValue.equals("INDIVIDUAL")) {
                participantes.getColumns().setAll(TableColumnFactory.createColumns(Atleta_Partida.class));
                participantes.getItems().setAll(data.select(Atleta_Partida.class, "partida = " + model.getIdentificador()));
                modalidade.getItems().setAll(data.select(Modalidade.class, "UPPER(tipo) = \'INDIVIDUAL\'"));
                info.times.clear();
            } else if(newValue.equals("TIME")) {
                participantes.getColumns().setAll(TableColumnFactory.createColumns(Time_Partida.class));
                participantes.getItems().setAll(data.select(Time_Partida.class, "partida = " + model.getIdentificador()));
                modalidade.getItems().setAll(data.select(Modalidade.class, "UPPER(tipo) = \'TIME\'"));
                info.atletas.clear();
            }
        });
        super.onInit();
    }

    @Override
    protected void setFormEnabled(boolean enabled) {
        super.setFormEnabled(enabled);
        if(!insertingNew)
            tipo.setDisable(true);
        add.setDisable(!enabled);
    }

    @ActionMethod("add")
    public void add() throws VetoException, FlowException {
        // TODO Add player (team or athlete) view
        context.register(info, PartidaInfo.class);
        if(tipo.getValue().equals("INDIVIDUAL")) {
            info.type = PartidaInfo.ParticipanteType.Atleta;
            actionHandler.navigate(ParticipantePartidaDetails.class);
        }
        else if(tipo.getValue().equals("TIME")) {
            info.type = PartidaInfo.ParticipanteType.Time;
            actionHandler.navigate(ParticipantePartidaDetails.class);
        }
    }

    @Override
    public void exit() throws VetoException, FlowException {
        context.register((PartidaInfo)null, PartidaInfo.class);
        super.exit();
    }

    @Override
    public void formToModel() {
        // TODO Parse error handling
        LocalTime time = LocalTime.parse(timeField.getText());
        LocalDate date = datePicker.getValue();
        Timestamp dataHora = Timestamp.valueOf(LocalDateTime.of(date, time));

        model.setDatahora(dataHora);
        model.setCompletada(completada.isSelected());
        model.setModalidade(modalidade.getSelectionModel().getSelectedItem().getNome());
        model.setObservacao(observacao.getText());
        model.setNomelocal(local.getSelectionModel().getSelectedItem().getNome());
        model.setTipo(tipo.getValue());
    }

    @Override
    public void modelToForm() {
        LocalTime time = model.getDatahora().toLocalDateTime().toLocalTime();
        LocalDate date = model.getDatahora().toLocalDateTime().toLocalDate();

        datePicker.setValue(date);
        timeField.setText(time.format(DateTimeFormatter.ISO_LOCAL_TIME));
        completada.setSelected(model.getCompletada());
        selectInCombo(modalidade, s -> s.getNome().equals(model.getModalidade()));
        observacao.setText(model.getObservacao());
        selectInCombo(local, s -> s.getNome().equals(model.getNomelocal()));
        tipo.setValue(model.getTipo());

    }

    @Override
    protected void onInsert() {
        int inserted = 0;
        try {
            Query query = data.makeInsert(model, getModelClass());
            data.getTransaction().begin();
            inserted = query.executeUpdate();
            // TODO Add players
        } finally {
            if (inserted == 1) {
                data.getTransaction().commit();
            } else {
                //TODO alert we couldn't insert row
                data.getTransaction().rollback();
            }
        }
    }

    @Override
    protected void onUpdate() {
        int updated = 0;
        try {
            Query query = data.makeUpdate(model, getModelClass());
            data.getTransaction().begin();
            updated = query.executeUpdate();
            // TODO Delete old players, add new players
        } finally {
            if (updated == 1) {
                data.getTransaction().commit();
            } else {
                //TODO alert we couldn't update row
                data.getTransaction().rollback();
            }
        }
    }

    @Override
    protected Class<Partida> getModelClass() {
        return Partida.class;
    }
}
