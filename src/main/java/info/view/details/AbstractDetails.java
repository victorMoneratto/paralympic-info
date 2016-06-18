package info.view.details;

import info.DataAccess;
import info.view.SimpleView;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.FlowActionHandler;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.Optional;

public abstract class AbstractDetails<T> extends SimpleView {
    @Inject
    protected DataAccess data;

    protected T model;

    @FXML
    @ActionTrigger("back")
    Button back;

    @FXML
    @ActionTrigger("save")
    Button save;

    @FXML
    @ActionTrigger("edit")
    Button edit;

    @FXML
    Pane form;

    @FXMLViewFlowContext
    ViewFlowContext context;

    @ActionHandler
    FlowActionHandler actionHandler;

    boolean insertingNew;

    @PostConstruct
    public void init() throws IllegalAccessException, InstantiationException {
        super.init();
        model = context.getRegisteredObject(getModelClass());
        if (model == null) {
            insertingNew = true;
            model = getModelClass().newInstance();
            setFormEnabled(true);
            edit.setVisible(false);
        } else {
            insertingNew = false;
            modelToForm();
            setFormEnabled(false);
        }
    }

    protected void setFormEnabled(boolean enabled) {
        form.getChildren().forEach(node -> {
            if (!(node instanceof Label)) {
                node.setDisable(!enabled);
            }
        });
        save.setDisable(!enabled);
        edit.setDisable(enabled);
    }

    @PreDestroy
    @ActionMethod("back")
    public void exit() throws VetoException, FlowException {
        // "remove" registered model
        context.register((T) null, getModelClass());
        actionHandler.navigateBack();
    }

    @ActionMethod("edit")
    public void edit() {
        setFormEnabled(true);
    }

    @ActionMethod("save")
    public void save() throws VetoException, FlowException {
        if (insertingNew) {
            Optional<ButtonType> response = new Alert(Alert.AlertType.CONFIRMATION,
                    "Tem certeza que deseja inserir os dados?").showAndWait();
            if (response.isPresent() && response.get() == ButtonType.OK) {
                //TODO
//                data.insert(model, getModelClass());
                exit();
            }
        } else {
            Optional<ButtonType> response = new Alert(Alert.AlertType.CONFIRMATION,
                    "Tem certeza que deseja atualizar os dados?").showAndWait();
            if (response.isPresent() && response.get() == ButtonType.OK) {
                //TODO
//                data.update(model, getModelClass());
                exit();
            }
        }
    }

    public abstract void formToModel();

    public abstract void modelToForm();

    protected abstract Class<T> getModelClass();
}
