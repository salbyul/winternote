package org.winternote.winternote.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.winternote.winternote.WinterNoteApplication;
import org.winternote.winternote.controller.utils.AlertUtils;
import org.winternote.winternote.logging.WinterLogger;
import org.winternote.winternote.application.property.PrivateProperty;

import java.io.IOException;

import static javafx.scene.control.Alert.AlertType.WARNING;
import static org.winternote.winternote.controller.utils.message.Message.UNKNOWN_ERROR;
import static org.winternote.winternote.application.property.PublicProperty.*;

@Component
public class StarterController extends AbstractController {

    private final ApplicationContext context;
    private final PrivateProperty property;
    private final WinterLogger logger;
    private final AlertUtils alertUtils;

    @FXML
    private VBox screen;

    @FXML
    private Button newButton;

    @FXML
    private ScrollPane scrollPane;

    public StarterController(final ApplicationContext context, final PrivateProperty property, final WinterLogger logger, final AlertUtils alertUtils) {
        this.context = context;
        this.property = property;
        this.logger = logger;
        this.alertUtils = alertUtils;
    }

    public void initialize() {
        screen.setAlignment(Pos.CENTER);
    }

    @FXML
    private void onNewButtonClick() {
        CreationController controller = context.getBean(CreationController.class);
        Stage stage = controller.generateStage();
        stage.show();
    }

    public Stage generateStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(WinterNoteApplication.class.getResource("winter-note-starter.fxml"));
            fxmlLoader.setControllerFactory(context::getBean);
            Scene scene = new Scene(fxmlLoader.load(), property.getDisplayWidth() / 3, property.getDisplayHeight() / 2);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle(APPLICATION_NAME);

            StarterController starterController = fxmlLoader.getController();
            starterController.setStage(newStage);
            return newStage;
        } catch (IOException e) {
            alertUtils.showAlert(WARNING, UNKNOWN_ERROR);
            logger.logException(e);
            System.exit(1);
        }
        return null; // unreachable code
    }
}