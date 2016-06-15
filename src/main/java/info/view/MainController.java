package info.view;

import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.action.LinkAction;
import io.datafx.core.concurrent.ProcessChain;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.annotation.PostConstruct;

@FXMLController(value = "main.fxml", title = "Paralimpíadas")
public class MainController  {

    @FXML
    @LinkAction(LocalTableController.class)
    private Button localButton;

    @PostConstruct
    public void init() {
        new ProcessChain()
                .addRunnableInPlatformThread(() -> localButton.getScene().getWindow().sizeToScene())
                .run();
    }
}
