package info.view.catalog;

import info.entity.Delegacao;
import io.datafx.controller.FXMLController;

@FXMLController(value = "../catalog.fxml", title = "Delegações")
public class DelegacaoCatalog extends AbstractCatalog<Delegacao> {
    @Override
    protected Class<Delegacao> getModelClass() {
        return Delegacao.class;
    }
}
