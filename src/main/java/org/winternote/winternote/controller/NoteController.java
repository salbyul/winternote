package org.winternote.winternote.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.winternote.winternote.controller.node.plate.Plate;
import org.winternote.winternote.model.logging.WinterLogger;
import org.winternote.winternote.model.property.PrivateProperty;

import static org.winternote.winternote.model.property.PublicProperty.*;

@Component
public class NoteController extends AbstractController {

    @FXML
    private Text title;

    @FXML
    private AnchorPane listPane;

    @FXML
    private VBox main;

    protected void setTitle(final String title) {
        this.title.setText(title);
    }

    public void initialize() {
        main.setSpacing(20);
        VBox.setVgrow(title, Priority.NEVER);
    }

    protected static Stage generateStage(final ApplicationContext context, final PrivateProperty property, final WinterLogger logger, final String title) {
        return AbstractController.generateStage(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader(NoteController.class.getResource("note.fxml"));
            fxmlLoader.setControllerFactory(context::getBean);
            Scene scene = new Scene(fxmlLoader.load(), property.getDisplayWidth(), property.getDisplayHeight());
            Stage stage = new Stage();
            stage.setTitle(APPLICATION_NAME + ": " + title);
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> {
                Stage starterStage = StarterController.generateStage(context, property, logger);
                starterStage.show();
            });

            NoteController controller = fxmlLoader.getController();
            controller.setStage(stage);
            controller.setTitle(title);
            controller.main.getChildren().add(new Plate());
            return stage;
        }, logger);
    }
}
