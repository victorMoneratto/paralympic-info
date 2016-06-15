package info.view;

import info.DataAccess;
import info.entities.Local;
import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.action.BackAction;
import io.datafx.controller.flow.action.LinkAction;
import io.datafx.core.concurrent.ProcessChain;
import io.datafx.crud.table.TableColumnFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.Executor;

@FXMLController(value = "table-template.fxml", title = "Locais")
public class LocalTableController {

    @FXML
    TableView<Local> dataTable;

    @Inject
    DataAccess data;

    @FXML
    @BackAction
    Button backButton;

    @PostConstruct
    public void init() {
        new ProcessChain<List<Local>>()
                .addRunnableInExecutor(() -> dataTable.getColumns().setAll(TableColumnFactory.createColumns(Local.class)))
                .addSupplierInExecutor(() -> data.selectAll(Local.class))
                .addConsumerInPlatformThread(list -> dataTable.getItems().setAll(list))
                .addRunnableInPlatformThread(() -> dataTable.getScene().getWindow().sizeToScene())
                .run();
    }


}
