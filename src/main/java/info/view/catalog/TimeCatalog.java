package info.view.catalog;

import info.entity.Time;
import io.datafx.controller.FXMLController;
import javafx.scene.control.TableColumn;

import java.util.List;
import java.util.stream.Collectors;

@FXMLController(value = "catalog.fxml", title = "Times")
public class TimeCatalog extends AbstractCatalog<Time>{
    @Override
    protected Class<Time> getModelClass() {
        return Time.class;
    }

    @Override
    protected List<TableColumn<Time, ?>> filterColumns(List<TableColumn<Time, ?>> cols) {
        return cols.stream()
                .filter(c -> !c.getText().equalsIgnoreCase("ID"))
                .collect(Collectors.toList());
    }
}
