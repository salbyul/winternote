package org.winternote.winternote.controller;

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
import org.winternote.winternote.common.exception.WinterException;
import org.winternote.winternote.controller.utils.AlertUtils;
import org.winternote.winternote.controller.utils.WindowUtils;
import org.winternote.winternote.metadata.service.MetadataService;
import org.winternote.winternote.model.application.ApplicationManager;
import org.winternote.winternote.model.logging.WinterLogger;
import org.winternote.winternote.project.domain.Project;
import org.winternote.winternote.note.service.NoteService;
import org.winternote.winternote.project.service.ProjectService;

import java.io.File;
import java.io.IOException;

import static javafx.scene.control.Alert.AlertType.*;
import static org.winternote.winternote.controller.utils.message.Message.*;
import static org.winternote.winternote.model.property.PrivateProperty.*;
import static org.winternote.winternote.model.property.PublicProperty.DELIMITER;

public class CreationController extends AbstractController {
    private final WinterLogger logger = WinterLogger.instance();

    private NoteService noteService;
    private ProjectService projectService;
    private MetadataService metadataService;

    @FXML
    private VBox screen;

    @FXML
    private TextField title;

    @FXML
    private TextField path;

    @FXML
    private HBox buttonBox;

    public void initialize() {
        noteService = ApplicationManager.getService(NoteService.class);
        projectService = ApplicationManager.getService(ProjectService.class);
        metadataService = ApplicationManager.getService(MetadataService.class);
        screen.setAlignment(Pos.CENTER);
        buttonBox.setAlignment(Pos.CENTER);
        title.onKeyPressedProperty().set(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                onCreateButtonClick();
            }
        });
        path.setText(metadataService.getRecentLocation());
    }

    @FXML
    private void onCreateButtonClick() { // TODO need Transactional
        Stage stage = NoteController.generateStage(title.getText());
        try {
            Project project = projectService.createProject(title.getText(), path.getText() + DELIMITER + title.getText());
            metadataService.addRecentProject(project);
            metadataService.changeLocation(path.getText());
            noteService.createNote(project, "untitled");

            WindowUtils.closeAllWindows();
            stage.show();
        } catch (IOException e) {
            AlertUtils.showAlert(WARNING, UNKNOWN_ERROR);
            logger.logException(e);
        } catch (WinterException e) {
            AlertUtils.showAlert(INFORMATION, e.getMessage());
            logger.logException(e);
        }
    }

    @FXML
    private void onBrowseButtonClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Location to be saved");
        File choice;
        try {
            choice = showDirectoryChooser(directoryChooser, metadataService.getRecentLocation());
        } catch (IllegalArgumentException e) { // The 'Location' of the metadata file is corrupted.
            choice = showDirectoryChooser(directoryChooser, APPLICATION_PATH);
            logger.logException(e);
        }
        path.setText(choice.getPath());
    }

    private File showDirectoryChooser(final DirectoryChooser directoryChooser, final String path) {
        directoryChooser.setInitialDirectory(new File(path));
        return directoryChooser.showDialog(getStage());
    }

    @FXML
    private void onCancelButtonClick(final ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    protected static Stage generateStage() {
        return AbstractController.generateStage(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader(CreationController.class.getResource("creation-note.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), DISPLAY_WIDTH / 5, DISPLAY_HEIGHT / 4);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.initModality(Modality.APPLICATION_MODAL);

            Controller controller = fxmlLoader.getController();
            controller.setStage(newStage);
            return newStage;
        });
    }
}