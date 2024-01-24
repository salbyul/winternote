package org.winternote.winternote.controller.node.plate;

public enum CursorPosition {

    LAST, FIRST, MIDDLE;

    public boolean isLast() {
        return this == LAST;
    }

    public boolean isFirst() {
        return this == FIRST;
    }
}
