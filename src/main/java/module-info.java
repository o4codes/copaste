module com.o4codes.clipshare {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires javafx.graphics;

    opens com.o4codes.clipshare to javafx.fxml;
    exports com.o4codes.clipshare;
    exports com.o4codes.clipshare.controllers;
    opens com.o4codes.clipshare.controllers to javafx.fxml;
}