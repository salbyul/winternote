package org.winternote.winternote.controller.utils;

import javafx.scene.control.Alert;
import org.winternote.winternote.controller.utils.message.Message;

public final class AlertUtils {

    private AlertUtils() {
    }

    /**
     * Show an alert with a specific type and message.
     *
     * @param type    Specific type.
     * @param message message.
     */
    public static void showAlert(final Alert.AlertType type, final Message message) {
        Alert alert = new Alert(type, message.getContent());
        alert.showAndWait();
    }

    /**
     * Show an alert with a specific type and message.
     *
     * @param type    Specific type.
     * @param message message.
     */
    public static void showAlert(final Alert.AlertType type, final String message) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }
}
