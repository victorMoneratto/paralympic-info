package info.view;

import info.entities.Local;
import io.datafx.controller.FXMLController;

import javax.annotation.PostConstruct;

@FXMLController(value = "table-template.fxml", title = "Locais")
public class LocalTableController extends TableController<Local> {

    @PostConstruct
    @Override
    public void init() {
        super.init();
    }

    @Override
    protected Class<Local> getTableClass() {
        return Local.class;
    }

}
