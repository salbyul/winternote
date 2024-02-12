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
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.winternote.winternote.common.exception.WinterException;
import org.winternote.winternote.controller.utils.AlertUtils;
import org.winternote.winternote.controller.utils.WindowUtils;
import org.winternote.winternote.metadata.service.MetadataService;
import org.winternote.winternote.model.logging.WinterLogger;
import org.winternote.winternote.model.property.PrivateProperty;
import org.winternote.winternote.project.domain.Project;
import org.winternote.winternote.note.service.NoteService;
import org.winternote.winternote.project.service.ProjectService;

import java.io.File;
import java.io.IOException;

import static javafx.scene.control.Alert.AlertType.*;
import static org.winternote.winternote.controller.utils.message.Message.*;
import static org.winternote.winternote.model.property.PublicProperty.DELIMITER;

@Component
public class CreationController extends AbstractController {

    private final ApplicationContext context;
    private final WinterLogger logger;
    private final NoteService noteService;

    private final ProjectService projectService;

    private final MetadataService metadataService;

    private final PrivateProperty property;

    @FXML
    private VBox screen;

    @FXML
    private TextField title;

    @FXML
    private TextField path;

    @FXML
    private HBox buttonBox;

    public CreationController(final ApplicationContext context, final WinterLogger logger, final NoteService noteService, final ProjectService projectService, final MetadataService metadataService, final PrivateProperty property) {
        this.context = context;
        this.logger = logger;
        this.noteService = noteService;
        this.projectService = projectService;
        this.metadataService = metadataService;
        this.property = property;
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

    @FXML
    private void onCreateButtonClick() { // TODO need Transactional
        Stage stage = NoteController.generateStage(context, property, logger, title.getText());
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
        choice = showDirectoryChooser(directoryChooser, metadataService.getRecentLocation());
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

    protected static Stage generateStage(final ApplicationContext context, final PrivateProperty property, final WinterLogger logger) {
        return AbstractController.generateStage(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader(CreationController.class.getResource("creation-note.fxml"));
            fxmlLoader.setControllerFactory(context::getBean);
            Scene scene = new Scene(fxmlLoader.load(), property.getDisplayWidth() / 5, property.getDisplayHeight() / 4);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.initModality(Modality.APPLICATION_MODAL);

            Controller controller = fxmlLoader.getController();
            controller.setStage(newStage);
            return newStage;
        }, logger);
    }
}