package org.winternote.winternote.presentation.controller;

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
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.winternote.winternote.common.exception.WinterException;
import org.winternote.winternote.note.service.NoteService;
import org.winternote.winternote.presentation.utils.AlertUtils;
import org.winternote.winternote.presentation.utils.WindowUtils;
import org.winternote.winternote.metadata.service.MetadataService;
import org.winternote.winternote.logging.WinterLogger;
import org.winternote.winternote.application.property.PrivateProperty;
import org.winternote.winternote.note.domain.Note;

import java.io.File;
import java.io.IOException;

import static javafx.scene.control.Alert.AlertType.*;
import static org.winternote.winternote.presentation.utils.message.Message.*;

@Component
public class CreationController extends AbstractController {

    private final ApplicationContext context;
    private final WinterLogger logger;
    private final MetadataService metadataService;
    private final PrivateProperty property;
    private final AlertUtils alertUtils;
    private final WindowUtils windowUtils;
    private final NoteService noteService;

    @FXML
    private VBox screen;

    @FXML
    private TextField title;

    @FXML
    private TextField path;

    @FXML
    private HBox buttonBox;

    public CreationController(final ApplicationContext context,
                              final WinterLogger logger,
                              final MetadataService metadataService,
                              final PrivateProperty property,
                              final AlertUtils alertUtils,
                              final WindowUtils windowUtils,
                              final NoteService noteService) {
        this.context = context;
        this.logger = logger;
        this.metadataService = metadataService;
        this.property = property;
        this.alertUtils = alertUtils;
        this.windowUtils = windowUtils;
        this.noteService = noteService;
    }

    public void initialize() {
        screen.setAlignment(Pos.CENTER);
        buttonBox.setAlignment(Pos.CENTER);
        title.onKeyPressedProperty().set(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                onCreateButtonClick();
            }
        });
        path.setText(metadataService.getRecentLocation());
        path.setEditable(false);
    }

    /**
     * Creates a note file on device and show the note editor scene.
     */
    @FXML
    private void onCreateButtonClick() {
        try {
            final String noteTitle = title.getText();
            final String noteLocation = path.getText();
            Note newNote = noteService.createNote(noteTitle, noteLocation);


            NoteController noteController = context.getBean(NoteController.class);
            Stage noteStage = noteController.generateStage(newNote);

            windowUtils.closeAllWindows();
            noteStage.show();
        } catch (IOException e) {
            alertUtils.showAlert(WARNING, UNKNOWN_ERROR);
            logger.logException(e);
        } catch (WinterException e) {
            alertUtils.showAlert(INFORMATION, e.getMessage());
            logger.logException(e);
        }
    }

    /**
     * Shows the DirectoryChooser.
     * The path set to the chosen directory.
     */
    @FXML
    private void onBrowseButtonClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Location to be saved");
        File choice;
        choice = showDirectoryChooser(directoryChooser, metadataService.getRecentLocation());
        path.setText(choice.getPath());
    }

    /**
     * Shows a directoryChooser.
     *
     * @param directoryChooser DirectoryChooser.
     * @param path             The base path.
     * @return Chosen directory.
     */
    private File showDirectoryChooser(final DirectoryChooser directoryChooser, final String path) {
        directoryChooser.setInitialDirectory(new File(path));
        return directoryChooser.showDialog(getStage());
    }

    /**
     * Close the current scene.
     *
     * @param event Closing event.
     */
    @FXML
    private void onCancelButtonClick(final ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public Stage generateStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CreationController.class.getResource("creation-note.fxml"));
            fxmlLoader.setControllerFactory(context::getBean);
            Scene scene = new Scene(fxmlLoader.load(), property.getDisplayWidth() / 5, property.getDisplayHeight() / 4);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            CreationController creationController = fxmlLoader.getController();
            creationController.setStage(stage);
            return stage;
        } catch (IOException e) {
            alertUtils.showAlert(WARNING, UNKNOWN_ERROR);
            logger.logException(e);
            System.exit(1);
        }
        return null; // unreachable code
    }
}