package info.view.details;

import info.entity.Local;
import info.entity.Modalidade;
import info.entity.Partida;
import io.datafx.controller.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@FXMLController(value = "partida-details.fxml", title = "Partida")
public class PartidaDetails extends AbstractDetails<Partida> {

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

    @Override
    @PostConstruct
    public void init() throws IllegalAccessException, InstantiationException {
        tipo.getItems().addAll("Individual", "Time");
        tipo.setValue("Individual");
        modalidade.getItems().addAll(data.select(Modalidade.class));
        modalidade.setValue(modalidade.getItems().get(0));
        local.getItems().addAll(data.select(Local.class));
        local.setValue(local.getItems().get(0));
        super.init();
    }

    @Override
    public void formToModel() {
        LocalTime time = LocalTime.parse(timeField.getText());
        LocalDate date = datePicker.getValue();
        Timestamp dataHora = Timestamp.valueOf(LocalDateTime.of(date, time));

        model.setDatahora(dataHora);
        model.setCompletada(completada.isSelected());
        model.setModalidade(modalidade.getValue().toString());
        model.setObservacao(observacao.getText());
        model.setNomelocal(local.getValue().toString());
        model.setTipo(tipo.getValue());
    }

    @Override
    public void modelToForm() {
        LocalTime time = model.getDatahora().toLocalDateTime().toLocalTime();
        LocalDate date = model.getDatahora().toLocalDateTime().toLocalDate();

        datePicker.setValue(date);
        timeField.setText(time.format(DateTimeFormatter.ISO_LOCAL_TIME));
        completada.setSelected(model.getCompletada());
        modalidade.setValue(modalidade.getConverter().fromString(model.getModalidade()));
        observacao.setText(model.getObservacao());
        local.setValue(local.getConverter().fromString(model.getNomelocal()));
        tipo.setValue(model.getTipo());

    }

    @Override
    protected Class<Partida> getModelClass() {
        return Partida.class;
    }
}
