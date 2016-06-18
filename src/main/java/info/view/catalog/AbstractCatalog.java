package info.view.catalog;

import info.DataAccess;
import info.entity.Local;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.BackAction;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.core.concurrent.ProcessChain;
import io.datafx.crud.table.TableColumnFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.xml.ws.Action;
import java.util.List;

public abstract class AbstractCatalog<T> {

    @Inject
    protected DataAccess data;

    @FXML
    protected TableView<T> table;

    @FXML
    protected Pane root;

    @FXML
    @ActionTrigger("add")
    protected Button add;

    @FXML
    @ActionTrigger("remove")
    protected Button remove;

    @FXML
    @ActionTrigger("details")
    protected Button details;

    @FXML
    @BackAction
    Button back;

    @FXMLViewFlowContext
    ViewFlowContext context;

    @PostConstruct
    public void init() {

        // Generate columns from Model @ViewColumn annotations
        List<TableColumn<T, ?>> cols = TableColumnFactory.createColumns(getModelClass());
        table.getColumns().setAll(filterColumns(cols));

        // Load data
        refresh();
        postLoad();

        // Wait for full FXML injection and then fit stage to scene bounds
        new ProcessChain()
                .addRunnableInPlatformThread(() -> table.getScene().getWindow().sizeToScene())
                .run();
    }

    protected void refresh() {
        // Load data on the background then pass it to the table
        new ProcessChain<List<T>>()
                .addSupplierInExecutor(this::supplyData)
                .addConsumerInPlatformThread(list -> table.getItems().setAll(list))
                .run();
    }

    protected abstract Class<T> getModelClass();

    protected List<TableColumn<T, ?>> filterColumns(List<TableColumn<T, ?>> cols) {
        return cols;
    }

    protected List<T> supplyData() {
        return data.select(getModelClass());
    }

    protected void postLoad() {
    }

    @ActionMethod("remove")
    public void onRemove() {
        T selected = table.getSelectionModel().getSelectedItem();
        try {
            data.delete(selected, getModelClass());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        refresh();
    }
}
