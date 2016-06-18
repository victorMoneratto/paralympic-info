package info.view.details;

import info.entity.Local;
import io.datafx.controller.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@FXMLController(value = "local-details.fxml", title = "Local")
public class LocalDetails extends AbstractDetails<Local> {

    @FXML
    TextField nome;

    @FXML
    TextField endereco;

    @Override
    public void formToModel() {
        model.setNome(nome.getText());
        model.setEndereco(endereco.getText());
    }

    @Override
    public void modelToForm() {
        nome.setText(model.getNome());
        endereco.setText(model.getEndereco());
    }

    @Override
    protected Class<Local> getModelClass() {
        return Local.class;
    }
}
