package org.winternote.winternote.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.winternote.winternote.controller.utils.WindowUtils;

import java.io.IOException;

import static org.winternote.winternote.controller.NoteController.generateNoteStage;

public class CreationController {

    @FXML
    private TextField title;

    @FXML
    private void onCreateButtonClick() throws IOException {
        Stage stage = generateNoteStage(title.getText());
        WindowUtils.closeAllWindows();
        stage.show();
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}