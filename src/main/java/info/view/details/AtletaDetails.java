package info.view.details;

import info.entity.Atleta;
import info.entity.Delegacao;
import io.datafx.controller.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.annotation.PostConstruct;
import java.time.LocalDate;


@FXMLController(value = "atleta-details.fxml", title = "Atleta")
public class AtletaDetails extends AbstractDetails<Atleta> {

    @FXML
    public TextField nome;

    @FXML
    public ComboBox<Delegacao> delegacao;

    @FXML
    public DatePicker dataNascimento;

    @FXML
    public TextField altura;

    @FXML
    public TextField peso;

    @FXML
    public ComboBox<String> genero;

    @FXML
    public ImageView image;

    @FXML
    public TextField foto;

    @Override
    public void onInit(){
        delegacao.getItems().setAll(data.select(Delegacao.class));
        genero.getItems().setAll("MASCULINO","FEMININO");
        super.onInit();
    }

    @Override
    public void formToModel() {
        // TODO Parse error handling
        double alturaValue = Double.parseDouble(altura.getText());
        double pesoValue = Double.parseDouble(peso.getText());
        LocalDate dataNascimentoValue = dataNascimento.getValue();

        model.setNome(nome.getText());
        model.setGenero(genero.getSelectionModel().getSelectedItem());
        model.setAltura(alturaValue);
        model.setPeso(pesoValue);
        model.setDataNascimento(java.sql.Date.valueOf(dataNascimentoValue));
        model.setDelegacao(delegacao.getSelectionModel().getSelectedItem().getNome());
        model.setFoto(foto.getText());
    }

    @Override
    public void modelToForm() {
        final String doubleFormat = "%.2f";
        String alturaText = String.format(doubleFormat, model.getAltura());
        String pesoText = String.format(doubleFormat, model.getPeso());
        LocalDate dataNascimentoValue = model.getDataNascimento().toLocalDate();

        nome.setText(model.getNome());
        selectInCombo(genero, s -> s.equals(model.getGenero()));
        altura.setText(alturaText);
        peso.setText(pesoText);
        dataNascimento.setValue(dataNascimentoValue);
        selectInCombo(delegacao, d -> d.getNome().equals(model.getDelegacao()));
        foto.setText(model.getFoto());
        image.setImage(new Image(foto.getText()));
    }

    @Override
    protected Class<Atleta> getModelClass() {
        return Atleta.class;
    }
}
