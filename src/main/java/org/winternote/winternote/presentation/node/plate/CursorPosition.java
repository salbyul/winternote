package org.winternote.winternote.presentation.node.plate;

public enum CursorPosition {

    FIRST, MIDDLE, LAST;

    public boolean isLast() {
        return this == LAST;
    }

    public boolean isFirst() {
        return this == FIRST;
    }
}
