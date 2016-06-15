package info.view;

import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.action.LinkAction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@FXMLController(value = "main.fxml", title = "Paralimpíadas")
public class MainController extends SimpleController {

    @FXML
    @LinkAction(LocalTableController.class)
    Button localButton;

}
