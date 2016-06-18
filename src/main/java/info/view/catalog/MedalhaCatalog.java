package info.view.catalog;

import info.entity.Medalha;
import io.datafx.controller.FXMLController;
import javafx.scene.control.TableColumn;

import java.util.List;
import java.util.stream.Collectors;

@FXMLController(value = "catalog.fxml", title = "Medalhas")
public class MedalhaCatalog extends AbstractCatalog<Medalha> {
    @Override
    protected Class<Medalha> getModelClass() {
        return Medalha.class;
    }

    @Override
    protected void postLoad() {
        remove.setDisable(true);
    }

    @Override
    protected List<TableColumn<Medalha, ?>> filterColumns(List<TableColumn<Medalha, ?>> cols) {
        return cols.stream()
                .filter(c -> !c.getText().equalsIgnoreCase("ID")
                            && !c.getText().equalsIgnoreCase("Atleta")
                            && !c.getText().equalsIgnoreCase("Time"))
                .collect(Collectors.toList());
    }
}
