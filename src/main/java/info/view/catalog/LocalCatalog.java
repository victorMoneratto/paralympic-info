package info.view.catalog;

import info.entity.Local;
import io.datafx.controller.FXMLController;

@FXMLController(value = "../catalog.fxml", title = "Locais")
public class LocalCatalog extends AbstractCatalog<Local> {

    @Override
    protected Class<Local> getModelClass() {
        return Local.class;
    }

}
