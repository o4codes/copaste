package com.o4codes.copaste;

import com.o4codes.copaste.services.ClipService;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;


public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("fxml/root.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        //stage decorations
        stage.setTitle("ClipShare");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);

        // set up services
        stage.setOnShown(event -> ClipService.startClipService());

        //set the stage to be able to close the application and stop the clipboard service
        stage.setOnCloseRequest(event ->  ClipService.stopClipService());

        stage.show(); // display main window
    }

    //show a help window
    public static Stage helpViewStags() throws IOException {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.setResizable(false);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(15));
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.TOP_RIGHT);
        vBox.getStylesheets().add(MainApp.class.getResource("styles/light.css").toExternalForm());
        vBox.getStyleClass().add("root");

        Label titleLabel = new Label("Help");
        titleLabel.maxWidthProperty().bind(stage.widthProperty());
        titleLabel.getStyleClass().add("header-label");


        Label descriptionLabel = new Label();

        String description= "This application allows you to share your clipboard with other users.\n" +
                "Ctrl C on your device to copy a text to the clipboard.\n" +
                "Ctrl V on the other device to paste text copied.\n" +
                "ClipShare will automatically save the text to the clipboard.\n";

        descriptionLabel.setText(description);
        descriptionLabel.setWrapText(true);

        MFXButton closeBtn = new MFXButton("Close");
        closeBtn.getStyleClass().add("secondary-button");
        closeBtn.setOnAction(event -> stage.close());

        VBox.setVgrow(descriptionLabel, Priority.ALWAYS);
        vBox.getChildren().addAll(titleLabel, descriptionLabel, closeBtn);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);

        return stage;
    }

    public static Stage clipViewStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("fxml/clipView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = new Stage();

        //stage decorations
        stage.setTitle("ClipShare");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);

        return stage;
    }

    public static void main(String[] args) {
        launch();
    }
}