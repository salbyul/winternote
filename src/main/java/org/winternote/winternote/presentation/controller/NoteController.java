package org.winternote.winternote.presentation.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.winternote.winternote.metadata.service.MetadataService;
import org.winternote.winternote.presentation.node.plate.Plate;
import org.winternote.winternote.presentation.utils.AlertUtils;
import org.winternote.winternote.logging.WinterLogger;
import org.winternote.winternote.application.property.PrivateProperty;
import org.winternote.winternote.note.domain.Line;
import org.winternote.winternote.note.domain.Note;
import org.winternote.winternote.note.service.NoteService;

import java.io.IOException;
import java.util.List;

import static javafx.scene.control.Alert.AlertType.WARNING;
import static org.winternote.winternote.presentation.Shortcut.SAVE_SHORTCUT;
import static org.winternote.winternote.presentation.utils.message.Message.UNKNOWN_ERROR;
import static org.winternote.winternote.application.property.PublicProperty.*;

@Component
public class NoteController extends AbstractController {

    private final ApplicationContext context;
    private final PrivateProperty property;
    private final WinterLogger logger;
    private final AlertUtils alertUtils;
    private final NoteService noteService;
    private final MetadataService metadataService;

    private Note note;

    @FXML
    private Text title;

    @FXML
    private AnchorPane listPane;

    @FXML
    private VBox main;
    private Plate plate;

    public NoteController(final ApplicationContext context,
                          final PrivateProperty property,
                          final WinterLogger logger,
                          final AlertUtils alertUtils,
                          final NoteService noteService,
                          final MetadataService metadataService) {
        this.context = context;
        this.property = property;
        this.logger = logger;
        this.alertUtils = alertUtils;
        this.noteService = noteService;
        this.metadataService = metadataService;
    }

    private void setTitle(final String title) {
        this.title.setText(title);
    }

    /**
     * Sets the note and title.
     *
     * @param note The note.
     */
    private void setNote(final Note note) {
        this.note = note;
        setTitle(note.getName());
        getStage().setTitle(APPLICATION_NAME + ": " + note.getName());
    }

    /**
     * Sets the plate.
     *
     * @param plate The plate.
     */
    private void setPlate(final Plate plate) {
        this.plate = plate;
        main.getChildren().add(plate);
    }

    public void initialize() {
        main.setSpacing(20);
        VBox.setVgrow(title, Priority.NEVER);
    }

    /**
     * Loads the note contents and replace lines of plate.
     *
     * @param note The note.
     */
    private void loadNote(final Note note) {
        noteService.loadNoteLines(note);
        List<String> lines = note.getLinesAsString();
        if (lines.isEmpty()) {
            plate.replaceLines(List.of(""));
            return;
        }
        plate.replaceLines(note.getLinesAsString());
        plate.requestFocus();
    }

    /**
     * Save note lines and transfer to a file from the contents of plate.
     */
    private void saveNote() {
        List<Line> lines = plate.getContents().stream()
                .map(Line::new)
                .toList();

        note.replaceLines(lines);
        noteService.saveNote(note);
    }

    /**
     * Add a save shortcut.
     */
    private void addSaveEventHandler() {
        getStage().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (SAVE_SHORTCUT.match(event)) {
                saveNote();
            }
        });
    }

    /**
     * Free plate data and note data from memory.
     */
    private void close() {
        this.plate = null;
        this.note = null;
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
                close();
            });
            stage.setOnShowing(e -> loadNote(note));
            stage.setOnShown(e -> {
                metadataService.addRecentNote(note);
                metadataService.changeLocation(note.getLocation());
            });

            NoteController noteController = fxmlLoader.getController();
            noteController.setStage(stage);
            noteController.setNote(note);
            noteController.setPlate(new Plate());
            addSaveEventHandler();
            return stage;
        } catch (IOException e) {
            alertUtils.showAlert(WARNING, UNKNOWN_ERROR);
            logger.logException(e);
            System.exit(1);
        }
        return null; // unreachable code
    }
}
