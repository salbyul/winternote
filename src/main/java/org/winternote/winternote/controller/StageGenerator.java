package org.winternote.winternote.controller;

import javafx.stage.Stage;

import java.io.IOException;

@FunctionalInterface
public interface StageGenerator {

    Stage generateStage() throws IOException;
}
