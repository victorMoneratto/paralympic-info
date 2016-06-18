package info.view.catalog;

import info.entity.Atleta;
import info.view.details.AtletaDetails;
import io.datafx.controller.FXMLController;
import javafx.scene.control.TableColumn;

import java.util.List;
import java.util.stream.Collectors;

@FXMLController(value = "catalog.fxml", title = "Atletas")
public class AtletaCatalog extends AbstractCatalog<Atleta> {
    @Override
    protected Class<Atleta> getModelClass() {
        return Atleta.class;
    }

    @Override
    protected Class getDetailsClass() {
        return AtletaDetails.class;
    }

    @Override
    protected List<TableColumn<Atleta, ?>> filterColumns(List<TableColumn<Atleta, ?>> cols) {
        return cols.stream()
                .filter(c -> !c.getText().equalsIgnoreCase("ID")
                            && !c.getText().equalsIgnoreCase("Foto"))
                .collect(Collectors.toList());
    }
}
