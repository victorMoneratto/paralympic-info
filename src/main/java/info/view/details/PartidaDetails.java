package info.view.details;

import info.entity.*;
import info.view.PartidaInfo;
import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.injection.scopes.FlowScoped;
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
import java.util.List;
import java.util.Optional;

@FXMLController(value = "partida-details.fxml", title = "Partida")
public class PartidaDetails extends AbstractDetails<Partida> {

    @FXML
    @ActionTrigger("addPlayer")
    public Button addParticipante;

    @FXML
    @ActionTrigger("editPlayer")
    public Button editParticipante;

    @FXML
    @ActionTrigger("removePlayer")
    public Button removeParticipante;

    @FXML
    public DatePicker datePicker;

    @FXML
    public TextField timeField;

    @FXML
    public ComboBox<Local> local;

    @FXML
    public CheckBox completada;

    @FXML
    public ComboBox<Modalidade> modalidade;

    @FXML
    public TextField observacao;

    @FXML
    public TableView participantes;

    private PartidaInfo info;

    @SuppressWarnings("unchecked")
    @Override
    public void onInit() {
        modalidade.getItems().setAll(data.select(Modalidade.class));
        local.getItems().addAll(data.select(Local.class));

        List<Participante_Partida> players = data.select(Participante_Partida.class, "partida = " + model.getIdentificador());
        participantes.getColumns().setAll(TableColumnFactory.createColumns(Participante_Partida.class));

        info = context.getRegisteredObject(PartidaInfo.class);
        if(info == null) {
            info = new PartidaInfo();
            info.participantes.addAll(players);
        }
        else {
            infoToForm();
            setFormEnabled(true);
        }
        participantes.getItems().setAll(info.participantes);

        super.onInit();
    }

    private void infoToForm() {
        modalidade.setValue(info.modalidade);
        local.setValue(info.local);
        datePicker.setValue(info.data);
        timeField.setText(info.hora);
        completada.setSelected(info.completada);
    }

    private void formToInfo() {
        info.partida = model.getIdentificador();
        info.modalidade = modalidade.getValue();
        info.local = local.getValue();
        info.data = datePicker.getValue();
        info.hora = timeField.getText();
        info.completada = completada.isSelected();

        if(modalidade.getValue().getTipo().toUpperCase().equals("INDIVIDUAL")) {
            info.type = PartidaInfo.ParticipanteType.Atleta;
        }
        else if(modalidade.getValue().getTipo().toUpperCase().equals("TIME")) {
            info.type = PartidaInfo.ParticipanteType.Time;
        }
    }


    @Override
    protected void setFormEnabled(boolean enabled) {
        super.setFormEnabled(enabled);
        if(!insertingNew || (info != null && info.participantes.size() > 0))
            modalidade.setDisable(true);
        addParticipante.setDisable(!enabled);
        editParticipante.setDisable(!enabled);
        removeParticipante.setDisable(!enabled);
    }

    @ActionMethod("addPlayer")
    public void addPlayer() throws VetoException, FlowException {
        if(modalidade.getValue() == null)
            return;
        formToInfo();
        context.register(info, PartidaInfo.class);
        actionHandler.navigate(ParticipantePartidaDetails.class);
    }

    @ActionMethod("editPlayer")
    public void editPlayer() throws VetoException, FlowException {
        context.register(info, PartidaInfo.class);
        Participante_Partida selected = (Participante_Partida)participantes.getSelectionModel().getSelectedItem();
        context.register(selected, Participante_Partida.class);
        actionHandler.navigate(ParticipantePartidaDetails.class);
    }

    @ActionMethod("removePlayer")
    public void removePlayer() throws VetoException, FlowException {
        Participante_Partida selected = (Participante_Partida)participantes.getSelectionModel().getSelectedItem();
        if(selected == null)
            return;
        Optional<ButtonType> response = new Alert(Alert.AlertType.CONFIRMATION,
                "Tem certeza que deseja remover o item selecionado?").showAndWait();
        int removed = 0;
        if (response.isPresent() && response.get() == ButtonType.OK) {
            if(selected.getPartida() == null) {
                info.participantes.remove(selected);
            }
            else {
                try {
                    data.getTransaction().begin();
                    Query query = null;
                    switch (info.type) {
                        case Atleta:
                            query = data.createNativeUpdate("DELETE FROM Participante_Partida WHERE Atleta = ? AND Partida = ?").setParameter(1, selected.getAtleta()).setParameter(2, model.getIdentificador());
                            break;
                        case Time:
                            query = data.createNativeUpdate("DELETE FROM Participante_Partida WHERE Time = ? AND Partida = ?").setParameter(1, selected.getTime()).setParameter(2, model.getIdentificador());
                            break;
                    }
                    removed = query.executeUpdate();
                } finally {
                    if (removed == 1) {
                        data.getTransaction().commit();
                        info.participantes.remove(selected);
                    } else {
                        //TODO Alert that the row wasn't deleted
                        data.getTransaction().rollback();
                    }
                }
            }
        }

        refresh();
    }

    @SuppressWarnings("unchecked")
    private void refresh() {
        participantes.getItems().setAll(info.participantes);
        participantes.refresh();
    }

    @Override
    @ActionMethod("back")
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

        if(modalidade.getValue().getTipo().toUpperCase().equals("INDIVIDUAL")) {
            info.type = PartidaInfo.ParticipanteType.Atleta;
        }
        else if(modalidade.getValue().getTipo().toUpperCase().equals("TIME")) {
            info.type = PartidaInfo.ParticipanteType.Time;
        }
    }

    @Override
    protected void onInsert() {
        int inserted = 0;
        try {
            Query query = data.makeInsert(model, getModelClass());
            data.getTransaction().begin();
            inserted = query.executeUpdate();
            Integer partidaId = ((Partida)data.createNativeSelect("SELECT * FROM Partida WHERE NomeLocal = ? AND DataHora = ?", Partida.class).
                    setParameter(1, model.getNomelocal()).setParameter(2, model.getDatahora()).getSingleResult()).getIdentificador();
            for(Participante_Partida part : info.participantes) {
                part.setPartida(partidaId);
                Query insertParticipante = null;
                switch (info.type) {
                    case Atleta:
                        insertParticipante = data.createNativeUpdate("INSERT INTO Participante_Partida(partida, atleta, classificacao, pontuacao) VALUES(?,?,?,?)");
                        insertParticipante.setParameter(2, part.getAtleta());
                        break;
                    case Time:
                        insertParticipante = data.createNativeUpdate("INSERT INTO Participante_Partida(partida, time, classificacao, pontuacao) VALUES(?,?,?,?)");
                        insertParticipante.setParameter(2, part.getTime());
                        break;
                }
                insertParticipante.setParameter(1, partidaId);
                insertParticipante.setParameter(3, part.getClassificacao());
                insertParticipante.setParameter(4, part.getPontuacao());
                insertParticipante.executeUpdate();
            }
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
            data.createNativeUpdate("DELETE FROM Atleta_Partida WHERE Partida = ?").setParameter(1, model.getIdentificador()).executeUpdate();
            data.createNativeUpdate("DELETE FROM Time_Partida WHERE Partida = ?").setParameter(1, model.getIdentificador()).executeUpdate();
            for(Participante_Partida part : info.participantes) {
                part.setPartida(model.getIdentificador());
                Query insertParticipante = null;
                switch (info.type) {
                    case Atleta:
                        insertParticipante = data.createNativeUpdate("INSERT INTO Participante_Partida(partida, atleta, classificacao, pontuacao) VALUES(?,?,?,?)");
                        insertParticipante.setParameter(2, part.getAtleta());
                        break;
                    case Time:
                        insertParticipante = data.createNativeUpdate("INSERT INTO Participante_Partida(partida, time, classificacao, pontuacao) VALUES(?,?,?,?)");
                        insertParticipante.setParameter(2, part.getTime());
                        break;
                }
                insertParticipante.setParameter(1, model.getIdentificador());
                insertParticipante.setParameter(3, part.getClassificacao());
                insertParticipante.setParameter(4, part.getPontuacao());
                insertParticipante.executeUpdate();
            }
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
