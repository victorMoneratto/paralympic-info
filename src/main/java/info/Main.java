package info;

import info.view.catalog.ArbitroCatalog;
import info.view.catalog.DelegacaoCatalog;
import info.view.catalog.PaisCatalog;
import io.datafx.controller.flow.Flow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Flow(ArbitroCatalog.class).startInStage(primaryStage);
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        launch(args);
    }

}
