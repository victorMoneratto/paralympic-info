package info;

import info.view.MainController;
import io.datafx.controller.flow.Flow;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.sql.SQLException;

public class Main extends Application {

    @Inject
    DataAccess data;

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Flow(MainController.class).startInStage(primaryStage);
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        launch(args);
    }

}
