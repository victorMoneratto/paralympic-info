package info.view;

import io.datafx.core.concurrent.ProcessChain;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;

public class SimpleController {

    @FXML
    Pane root;

    @PostConstruct
    public void init() {
        new ProcessChain()
                .addRunnableInPlatformThread(() -> root.getScene().getWindow().sizeToScene())
                .run();
    }
}
