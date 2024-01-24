package org.winternote.winternote.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static org.winternote.winternote.model.property.PrivateProperty.*;
import static org.winternote.winternote.model.property.PublicProperty.*;

public class NoteController extends AbstractController {

    @FXML
    private Text title;

    protected void setTitle(final String title) {
        this.title.setText(title);
    }

    protected static Stage generateStage(final String title) {
        return AbstractController.generateStage(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader(NoteController.class.getResource("note.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), DISPLAY_WIDTH, DISPLAY_HEIGHT);
            Stage stage = new Stage();
            stage.setTitle(APPLICATION_NAME + ": " + title);
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> {
                Stage starterStage = StarterController.generateStage();
                starterStage.show();
            });

            NoteController controller = fxmlLoader.getController();
            controller.setStage(stage);
            controller.setTitle(title);
            return stage;
        });
    }
}
