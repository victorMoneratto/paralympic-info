package info.view;

import info.DataAccess;
import io.datafx.controller.flow.action.BackAction;
import io.datafx.core.concurrent.ProcessChain;
import io.datafx.crud.table.TableColumnFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

public abstract class TableController<T> {
    @FXML
    TableView<T> dataTable;

    @Inject
    DataAccess data;

    @FXML
    @BackAction
    Button backButton;

    @PostConstruct
    public void init() {
        new ProcessChain<List<T>>()
                .addRunnableInExecutor(() -> dataTable.getColumns().setAll(TableColumnFactory.createColumns(getTableClass())))
                .addSupplierInExecutor(() -> data.selectAll(getTableClass()))
                .addConsumerInPlatformThread(list -> dataTable.getItems().setAll(list))
                .addRunnableInPlatformThread(() -> dataTable.getScene().getWindow().sizeToScene())
                .run();
    }

    protected abstract Class<T> getTableClass();
}
