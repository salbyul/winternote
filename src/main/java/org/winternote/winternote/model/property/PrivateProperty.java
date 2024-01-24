package org.winternote.winternote.model.property;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public abstract class PrivateProperty {

    public static final double DISPLAY_WIDTH;
    public static final double DISPLAY_HEIGHT;
    public static final String OS;
    public static final String USER_NAME;

    static {
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        DISPLAY_WIDTH = bounds.getWidth();
        DISPLAY_HEIGHT = bounds.getHeight();
        OS = System.getProperty("os.name");
        USER_NAME = System.getProperty("user.name");
    }

    private PrivateProperty() {}

    public static boolean isMac() {
        return OS.startsWith("Mac");
    }
}