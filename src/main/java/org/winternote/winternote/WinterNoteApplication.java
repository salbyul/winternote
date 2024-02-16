package org.winternote.winternote;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.winternote.winternote.controller.controller.StarterController;

@SpringBootApplication
public class WinterNoteApplication extends Application {

    private static ConfigurableApplicationContext context;

    @Override
    public void start(final Stage stage) {
        StarterController controller = context.getBean(StarterController.class);
        Stage starterStage = controller.generateStage();
        starterStage.show();
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