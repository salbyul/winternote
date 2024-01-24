package org.winternote.winternote.controller.node.plate;

public enum Position {

    LAST, FIRST, MIDDLE;

    public boolean isLast() {
        return this == LAST;
    }

    public boolean isFirst() {
        return this == FIRST;
    }

    public boolean isMiddle() {
        return this == MIDDLE;
    }
}
