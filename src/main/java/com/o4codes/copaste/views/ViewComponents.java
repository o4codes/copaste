package com.o4codes.copaste.views;

import com.o4codes.copaste.MainApp;
import com.o4codes.copaste.services.SocketServerService;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.Objects;

public class ViewComponents {

    public static Stage showRootView(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("fxml/root.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        //stage decorations
        if (stage == null){
            stage = new Stage();
        }
        stage.setTitle("ClipShare");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);

        //set the stage to be able to close the application and stop the clipboard service
        Stage finalStage = stage;

        stage.setOnCloseRequest(event -> {
            event.consume();
            boolean result = AlertComponents.showConfirmAlert(
                    finalStage, "Confirm", "Are you sure you want to exit?");
            if (result) {
                SocketServerService.stopSocketServer();
                finalStage.close();
            }

        });

        return stage;
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
        vBox.getStylesheets().add(Objects.requireNonNull(MainApp.class.getResource("styles/light.css")).toExternalForm());
        vBox.getStyleClass().add("root");

        Label titleLabel = new Label("Help");
        titleLabel.maxWidthProperty().bind(stage.widthProperty());
        titleLabel.getStyleClass().add("header-label");


        Label descriptionLabel = new Label();

        String description = """
                This application allows you to share your clipboard with other users.
                Ctrl C on your device to copy a text to the clipboard.
                Ctrl V on the other device to paste text copied.
                ClipShare will automatically save the text to the clipboard.
                """;

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


    public static HBox bottomFragment(Node parent) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.TOP_RIGHT);
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(0, 20, 0, 0));
        hBox.setPrefHeight(34);

        Label settingsLabel = new Label("Settings");
        FontIcon settingsIcon = new FontIcon("mdi-settings");
        settingsIcon.setIconSize(14);
        settingsLabel.setGraphic(settingsIcon);
        settingsLabel.setContentDisplay(ContentDisplay.RIGHT);
        settingsLabel.getStyleClass().add("small_click_label");

        Label helpLabel = new Label("Help");
        FontIcon helpIcon = new FontIcon("mdi-help");
        helpIcon.setIconSize(14);
        helpLabel.setGraphic(helpIcon);
        helpLabel.setContentDisplay(ContentDisplay.RIGHT);
        helpLabel.getStyleClass().add("small_click_label");
        helpLabel.setOnMouseClicked(event -> {
            BoxBlur blur = new BoxBlur(3, 3, 3);
            parent.setEffect(blur);
            try {
                ViewComponents.helpViewStags().showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }

            parent.setEffect(null);
        });

        hBox.getChildren().addAll(settingsLabel, helpLabel);
        return hBox;
    }




}

