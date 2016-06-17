package info.view.catalog;

import info.entity.*;
import io.datafx.controller.FXMLController;
import javafx.scene.control.TableColumn;

import java.util.List;
import java.util.stream.Collectors;

@FXMLController(value = "../catalog.fxml", title = "Arbitros")
public class ArbitroCatalog extends AbstractCatalog<Arbitro> {
    @Override
    protected Class<Arbitro> getModelClass() {
        return Arbitro.class;
    }

    @Override
    protected List<TableColumn<Arbitro, ?>> modifyColumns(List<TableColumn<Arbitro, ?>> cols) {
        return cols.stream()
                .filter(c -> !c.getText().equalsIgnoreCase("ID"))
                .collect(Collectors.toList());
    }
}
