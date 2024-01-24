package org.winternote.winternote.controller.node.plate;

public enum Position {

    FORWARD, BACKWARD, MIDDLE;

    public boolean isForward() {
        return this == FORWARD;
    }

    public boolean isBackward() {
        return this == BACKWARD;
    }

    public boolean isMiddle() {
        return this == MIDDLE;
    }
}
