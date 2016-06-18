package info.view.details;

import info.entity.Atleta;
import info.entity.Delegacao;
import io.datafx.controller.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;


@FXMLController(value = "atleta-details.fxml", title = "Atleta")
public class AtletaDetails extends AbstractDetails<Atleta> {

    @FXML
    TextField nome;

    @FXML
    ComboBox<Delegacao> delegacao;

    @FXML
    DatePicker dataNascimento;

    // TODO: Check float and double attributes

    @FXML
    TextField altura;

    @FXML
    TextField peso;

    @FXML
    TextField genero;

    // TODO: Image picker

    @FXML
    TextField foto;

    @Override
    @PostConstruct
    public void init() throws IllegalAccessException, InstantiationException {
        List<Delegacao> delegacoes = data.select(Delegacao.class);
        delegacao.getItems().addAll(delegacoes);
        delegacao.setValue(delegacao.getItems().get(0));
        super.init();
    }

    @Override
    public void formToModel() {
        double alturaValue = Double.parseDouble(altura.getText());
        double pesoValue = Double.parseDouble(peso.getText());
        LocalDate dataNascimentoValue = dataNascimento.getValue();

        model.setNome(nome.getText());
        model.setGenero(genero.getText());
        model.setAltura(alturaValue);
        model.setPeso(pesoValue);
        model.setDataNascimento(java.sql.Date.valueOf(dataNascimentoValue));
        model.setDelegacao(delegacao.getValue().toString());
        model.setFoto(foto.getText());
    }

    @Override
    public void modelToForm() {
        final String doubleFormat = "%.2f";
        String alturaText = String.format(doubleFormat, model.getAltura());
        String pesoText = String.format(doubleFormat, model.getPeso());
        LocalDate dataNascimentoValue = model.getDataNascimento().toLocalDate();

        nome.setText(model.getNome());
        genero.setText(model.getGenero());
        altura.setText(alturaText);
        peso.setText(pesoText);
        dataNascimento.setValue(dataNascimentoValue);
        delegacao.setValue(delegacao.getConverter().fromString(model.getDelegacao()));
        foto.setText(model.getFoto());
    }

    @Override
    protected Class<Atleta> getModelClass() {
        return Atleta.class;
    }
}
