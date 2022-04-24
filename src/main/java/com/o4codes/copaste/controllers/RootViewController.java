package com.o4codes.copaste.controllers;

import com.o4codes.copaste.services.ClipBoardService;
import com.o4codes.copaste.services.SocketServerService;
import com.o4codes.copaste.utils.NetworkUtils;
import com.o4codes.copaste.views.ViewComponents;
import com.o4codes.copaste.views.AlertComponents;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootViewController implements Initializable {

    @FXML
    private BorderPane rootPane;

    @FXML
    private Button closeBtn;

    @FXML
    private VBox fragmentsPane;

    private double xOffset, yOffset;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rootPane.setBottom(ViewComponents.bottomFragment(rootPane));
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
            event.consume();
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            boolean result = AlertComponents.showConfirmAlert(
                    stage, "Confirm", "Are you sure you want to exit?");

            if (result) {
                SocketServerService.stopSocketServer();
                stage.close();
            }
        });

    }

    private Node connectionChoiceFragment() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);
        VBox.setVgrow(vBox, Priority.ALWAYS);

        MFXButton joinConnBtn = new MFXButton("Join Connection");
        joinConnBtn.getStyleClass().add("secondary-button");
        joinConnBtn.setPrefWidth(Double.MAX_VALUE);

        MFXButton createConnBtn = new MFXButton("Create Connection");
        createConnBtn.getStyleClass().add("primary-button");
        createConnBtn.setPrefWidth(Double.MAX_VALUE);

        joinConnBtn.setOnAction(event -> {
            fragmentsPane.getChildren().clear();
            fragmentsPane.getChildren().add(joinConnectionPane());
        });

        createConnBtn.setOnAction(event -> {
            try {
                if (NetworkUtils.getSystemNetworkConfig() != null) {
                    SocketServerService.startSocketServer(); //start the server
                    ClipBoardService.startClipBoardListener(); // start the clipboard listener

                    ViewComponents.clipViewStage().show();
                    createConnBtn.getScene().getWindow().hide();

                    AlertComponents.showSuccessNotification("Server Started","Server is running");
                }
                else {
                    AlertComponents.showErrorNotification("Network Error","No network is found");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        vBox.getChildren().addAll(joinConnBtn, createConnBtn);
        return vBox;
    }


    private Node joinConnectionPane(){
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(vBox, javafx.scene.layout.Priority.ALWAYS);

        Label addressLabel = new Label("Host URL");
        addressLabel.getStyleClass().add("field_lbl");
        addressLabel.setPrefWidth(Double.MAX_VALUE);

        TextField URLfield = new TextField();
        URLfield.getStyleClass().add("field");
        URLfield.setPromptText("Enter host URL");
        URLfield.setPrefWidth(Double.MAX_VALUE);

        HBox btnPane = new HBox();
        btnPane.setSpacing(20);
        btnPane.setMaxWidth(Double.MAX_VALUE);
        VBox.setMargin(btnPane, new Insets(40,0,0,0));

        MFXButton homeBtn = new MFXButton("Home");
        homeBtn.getStyleClass().add("secondary-button");
        homeBtn.setMaxWidth(Double.MAX_VALUE);
        homeBtn.setOnAction(event -> {
            fragmentsPane.getChildren().clear();
            fragmentsPane.getChildren().add(connectionChoiceFragment());
        });

        MFXButton proceedBtn = new MFXButton("Proceed");
        proceedBtn.getStyleClass().add("primary-button");
        proceedBtn.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(proceedBtn, Priority.ALWAYS);
        HBox.setHgrow(homeBtn, Priority.ALWAYS);
        btnPane.getChildren().addAll(homeBtn, proceedBtn);
        addressLabel.setLabelFor(URLfield);
        vBox.getChildren().addAll(addressLabel, URLfield, btnPane);
        return vBox;
    }
}