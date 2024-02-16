package org.winternote.winternote.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public enum Shortcut {

    SIZE_UP_SHORTCUT(new KeyCodeCombination(KeyCode.EQUALS, KeyCombination.SHORTCUT_DOWN)),
    SIZE_DOWN_SHORTCUT(new KeyCodeCombination(KeyCode.MINUS, KeyCombination.SHORTCUT_DOWN)),
    SAVE_SHORTCUT(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));


    private final KeyCodeCombination keyCodeCombination;

    Shortcut(final KeyCodeCombination keyCodeCombination) {
        this.keyCodeCombination = keyCodeCombination;
    }

    public boolean match(final KeyEvent event) {
        return keyCodeCombination.match(event);
    }
}
