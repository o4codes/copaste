package com.o4codes.copaste.controllers;

import com.o4codes.copaste.services.ClipService;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @FXML
    private Button closeBtn;

    @FXML
    private VBox fragmentsPane;

    private double xOffset, yOffset;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //set init fragment
        fragmentsPane.getChildren().add(connectionChoiceFragment());

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


    public VBox connectionChoiceFragment() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);

        VBox.setVgrow(vBox, javafx.scene.layout.Priority.ALWAYS);

        MFXButton joinConnBtn = new MFXButton("Join Connection");
        joinConnBtn.getStyleClass().add("secondary-button");
        joinConnBtn.setPrefWidth(Double.MAX_VALUE);

        MFXButton createConnBtn = new MFXButton("Create Connection");
        createConnBtn.getStyleClass().add("primary-button");
        createConnBtn.setPrefWidth(Double.MAX_VALUE);

        vBox.getChildren().addAll(joinConnBtn, createConnBtn);
        return vBox;
    }
}