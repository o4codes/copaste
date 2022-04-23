package com.o4codes.copaste.utils;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Window;
import org.controlsfx.control.Notifications;

public class ViewComponents {

    public static void showSuccessNotification(String title, String content){
        /**
         * Displays a success notification
         * ** title : String
         * ** content : String
         */
        Notifications.create()
                .title(title)
                .text(content)
                .position(Pos.TOP_RIGHT)
                .showInformation();

    }


    public static void showErrorNotification(String title, String content){
        /**
         * Displays an error notification
         * ** title : String
         * ** content : String
         */
        Notifications.create()
                .title(title)
                .text(content)
                .position(Pos.TOP_RIGHT)
                .showError();
    }


    public static void showSuccessAlert(Window window, String title, String content){

    }

    public static void showErrorAlert(){

    }

    public static boolean showConfirmAlert(Window window, String title, String content){
        VBox rootNode = new VBox();
        rootNode.setPadding(new Insets(15));
        rootNode.setSpacing(15);

        Label titleLbl = new Label(title);
        titleLbl.setFont(Font.font(null, FontWeight.BOLD, 14));

        Label contentLbl = new Label(content);
        contentLbl.setFont(Font.font(null, FontWeight.NORMAL, 12));
        contentLbl.setWrapText(true);

        HBox controlsNode = new HBox();
        controlsNode.setSpacing(15);
        controlsNode.setAlignment(Pos.CENTER_RIGHT);

        MFXButton confirmBtn = new MFXButton("Accept");
        MFXButton cancelBtn = new MFXButton("Cancel");

        rootNode.getChildren().addAll(titleLbl, contentLbl, controlsNode);
        controlsNode.getChildren().addAll(confirmBtn, cancelBtn);

        if (confirmBtn.isPressed()) return true;
        else if (cancelBtn.isPressed()) return false;

        return false;
    }
}
