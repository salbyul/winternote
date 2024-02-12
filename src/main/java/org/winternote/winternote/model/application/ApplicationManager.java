package org.winternote.winternote.model.application;

import javafx.scene.control.Alert;
import org.springframework.stereotype.Component;
import org.winternote.winternote.controller.utils.AlertUtils;
import org.winternote.winternote.model.application.initializer.Initializer;
import org.winternote.winternote.model.logging.WinterLogger;

@Component
public class ApplicationManager {

    private final WinterLogger logger;
    private final Initializer initializer;

    public ApplicationManager(final WinterLogger logger, final Initializer initializer) {
        this.logger = logger;
        this.initializer = initializer;
        initialize();
    }

    public void initialize() {
        try {
            if (initializer.isFirstTimeRunning()) {
                initializer.initialize();
            }
        } catch (Exception e) {
            logger.logException(e);
            AlertUtils.showAlert(Alert.AlertType.ERROR, e.getMessage());
            System.exit(1);
        }
    }
}