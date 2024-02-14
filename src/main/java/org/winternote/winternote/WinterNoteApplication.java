package org.winternote.winternote;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.winternote.winternote.controller.Controller;

import java.io.*;

import static org.winternote.winternote.application.property.PublicProperty.*;

@SpringBootApplication
public class WinterNoteApplication extends Application {

    private static ConfigurableApplicationContext context;

    @Override
    public void start(final Stage stage) throws IOException {
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        final double displayWidth = bounds.getWidth();
        final double displayHeight = bounds.getHeight();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("winter-note-starter.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);

        Scene scene = new Scene(fxmlLoader.load(), displayWidth / 3, displayHeight / 2);
        stage.setScene(scene);
        stage.setTitle(APPLICATION_NAME);

        Controller controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.show();
    }

    @Override
    public void stop() {
        context.stop();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void init() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(WinterNoteApplication.class);
        builder.application()
                .setWebApplicationType(WebApplicationType.NONE);
        context = builder.run();
    }
}