package org.winternote.winternote.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.winternote.winternote.WinterNoteApplication;
import org.winternote.winternote.controller.utils.AlertUtils;
import org.winternote.winternote.controller.utils.WindowUtils;

import java.io.IOException;

import static javafx.scene.control.Alert.AlertType.*;
import static org.winternote.winternote.controller.utils.message.Message.*;

public class NoteController {

    @FXML
    private Text title;

    protected void setTitle(final String title) {
        this.title.setText(title);
    }

    protected static Stage generateNoteStage(final String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(NoteController.class.getResource("note.fxml"));

        NoteController controller = fxmlLoader.getController();
        controller.setTitle(title);

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(WinterNoteApplication.class.getResource("winter-note-starter.fxml"));
                Stage newStage = new Stage();
                newStage.setScene(new Scene(loader.load()));
                newStage.show();
            } catch (IOException | IllegalStateException e) {
                AlertUtils.showAlert(ERROR, CONFIGURATION_ERROR);
                WindowUtils.closeAllWindows();
            }
        });
        return stage;
    }
}
