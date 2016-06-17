package info.view.catalog;

import info.DataAccess;
import io.datafx.controller.flow.action.BackAction;
import io.datafx.core.concurrent.ProcessChain;
import io.datafx.crud.table.TableColumnFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

public abstract class AbstractCatalog<T> {
    @FXML
    TableView<T> catalog;

    @Inject
    DataAccess data;

    @FXML
    @BackAction
    Button back;

    @PostConstruct
    public void init() {
        new ProcessChain<List<T>>()
                .addRunnableInExecutor(() -> {
                    List<TableColumn<T, ?>> cols = TableColumnFactory.createColumns(getModelClass());
                    catalog.getColumns().setAll(modifyColumns(cols));
                })
                .addSupplierInExecutor(this::supplyData)
                .addConsumerInPlatformThread(list -> catalog.getItems().setAll(list))
                .addRunnableInPlatformThread(() -> catalog.getScene().getWindow().sizeToScene())
                .addRunnableInExecutor(this::postLoad)
                .run();
    }

    protected abstract Class<T> getModelClass();


    protected List<TableColumn<T,?>> modifyColumns(List<TableColumn<T,?>> cols) {
        return cols;
    }

    protected List<T> supplyData() {
        return data.selectAll(getModelClass());
    }

    protected void postLoad() {
    }
}
