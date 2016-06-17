package info.view;

import info.view.catalog.LocalCatalog;
import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.LinkAction;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@FXMLController(value = "main.fxml", title = "Paralimpíadas")
public class MainView extends SimpleView {

    @FXML
    @LinkAction(LocalCatalog.class)
    Button local;

    @FXML
    @ActionTrigger("exit")
    Button exit;

    @ActionMethod("exit")
    public void exit() {
        Platform.exit();
    }
}
