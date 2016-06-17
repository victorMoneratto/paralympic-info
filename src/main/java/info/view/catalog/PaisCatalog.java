package info.view.catalog;

import info.entity.Pais;
import io.datafx.controller.FXMLController;

import java.util.List;

@FXMLController(value = "../catalog.fxml", title = "Países")
public class PaisCatalog extends AbstractCatalog<Pais> {
    @Override
    protected Class<Pais> getModelClass() {
        return Pais.class;
    }

    @Override
    protected List<Pais> supplyData() {
        return super.supplyData();
    }
}
