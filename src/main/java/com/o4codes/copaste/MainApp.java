package com.o4codes.copaste;

import com.o4codes.copaste.services.ClipService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("fxml/root.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ClipShare");
        stage.setScene(scene);
        stage.show();
        ClipService.startClipService();

        stage.setOnCloseRequest(event ->  ClipService.stopClipService());

    }

    public static void main(String[] args) {
        launch();
    }
}