package org.winternote.winternote;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.winternote.winternote.controller.Controller;

import java.io.IOException;

import static org.winternote.winternote.property.PrivateProperty.*;
import static org.winternote.winternote.property.PublicProperty.*;

public class WinterNoteApplication extends Application {

    @Override
    public void start(final Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("winter-note-starter.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), DISPLAY_WIDTH / 3, DISPLAY_HEIGHT / 2);
        stage.setScene(scene);
        stage.setTitle(APPLICATION_NAME);

        Controller controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}