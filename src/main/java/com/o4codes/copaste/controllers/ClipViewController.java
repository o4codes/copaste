package com.o4codes.copaste.controllers;

import com.o4codes.copaste.MainApp;
import com.o4codes.copaste.services.SocketServerService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.enums.ButtonType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class ClipViewController implements Initializable {
    @FXML
    private BorderPane rootPane;

    @FXML
    private Button closeBtn;

    @FXML
    private VBox clipHistoryPane;

    @FXML
    private Label connectionAddressLbl;

    @FXML
    private Label clipDeviceNameLbl;

    @FXML
    private Label clipContentLbl;

    @FXML
    private Label clipDateTimeContent;

    @FXML
    private MFXScrollPane scrollPane;

    @FXML
    private MFXButton disconnectBtn;

    private double xOffset, yOffset;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rootPane.setBottom(MainApp.bottomFragment(rootPane));
        setEmptyClipHistory();

        closeBtn.getParent().setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        closeBtn.getParent().setOnMouseDragged(event -> {
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        closeBtn.setOnAction(event -> {
            SocketServerService.stopSocketServer();
            System.exit(0);
        });

    }

    private void setEmptyClipHistory(){
        VBox root = new VBox();
        root.getStyleClass().add("root");
        root.setPrefHeight(360);
        root.setAlignment(Pos.CENTER);

        Label contentLabel = new Label("Empty Clipboard History");
        contentLabel.getStyleClass().add("small_click_label");

        root.getChildren().add(contentLabel);
        clipHistoryPane.getChildren().removeAll();
        clipHistoryPane.getChildren().add(root);
    }

    public Node clipCard(String clipText){

        HBox rootPane = new HBox();
        rootPane.getStyleClass().add("primary-container");
        rootPane.setAlignment(Pos.CENTER_LEFT);
        rootPane.setPrefHeight(39);
        rootPane.setPadding(new Insets(0,10,0,10));

        Label label = new Label(clipText);
        label.setPrefWidth(Double.MAX_VALUE);

        FontIcon fontIcon = new FontIcon("mdi-content-copy");
        MFXButton copyBtn = new MFXButton();
        copyBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        copyBtn.setGraphic(fontIcon);
        copyBtn.setButtonType(ButtonType.RAISED);

        rootPane.getChildren().addAll(label, copyBtn);
        return rootPane;
    }
}
