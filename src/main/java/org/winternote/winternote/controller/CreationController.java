package org.winternote.winternote.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.winternote.winternote.controller.utils.AlertUtils;
import org.winternote.winternote.controller.utils.WindowUtils;

import static javafx.scene.control.Alert.AlertType.*;
import static org.winternote.winternote.controller.utils.message.Message.*;
import static org.winternote.winternote.property.PrivateProperty.DISPLAY_HEIGHT;
import static org.winternote.winternote.property.PrivateProperty.DISPLAY_WIDTH;

public class CreationController extends AbstractController {

    @FXML
    private VBox screen;

    @FXML
    private TextField title;

    @FXML
    private HBox buttonBox;

    public void initialize() {
        screen.setAlignment(Pos.CENTER);
        buttonBox.setAlignment(Pos.CENTER);
        title.onKeyPressedProperty().set(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                onCreateButtonClick();
            }
        });
    }

    @FXML
    private void onCreateButtonClick() {
        Stage stage = NoteController.generateStage(title.getText());
        try {
            validateTitle();
            WindowUtils.closeAllWindows();
            stage.show();
        } catch (IllegalArgumentException e) {
            AlertUtils.showAlert(WARNING, TITLE_EMPTY_ERROR);
        }
    }

    private void validateTitle() {
        String text = title.getText();
        if (text.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
    }

    @FXML
    private void onCancelButtonClick(final ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    protected static Stage generateStage() {
        return AbstractController.generateStage(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader(CreationController.class.getResource("creat1ion-note.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), DISPLAY_WIDTH / 5, DISPLAY_HEIGHT / 4);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.initModality(Modality.APPLICATION_MODAL);

            Controller controller = fxmlLoader.getController();
            controller.setStage(newStage);
            return newStage;
        });
    }
}