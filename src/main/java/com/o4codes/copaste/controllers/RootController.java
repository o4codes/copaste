package com.o4codes.copaste.controllers;

import com.o4codes.copaste.services.ClipService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {


    @FXML
    private Button closeBtn;

    private double xOffset, yOffset;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
            ClipService.stopClipService();
            System.exit(0);
        });
    }
}