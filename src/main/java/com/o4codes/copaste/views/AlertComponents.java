package com.o4codes.copaste.views;

import com.o4codes.copaste.MainApp;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.controlsfx.control.Notifications;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class AlertComponents {
    public static void showSuccessNotification(String title, String content){
        Notifications.create()
                .title(title)
                .text(content)
                .position(Pos.TOP_RIGHT)
                .showInformation();

    }


    public static void showErrorNotification(String title, String content){
        Notifications.create()
                .title(title)
                .text(content)
                .position(Pos.TOP_RIGHT)
                .showError();
    }


    public static void showInfoPopUp(Window parentWindow, String title, String content){
        VBox rootNode = new VBox();
        rootNode.setPadding(new Insets(15));
        rootNode.setSpacing(15);
        rootNode.setMinWidth(500);
        rootNode.getStylesheets()
                .add(Objects.requireNonNull(
                                MainApp.class.getResource("styles/light.css"))
                        .toExternalForm());
        rootNode.getStyleClass().add("root");

        Label titleLbl = new Label(title);
        titleLbl.setFont(Font.font(null, FontWeight.BOLD, 14));

        Label contentLbl = new Label(content);
        contentLbl.setFont(Font.font(null, FontWeight.NORMAL, 12));
        contentLbl.setWrapText(true);

        HBox controlsNode = new HBox();
        controlsNode.setSpacing(15);
        controlsNode.setAlignment(Pos.CENTER_RIGHT);

        MFXButton confirmBtn = new MFXButton("Dismiss");
        confirmBtn.setFont(Font.font(null, FontWeight.BOLD, 14));
        confirmBtn.getStyleClass().add("dark-text");
        confirmBtn.setBackground(new Background(
                new BackgroundFill(Color.TRANSPARENT, null, null)));

        rootNode.getChildren().addAll(titleLbl, contentLbl, controlsNode);
        controlsNode.getChildren().addAll(confirmBtn);

        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.setScene(new Scene(rootNode));
        stage.initOwner(parentWindow.getScene().getWindow());

        BoxBlur blur = new BoxBlur(3, 3, 3);
        parentWindow.getScene().getRoot().setEffect(blur);
        confirmBtn.setOnAction(event -> {
            parentWindow.getScene().getRoot().setEffect(null);
            stage.close();
        });
        stage.showAndWait();
    }


    public static boolean showConfirmAlert(Window parentWindow, String title, String content){
        VBox rootNode = new VBox();
        rootNode.setPadding(new Insets(15));
        rootNode.setSpacing(15);
        rootNode.setMinWidth(500);
        rootNode.getStylesheets()
                .add(Objects.requireNonNull(
                        MainApp.class.getResource("styles/light.css"))
                        .toExternalForm());
        rootNode.getStyleClass().add("root");

        Label titleLbl = new Label(title);
        titleLbl.setFont(Font.font(null, FontWeight.BOLD, 14));

        Label contentLbl = new Label(content);
        contentLbl.setFont(Font.font(null, FontWeight.NORMAL, 12));
        contentLbl.setWrapText(true);

        HBox controlsNode = new HBox();
        controlsNode.setSpacing(15);
        controlsNode.setAlignment(Pos.CENTER_RIGHT);

        MFXButton acceptBtn = new MFXButton("Accept");
        acceptBtn.getStyleClass().add("confirm-btn");

        MFXButton cancelBtn = new MFXButton("Cancel");
        cancelBtn.getStyleClass().add("dismiss-btn");

        rootNode.getChildren().addAll(titleLbl, contentLbl, controlsNode);
        controlsNode.getChildren().addAll(acceptBtn, cancelBtn);

        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.setScene(new Scene(rootNode));
        stage.initOwner(parentWindow.getScene().getWindow());

        BoxBlur blur = new BoxBlur(3, 3, 3);
        parentWindow.getScene().getRoot().setEffect(blur);

        AtomicBoolean result = new AtomicBoolean(false);
        acceptBtn.setOnAction(event -> {
            result.set(true);
            parentWindow.getScene().getRoot().setEffect(null);
            stage.close();
        });

        cancelBtn.setOnAction(event -> {
            parentWindow.getScene().getRoot().setEffect(null);
            stage.close();
        });

        stage.showAndWait();
        return result.get();
    }
}
