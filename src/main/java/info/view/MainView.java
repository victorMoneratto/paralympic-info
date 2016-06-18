package info.view;

import info.view.catalog.*;
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
    @LinkAction(AtletaCatalog.class)
    Button atletas;

    @FXML
    @LinkAction(TimeCatalog.class)
    Button times;

    @FXML
    @LinkAction(PartidaCatalog.class)
    Button partidas;

    @FXML
    @LinkAction(MedalhaCatalog.class)
    Button medalhas;

    @FXML
    @LinkAction(LocalCatalog.class)
    Button locais;

    @FXML
    @ActionTrigger("exit")
    Button exit;

    @ActionMethod("exit")
    public void exit() {
        Platform.exit();
    }
}
