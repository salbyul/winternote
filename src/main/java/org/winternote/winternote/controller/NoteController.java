package org.winternote.winternote.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.winternote.winternote.controller.utils.AlertUtils;
import org.winternote.winternote.controller.utils.WindowUtils;

import java.io.IOException;

import static javafx.scene.control.Alert.AlertType.*;
import static org.winternote.winternote.controller.utils.message.Message.*;
import static org.winternote.winternote.property.PrivateProperty.*;
import static org.winternote.winternote.property.PublicProperty.*;

public class NoteController extends AbstractController {

    @FXML
    private Text title;

    protected void setTitle(final String title) {
        this.title.setText(title);
    }

    protected static Stage generateNoteStage(final String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(NoteController.class.getResource("note.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), DISPLAY_WIDTH, DISPLAY_HEIGHT);
        Stage stage = new Stage();
        stage.setTitle(APPLICATION_NAME + ": " + title);
        stage.setScene(scene);
        stage.setOnCloseRequest(showStarterStage());

        NoteController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setTitle(title);
        return stage;
    }

    private static EventHandler<WindowEvent> showStarterStage() {
        return event -> {
            try {
                Stage stage = StarterController.generateStarterStage();
                stage.show();
            } catch (IOException | IllegalStateException e) {
                AlertUtils.showAlert(ERROR, CONFIGURATION_ERROR);
                WindowUtils.closeAllWindows();
            }
        };
    }
}
