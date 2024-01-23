package org.winternote.winternote.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static org.winternote.winternote.property.PrivateProperty.*;

public class StarterController {

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
    private void onNewButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("creation-note.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), DISPLAY_WIDTH / 5, DISPLAY_HEIGHT / 4);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.show();
    }
}