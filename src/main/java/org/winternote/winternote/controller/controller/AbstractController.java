package org.winternote.winternote.controller.controller;

import javafx.stage.Stage;

public abstract class AbstractController implements Controller {

    private Stage stage;

    @Override
    public void setStage(final Stage stage) {
        this.stage = stage;
    }

    @Override
    public Stage getStage() {
        return this.stage;
    }
}
