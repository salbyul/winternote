package org.winternote.winternote;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static org.winternote.winternote.property.PrivateProperty.*;

public class WinterNoteApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("winter-note-starter.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), DISPLAY_WIDTH / 3, DISPLAY_HEIGHT / 2);
        stage.setTitle("Winter Note");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}