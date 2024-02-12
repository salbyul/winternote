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
import org.winternote.winternote.model.logging.WinterLogger;
import org.winternote.winternote.model.property.PrivateProperty;

import static org.winternote.winternote.model.property.PublicProperty.*;

@Component
public class StarterController extends AbstractController {

    private final ApplicationContext context;
    private final WinterLogger logger;
    private final PrivateProperty property;

    @FXML
    private VBox screen;

    @FXML
    private Button newButton;

    @FXML
    private ScrollPane scrollPane;

    public StarterController(final ApplicationContext context, final WinterLogger logger, final PrivateProperty property) {
        this.context = context;
        this.logger = logger;
        this.property = property;
    }

    public void initialize() {
        screen.setAlignment(Pos.CENTER);
    }

    @FXML
    private void onNewButtonClick() {
        Stage stage = CreationController.generateStage(context, property, logger);
        stage.show();
    }

    protected static Stage generateStage(final ApplicationContext context, final PrivateProperty property, final WinterLogger logger) {
        return AbstractController.generateStage(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader(WinterNoteApplication.class.getResource("winter-note-starter.fxml"));
            fxmlLoader.setControllerFactory(context::getBean);
            Scene scene = new Scene(fxmlLoader.load(), property.getDisplayWidth() / 3, property.getDisplayHeight() / 2);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle(APPLICATION_NAME);

            Controller controller = fxmlLoader.getController();
            controller.setStage(newStage);
            return newStage;
        }, logger);
    }
}