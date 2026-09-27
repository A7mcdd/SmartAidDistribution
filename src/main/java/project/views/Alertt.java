package project.views;

import javafx.scene.control.Alert;

public class Alertt {

    public static void info(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }

    public static void error(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
}