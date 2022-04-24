package com.o4codes.copaste;


import com.o4codes.copaste.views.AlertComponents;
import com.o4codes.copaste.views.ViewComponents;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        ViewComponents.showRootView(stage).show();
    }

    public static void main(String[] args) {
        launch();

    }
}