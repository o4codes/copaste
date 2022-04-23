package com.o4codes.copaste.controllers;

import com.o4codes.copaste.MainApp;
import com.o4codes.copaste.services.ClipBoardService;
import com.o4codes.copaste.services.SocketClientService;
import com.o4codes.copaste.services.SocketServerService;
import com.o4codes.copaste.utils.NetworkUtils;
import com.o4codes.copaste.utils.Session;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.enums.ButtonType;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.SocketException;
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
        
        disconnectBtn.setOnAction(event -> {
            try {
                if (Session.webSocketClient != null){
                    SocketClientService.stopClient();
                }
                else {
                    SocketServerService.stopSocketServer();
                }
                ClipBoardService.stopClipBoardListener();
                MainApp.showRootView(null).show();
                this.disconnectBtn.getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try {
            initBindings();
        } catch (SocketException e) {
            e.printStackTrace();
        }
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
        rootPane.getStyleClass().add("card");
        rootPane.setAlignment(Pos.CENTER_LEFT);
        rootPane.setPrefHeight(39);
        rootPane.setPadding(new Insets(0,10,0,10));
        rootPane.setSpacing(5);

        Label label = new Label(clipText);
        label.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        label.getStyleClass().add("dark-text");
        label.setMaxWidth(Double.MAX_VALUE);

        FontIcon fontIcon = new FontIcon("mdi-content-copy");
        MFXButton copyBtn = new MFXButton();
        copyBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        copyBtn.setGraphic(fontIcon);
        copyBtn.setButtonType(ButtonType.RAISED);

        rootPane.getChildren().addAll(label, copyBtn);
        HBox.setHgrow(label, Priority.ALWAYS);
        return rootPane;
    }

    private void initBindings() throws SocketException {
        connectionAddressLbl.setText(NetworkUtils.getSystemNetworkConfig().getHostAddress()+":"+Session.CONNECTION_PORT);
        clipContentLbl.textProperty().bind(Session.clip.content);
        clipDeviceNameLbl.textProperty().bind(Session.clip.user);
        clipDateTimeContent.textProperty().bind(Session.clip.createdAt);

        Session.clip.content.addListener( (observable, oldValue, newValue) -> {
            System.out.println("newValue is "+newValue);
            ObservableList<Node> clipHistoryChildren= clipHistoryPane.getChildren();

            if (clipHistoryChildren.get(0).getStyleClass()
                    .stream()
                    .anyMatch(style -> style == "root")){
                clipHistoryPane.getChildren().clear();
            }


            if (clipHistoryChildren.size() < 10) {
                clipHistoryPane.getChildren().add(0, clipCard(newValue));
            }

            if (clipHistoryChildren.size() == 10) {
                clipHistoryPane.getChildren().remove(9);
                clipHistoryPane.getChildren().add(0, clipCard(newValue));
            }

        } );

    }


}
