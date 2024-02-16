package org.winternote.winternote.presentation.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.winternote.winternote.common.annotation.Utils;
import org.winternote.winternote.presentation.utils.message.Message;

import java.util.Optional;

@Utils
public final class AlertUtils {

    /**
     * Show an alert with a specific type and message.
     *
     * @param type    Specific type.
     * @param message message.
     */
    public void showAlert(final Alert.AlertType type, final Message message) {
        Alert alert = new Alert(type, message.getContent());
        alert.showAndWait();
    }

    /**
     * Show an alert with a specific type and message.
     *
     * @param type    Specific type.
     * @param message message.
     */
    public void showAlert(final Alert.AlertType type, final String message) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }

    /**
     * Show an alert that has result.
     *
     * @param type    Alert type.
     * @param message Alert message.
     * @return result (true or false)
     */
    public boolean showAlertHasResult(final Alert.AlertType type, final String message) {
        Alert alert = new Alert(type, message, ButtonType.CANCEL, ButtonType.OK);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();
        ButtonType buttonType = optionalButtonType
                .orElseThrow(UnsupportedOperationException::new); // Exception that cannot be thrown.
        return !buttonType.getButtonData().isCancelButton();
    }
}
