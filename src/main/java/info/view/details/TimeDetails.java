package info.view.details;

import info.entity.Atleta_Time_Info;
import info.entity.Delegacao;
import info.entity.Modalidade;
import info.entity.Time;
import io.datafx.controller.FXMLController;
import io.datafx.crud.table.TableColumnFactory;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Optional;
import java.util.function.Predicate;

@FXMLController(value = "time-details.fxml", title = "Time")
public class TimeDetails extends AbstractDetails<Time> {

    @FXML
    TextField nome;

    @FXML
    ComboBox<Delegacao> delegacao;

    @FXML
    ComboBox<Modalidade> modalidade;

    @FXML
    TextField categoria;

    @FXML
    ComboBox<String> genero;

    @FXML
    ComboBox<String> medalha;

    @FXML
    TextField comprometimento;

    @FXML
    TableView<Atleta_Time_Info> atletas;

    @Override
    public void onInit() {
        delegacao.getItems().setAll(data.select(Delegacao.class));
        modalidade.getItems().setAll(data.select(Modalidade.class, "UPPER(tipo) = \'TIME\'"));
        genero.getItems().setAll("MASCULINO", "FEMININO", "MISTO");
        medalha.getItems().setAll("OURO", "PRATA", "BRONZE");
        atletas.getColumns().setAll(TableColumnFactory.createColumns(Atleta_Time_Info.class));
        atletas.getItems().setAll(data.select(Atleta_Time_Info.class, "timeolimp = " + model.getIdentificador()));

        super.onInit(); // <- this calls ModelToForm, call last so comboBox are populated and such
    }

    @Override
    public void formToModel() {
        model.setNome(nome.getText());
        model.setDelegacao(delegacao.getSelectionModel().getSelectedItem().getNome());
        model.setModalidade(modalidade.getSelectionModel().getSelectedItem().getNome());
        model.setCategoria(categoria.getText());
        model.setComprometimento(comprometimento.getText());
        model.setGenero(genero.getSelectionModel().getSelectedItem());
        model.setMedalha(medalha.getSelectionModel().getSelectedItem());
    }

    @Override
    public void modelToForm() {
        nome.setText(model.getNome());
        selectInCombo(delegacao, d -> d.getNome().equals(model.getDelegacao()));
        selectInCombo(modalidade, m -> m.getNome().equals(model.getModalidade()));
        categoria.setText(model.getCategoria());
        comprometimento.setText(model.getComprometimento());
        genero.getSelectionModel().select(model.getGenero());
        medalha.getSelectionModel().select(model.getMedalha());
    }

    @Override
    protected Class<Time> getModelClass() {
        return Time.class;
    }
}
