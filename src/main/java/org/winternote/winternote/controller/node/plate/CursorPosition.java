package org.winternote.winternote.controller.node.plate;

public enum CursorPosition {

    FIRST, MIDDLE, LAST;

    public boolean isLast() {
        return this == LAST;
    }

    public boolean isFirst() {
        return this == FIRST;
    }
}
