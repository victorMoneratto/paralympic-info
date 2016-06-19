package info.view.catalog;

import info.entity.Partida;
import info.view.details.PartidaDetails;
import io.datafx.controller.FXMLController;
import javafx.scene.control.TableColumn;

import java.util.List;
import java.util.stream.Collectors;

@FXMLController(value = "catalog.fxml", title = "Partidas")
public class PartidaCatalog extends AbstractCatalog<Partida>{
    @Override
    protected Class<Partida> getModelClass() {
        return Partida.class;
    }

    @Override
    protected Class getDetailsClass() {
        return PartidaDetails.class;
    }

    @Override
    protected List<TableColumn<Partida, ?>> filterColumns(List<TableColumn<Partida, ?>> cols) {
        return cols.stream()
                .filter(c -> !c.getText().equalsIgnoreCase("ID")
                            && !c.getText().equalsIgnoreCase("Observação"))
                .collect(Collectors.toList());
    }
}
