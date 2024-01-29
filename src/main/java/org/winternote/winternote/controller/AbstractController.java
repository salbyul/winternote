package org.winternote.winternote.controller;

import javafx.stage.Stage;
import org.winternote.winternote.controller.utils.AlertUtils;
import org.winternote.winternote.model.logging.WinterLogger;

import java.io.IOException;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.WARNING;
import static org.winternote.winternote.controller.utils.message.Message.*;
import static org.winternote.winternote.model.property.PublicProperty.LOCATION_IS_NOT_SET;

public abstract class AbstractController implements Controller {

    private Stage stage;

    @Override
    public void setStage(final Stage stage) {
        this.stage = stage;
    }

    @Override
    public Stage getStage() {
        return this.stage;
    }

    protected static Stage generateStage(final StageGenerator generator) {
        try {
            return generator.generateStage();
        } catch (IOException | RuntimeException e) {
            WinterLogger instance = WinterLogger.instance();
            if (e.getMessage().equals(LOCATION_IS_NOT_SET)) {
                instance.logException(e);
                AlertUtils.showAlert(ERROR, CONFIGURATION_ERROR);
                System.exit(1);
            } else {
                instance.logException(e);
                AlertUtils.showAlert(WARNING, UNKNOWN_ERROR);
                System.exit(1);
            }
            return null; // unreachable code
        }
    }
}
