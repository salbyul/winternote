package org.winternote.winternote.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.winternote.winternote.WinterNoteApplication;
import org.winternote.winternote.controller.utils.AlertUtils;
import org.winternote.winternote.logging.WinterLogger;
import org.winternote.winternote.application.property.PrivateProperty;
import org.winternote.winternote.metadata.service.MetadataService;
import org.winternote.winternote.note.domain.Note;
import org.winternote.winternote.note.domain.NoteSummary;

import java.io.IOException;
import java.util.List;

import static javafx.scene.control.Alert.AlertType.WARNING;
import static org.winternote.winternote.controller.utils.message.Message.UNKNOWN_ERROR;
import static org.winternote.winternote.application.property.PublicProperty.*;

@Component
public class StarterController extends AbstractController {

    private final ApplicationContext context;
    private final PrivateProperty property;
    private final WinterLogger logger;
    private final AlertUtils alertUtils;
    private final MetadataService metadataService;

    @FXML
    private VBox screen;

    @FXML
    private Button newButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox noteList;

    public StarterController(final ApplicationContext context, final PrivateProperty property, final WinterLogger logger, final AlertUtils alertUtils, final MetadataService metadataService) {
        this.context = context;
        this.property = property;
        this.logger = logger;
        this.alertUtils = alertUtils;
        this.metadataService = metadataService;
    }

    public void initialize() {
        screen.setAlignment(Pos.CENTER);
    }

    public void initializeSize() {
        scrollPane.setPrefWidth(scrollPane.getScene().getWidth());
        scrollPane.setPrefHeight(scrollPane.getScene().getHeight() / 3);
        noteList.setPrefWidth(scrollPane.getPrefWidth());
        noteList.setPrefHeight(scrollPane.getPrefHeight());
        noteList.setSpacing(3);
    }

    @FXML
    private void onNewButtonClick() {
        CreationController controller = context.getBean(CreationController.class);
        Stage stage = controller.generateStage();
        stage.show();
    }

    public void loadRecentNoteList() {
        List<NoteSummary> recentNoteList = metadataService.getRecentNoteList();
        for (NoteSummary note : recentNoteList) {
            Pane pane = transferToPane(note);
            noteList.getChildren().add(pane);
        }
    }

    private Pane transferToPane(final NoteSummary note) {
        // box setting
        HBox boxContent = new HBox();
        boxContent.setAlignment(Pos.CENTER_LEFT);
        boxContent.setSpacing(10);
        boxContent.setPadding(new Insets(10, 10, 10, 20));

        ObservableList<Node> children = boxContent.getChildren();
        children.add(new Text(note.getName()));
        children.add(new Text(note.getPath()));

        // pane setting
        Pane pane = new Pane(boxContent);
        pane.setStyle("-fx-border-color: gray;");
        pane.setPrefWidth(scrollPane.getPrefWidth());
        pane.setMinHeight(scrollPane.getPrefHeight() / 5);
        pane.setMaxHeight(scrollPane.getPrefHeight() / 5);
        boxContent.setPrefWidth(pane.getPrefWidth());
        boxContent.setPrefHeight(pane.getPrefHeight());

        pane.setCursor(Cursor.HAND);
        pane.setOnMouseClicked(e -> {
            NoteController noteController = context.getBean(NoteController.class);
            Stage stage = noteController.generateStage(Note.of(note));
            getStage().close();
            stage.show();
        });
        return pane;
    }

    public Stage generateStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(WinterNoteApplication.class.getResource("winter-note-starter.fxml"));
            fxmlLoader.setControllerFactory(context::getBean);
            Scene scene = new Scene(fxmlLoader.load(), property.getDisplayWidth() / 3, property.getDisplayHeight() / 2);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle(APPLICATION_NAME);
            newStage.setOnShown(e -> loadRecentNoteList());

            StarterController starterController = fxmlLoader.getController();
            starterController.setStage(newStage);
            starterController.initializeSize();
            return newStage;
        } catch (IOException e) {
            alertUtils.showAlert(WARNING, UNKNOWN_ERROR);
            logger.logException(e);
            System.exit(1);
        }
        return null; // unreachable code
    }
}