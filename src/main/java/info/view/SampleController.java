package info.view;

import info.DataAccess;
import info.entities.Local;
import io.datafx.controller.FXMLController;
import io.datafx.crud.table.TableColumnFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

@FXMLController("sample.fxml")
public class SampleController {

    @FXML
    TableView<Local> dataTable;

    @Inject
    DataAccess data;

    @PostConstruct
    public void init() {
        dataTable.getColumns().setAll(TableColumnFactory.createColumns(Local.class));
        List<Local> list = data.selectAll(Local.class);
        dataTable.getItems().setAll(list);
    }


}
