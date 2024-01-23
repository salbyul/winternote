package org.winternote.winternote.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.winternote.winternote.WinterNoteApplication;

import static org.winternote.winternote.property.PrivateProperty.*;
import static org.winternote.winternote.property.PublicProperty.*;

public class StarterController extends AbstractController {

    @FXML
    private VBox screen;

    @FXML
    private Button newButton;

    @FXML
    private ScrollPane scrollPane;

    public void initialize() {
        screen.setAlignment(Pos.CENTER);
    }

    @FXML
    private void onNewButtonClick() {
        Stage stage = CreationController.generateStage();
        stage.show();
    }

    protected static Stage generateStage() {
        return AbstractController.generateStage(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader(WinterNoteApplication.class.getResource("winter-note-starter.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), DISPLAY_WIDTH / 3, DISPLAY_HEIGHT / 2);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle(APPLICATION_NAME);

            Controller controller = fxmlLoader.getController();
            controller.setStage(newStage);
            return newStage;
        });
    }
}