package info;

import info.view.MainController;
import info.view.SampleController;
import io.datafx.controller.flow.Flow;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Flow(SampleController.class).startInStage(primaryStage);
//        new Flow(MainController.class).startInStage(primaryStage);
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        launch(args);
    }

}
