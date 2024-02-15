package org.winternote.winternote.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.winternote.winternote.controller.node.plate.Plate;
import org.winternote.winternote.controller.utils.AlertUtils;
import org.winternote.winternote.logging.WinterLogger;
import org.winternote.winternote.application.property.PrivateProperty;
import org.winternote.winternote.note.domain.Line;
import org.winternote.winternote.note.domain.Note;
import org.winternote.winternote.note.service.NoteService;

import java.io.IOException;
import java.util.List;

import static javafx.scene.control.Alert.AlertType.WARNING;
import static org.winternote.winternote.controller.utils.message.Message.UNKNOWN_ERROR;
import static org.winternote.winternote.application.property.PublicProperty.*;

@Component
public class NoteController extends AbstractController {

    private final ApplicationContext context;
    private final PrivateProperty property;
    private final WinterLogger logger;
    private final AlertUtils alertUtils;
    private final NoteService noteService;

    private Note note;

    @FXML
    private Text title;

    @FXML
    private AnchorPane listPane;

    @FXML
    private VBox main;

    public NoteController(final ApplicationContext context,
                          final PrivateProperty property,
                          final WinterLogger logger,
                          final AlertUtils alertUtils,
                          final NoteService noteService) {
        this.context = context;
        this.property = property;
        this.logger = logger;
        this.alertUtils = alertUtils;
        this.noteService = noteService;
    }

    private void setTitle(final String title) {
        this.title.setText(title);
    }

    private void setNote(final Note note) {
        this.note = note;
        setTitle(note.getName());
        getStage().setTitle(APPLICATION_NAME + ": " + note.getName());
    }

    public void initialize() {
        main.setSpacing(20);
        VBox.setVgrow(title, Priority.NEVER);
    }

    private void loadNote(final Note note) {// TODO
        noteService.loadNoteLines(note);
    }

    public Stage generateStage(final Note note) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(NoteController.class.getResource("note.fxml"));
            fxmlLoader.setControllerFactory(context::getBean);
            Scene scene = new Scene(fxmlLoader.load(), property.getDisplayWidth(), property.getDisplayHeight());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> {
                StarterController starterController = context.getBean(StarterController.class);
                Stage starterStage = starterController.generateStage();
                starterStage.show();
            });
            stage.setOnShowing(e -> loadNote(note));

            NoteController noteController = fxmlLoader.getController();
            noteController.setStage(stage);
            noteController.setNote(note);
            noteController.main.getChildren().add(new Plate());
            return stage;
        } catch (IOException e) {
            alertUtils.showAlert(WARNING, UNKNOWN_ERROR);
            logger.logException(e);
            System.exit(1);
        }
        return null; // unreachable code
    }
}
