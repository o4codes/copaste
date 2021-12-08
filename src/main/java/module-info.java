/**
 * Requirements to be met:
 * 1. The module-info.java file must be in the root of the project.
 * 2. The module-info.java file must be in the src/main/java directory.
 * 3. The module-info.java file must be named module-info.java.
 * 4. The module-info.java file must be in the java source code format.
 */


module com.o4codes.copaste {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires javafx.graphics;
    requires io.javalin;
    requires kotlin.stdlib;

    opens com.o4codes.copaste to javafx.fxml;
    exports com.o4codes.copaste;
    exports com.o4codes.copaste.controllers;
    exports com.o4codes.copaste.models;
    opens com.o4codes.copaste.controllers to javafx.fxml;
}