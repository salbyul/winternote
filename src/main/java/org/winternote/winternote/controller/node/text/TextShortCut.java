package org.winternote.winternote.controller.node.text;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public enum TextShortCut {

    SIZE_UP(new KeyCodeCombination(KeyCode.EQUALS, KeyCombination.SHORTCUT_DOWN)),
    SIZE_DOWN(new KeyCodeCombination(KeyCode.MINUS, KeyCombination.SHORTCUT_DOWN));

    private final KeyCodeCombination keyCodeCombination;

    TextShortCut(final KeyCodeCombination keyCodeCombination) {
        this.keyCodeCombination = keyCodeCombination;
    }

    public boolean match(final KeyEvent event) {
        return keyCodeCombination.match(event);
    }
}
