package info.view.catalog;

import info.DataAccess;
import info.entity.Local;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.BackAction;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.FlowActionHandler;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import io.datafx.core.concurrent.ProcessChain;
import io.datafx.crud.table.TableColumnFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.xml.ws.Action;
import java.util.List;
import java.util.Optional;

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

    @ActionHandler
    protected FlowActionHandler actionHandler;

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
    protected abstract Class getDetailsClass();

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
        Optional<ButtonType> response = new Alert(Alert.AlertType.CONFIRMATION,
                "Tem certeza que deseja remover o item selecionado?").showAndWait();
        T selected = table.getSelectionModel().getSelectedItem();
        if (selected != null && response.isPresent() && response.get() == ButtonType.OK) {
            try {
                data.delete(selected, getModelClass());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            refresh();
        }
    }

    @ActionMethod("add")
    public void onAdd() throws VetoException, FlowException {
        actionHandler.navigate(getDetailsClass());
    }

    @ActionMethod("details")
    public void onDetails() throws VetoException, FlowException {
        T selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            context.register(selected, getModelClass());
            actionHandler.navigate(getDetailsClass());
        }
    }
}
