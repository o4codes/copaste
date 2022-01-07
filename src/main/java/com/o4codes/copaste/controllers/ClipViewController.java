package com.o4codes.copaste.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.enums.ButtonType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class ClipViewController implements Initializable {
    @FXML
    private Button closeBtn;

    @FXML
    private Label settingsLabel;

    @FXML
    private Label helpLabel;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
