package org.winternote.winternote.controller;

import javafx.stage.Stage;
import org.winternote.winternote.controller.utils.AlertUtils;

import java.io.IOException;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.WARNING;
import static org.winternote.winternote.controller.utils.message.Message.*;
import static org.winternote.winternote.property.PublicProperty.LOCATION_IS_NOT_SET;

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
            if (e.getMessage().equals(LOCATION_IS_NOT_SET)) {
                AlertUtils.showAlert(ERROR, CONFIGURATION_ERROR);
                e.printStackTrace();
                System.exit(1);
            } else {
                AlertUtils.showAlert(WARNING, UNKNOWN_ERROR);
                e.printStackTrace();
                System.exit(1);
            }
            return null; // unreachable code
        }
    }
}
