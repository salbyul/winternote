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
import org.winternote.winternote.controller.utils.AlertUtils;
import org.winternote.winternote.controller.utils.WindowUtils;
import org.winternote.winternote.model.application.ApplicationManager;
import org.winternote.winternote.project.domain.Project;
import org.winternote.winternote.note.service.NoteService;
import org.winternote.winternote.project.service.ProjectService;

import java.io.File;
import java.io.IOException;

import static javafx.scene.control.Alert.AlertType.*;
import static org.winternote.winternote.controller.utils.message.Message.*;
import static org.winternote.winternote.model.application.ApplicationManager.DISPLAY_HEIGHT;
import static org.winternote.winternote.model.application.ApplicationManager.DISPLAY_WIDTH;

public class CreationController extends AbstractController {

    private NoteService noteService;
    private ProjectService projectService;

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
        screen.setAlignment(Pos.CENTER);
        buttonBox.setAlignment(Pos.CENTER);
        title.onKeyPressedProperty().set(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                onCreateButtonClick();
            }
        });
        path.setText(ApplicationManager.instance().getRecentLocation());
    }

    @FXML
    private void onCreateButtonClick() {
        Stage stage = NoteController.generateStage(title.getText());
        try {
            validateTitle();

            Project project = projectService.createProject(title.getText(), path.getText());
            noteService.createNote(project, "untitled");

            WindowUtils.closeAllWindows();
            stage.show();
        } catch (IllegalArgumentException e) {
            AlertUtils.showAlert(WARNING, TITLE_EMPTY_ERROR);
        } catch (IOException e) {
            AlertUtils.showAlert(WARNING, UNKNOWN_ERROR);
        }
    }

    @FXML
    private void onBrowseButtonClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Location to be saved");
        File choice;
        try {
            choice = showDirectoryChooser(directoryChooser, ApplicationManager.instance().getRecentLocation());
        } catch (IllegalArgumentException e) { // The 'Location' of the metadata file is corrupted.
            choice = showDirectoryChooser(directoryChooser, ApplicationManager.APPLICATION_PATH);
        }
        path.setText(choice.getPath());
    }

    private File showDirectoryChooser(final DirectoryChooser directoryChooser, final String path) {
        directoryChooser.setInitialDirectory(new File(path));
        return directoryChooser.showDialog(getStage());
    }

    private void validateTitle() {
        String text = title.getText();
        if (text.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
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