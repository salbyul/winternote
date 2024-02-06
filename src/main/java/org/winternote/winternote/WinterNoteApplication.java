package org.winternote.winternote;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.winternote.winternote.controller.Controller;
import org.winternote.winternote.controller.utils.AlertUtils;
import org.winternote.winternote.model.application.ApplicationManager;
import org.winternote.winternote.model.application.initializer.ApplicationInitializer;
import org.winternote.winternote.model.application.initializer.Initializer;
import org.winternote.winternote.model.logging.WinterLogger;

import java.io.*;

import static org.winternote.winternote.model.application.ApplicationManager.*;
import static org.winternote.winternote.model.property.PublicProperty.*;

public class WinterNoteApplication extends Application {

    @Override
    public void start(final Stage stage) throws IOException {
        try {
            Initializer initializer = new ApplicationInitializer(ApplicationManager.getMetadataHandler());
            ApplicationManager.instance(initializer);
        } catch (Exception e) {
            WinterLogger instance = WinterLogger.instance();
            instance.logException(e);
            AlertUtils.showAlert(Alert.AlertType.ERROR, e.getMessage());
            System.exit(1);
        }

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