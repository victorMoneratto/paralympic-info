package info.view;

import io.datafx.core.concurrent.ProcessChain;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;

public class SimpleView {

    @FXML
    Pane root;

    @PostConstruct
    public void init() throws IllegalAccessException, InstantiationException {
        new ProcessChain()
                .addRunnableInPlatformThread(() -> ((Stage)root.getScene().getWindow()).setMaximized(false))
                .addRunnableInPlatformThread(() -> ((Stage)root.getScene().getWindow()).setMaxWidth(root.getMaxWidth()))
                .addRunnableInPlatformThread(() -> ((Stage)root.getScene().getWindow()).setMaxHeight(root.getMaxHeight()))
                .addRunnableInPlatformThread(() -> root.getScene().getWindow().sizeToScene())
                .addRunnableInPlatformThread(() -> root.getScene().getWindow().centerOnScreen())
                .run();
    }
}
