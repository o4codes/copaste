package com.o4codes.clipshare.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RootController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}