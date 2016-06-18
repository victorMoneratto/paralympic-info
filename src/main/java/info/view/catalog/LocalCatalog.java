package info.view.catalog;

import info.entity.Local;
import info.view.details.LocalDetails;
import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

@FXMLController(value = "catalog.fxml", title = "Locais")
public class LocalCatalog extends AbstractCatalog<Local> {

    @Override
    protected Class<Local> getModelClass() {
        return Local.class;
    }

    @Override
    protected Class getDetailsClass() {
        return LocalDetails.class;
    }
}
