package edu.upc.prop.clusterxx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PropApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PropApp.class.getResource("main-view.fxml"));
        Parent root = fxmlLoader.load();
        PropController controller = fxmlLoader.getController();
        Scene scene = new Scene(root);
        stage.setTitle("PROP 42.1");
        stage.setMinWidth(640.);
        stage.setMinHeight(480.);
        stage.setWidth(stage.getMinWidth());
        stage.setHeight(stage.getMinHeight());
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            if (!controller.onSalir()) event.consume();
        });
        stage.show();
    }
    @Override
    public void stop() {

    }
    public static void main(String[] args) {
        launch();
    }
}