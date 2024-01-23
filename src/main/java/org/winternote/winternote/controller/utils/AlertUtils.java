package org.winternote.winternote.controller.utils;

import javafx.scene.control.Alert;
import org.winternote.winternote.controller.utils.message.Message;

public final class AlertUtils {

    private AlertUtils() {}

    public static void showAlert(final Alert.AlertType type, final Message message) {
        Alert alert = new Alert(type, message.getContent());
        alert.showAndWait();
    }
}
